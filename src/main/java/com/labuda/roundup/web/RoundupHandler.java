package com.labuda.roundup.web;

import com.labuda.roundup.api.ApiService;
import com.labuda.roundup.logic.RoundupCalculator;
import com.labuda.roundup.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Predicate;

import static com.labuda.roundup.model.FeedItem.Direction.OUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * This class holds handlers to all available endpoints
 */

@Component
public class RoundupHandler {

    @Autowired
    private DateTimeFormatter apiDateTimeFormatter;

    @Autowired
    private ApiService apiService;

    public Mono<ServerResponse> accountsGet(ServerRequest serverRequest) {
        Mono<Accounts> accounts = apiService.getAllAccounts(getAuthorizationHeader(serverRequest));
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(accounts, Accounts.class);
    }

    public Mono<ServerResponse> accountGetSavingsGoals(ServerRequest serverRequest) {
        Mono<SavingsGoalsV2> goals = apiService.getAllSavingGoals(
                getAuthorizationHeader(serverRequest),
                serverRequest.pathVariable("accountUid")
        );
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(goals, SavingsGoalsV2.class);
    }

    public Mono<ServerResponse> accountPutSavingGoal(ServerRequest serverRequest) {
        Mono<CreateOrUpdateSavingsGoalResponseV2> createOrUpdateSavingsGoalResponseV2 =
                serverRequest.bodyToMono(SavingsGoalRequestV2.class)
                        .flatMap(savingsGoalRequestV2 -> apiService.createSavingsGoal(
                                getAuthorizationHeader(serverRequest),
                                serverRequest.pathVariable("accountUid"),
                                savingsGoalRequestV2)
                        );
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(createOrUpdateSavingsGoalResponseV2, CreateOrUpdateSavingsGoalResponseV2.class);
    }

    public Mono<ServerResponse> deleteSavingGoal(ServerRequest serverRequest) {
        Mono<Void> mono = apiService.deleteSavingGoal(
                getAuthorizationHeader(serverRequest),
                serverRequest.pathVariable("accountUid"),
                serverRequest.pathVariable("savingsGoalUid")
        );
        return ServerResponse.ok().contentType(APPLICATION_JSON).build(mono);
    }

    public Mono<ServerResponse> accountPutSavingsGoalAddMoney(ServerRequest serverRequest) {
        Mono<SavingsGoalTransferResponseV2> savingsGoalTransferResponseV2 =
                serverRequest.bodyToMono(TopUpRequestV2.class)
                        .flatMap(topUpRequestV2 -> apiService.addMoneySavingsGoal(
                                getAuthorizationHeader(serverRequest),
                                serverRequest.pathVariable("accountUid"),
                                serverRequest.pathVariable("savingsGoalUid"),
                                serverRequest.pathVariable("transferUid"),
                                topUpRequestV2
                                )
                        );
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(savingsGoalTransferResponseV2, SavingsGoalTransferResponseV2.class);
    }

    public Mono<ServerResponse> accountGetTransactionsSince(ServerRequest serverRequest) {
        Mono<FeedItems> feedItems = apiService.getUpdatedFeedItemsSince(
                getAuthorizationHeader(serverRequest),
                serverRequest.pathVariable("accountUid"),
                serverRequest.pathVariable("categoryUid"),
                serverRequest.queryParam("changesSince").orElse(null)
        );
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(feedItems, FeedItems.class);
    }

    public Mono<ServerResponse> accountGetTransactionsBetween(ServerRequest serverRequest) {
        Mono<FeedItems> feedItems = apiService.getFeedItemsBetween(
                getAuthorizationHeader(serverRequest),
                serverRequest.pathVariable("accountUid"),
                serverRequest.pathVariable("categoryUid"),
                serverRequest.queryParam("minTransactionTimestamp").orElse(null),
                serverRequest.queryParam("maxTransactionTimestamp").orElse(null)
        );
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(feedItems, FeedItems.class);
    }

    /**
     * This method chains series of requests in a non blocking way
     * It retrieves all accounts, for each account it retrieves transactions
     * After filtering & summing up complements of payments it creates a saving goal and transfers the money.
     */
    public Mono<ServerResponse> roundupAll(ServerRequest serverRequest) {
        String start = LocalDateTime.now().minusDays(7).format(apiDateTimeFormatter);

        //filter out non purchase items (to the best of my knowledge)
        Predicate<FeedItem> feedItemFilter = feedItem -> OUT.equals(FeedItem.Direction.valueOf(feedItem.getDirection()))
                && (FeedItem.Source.valueOf(feedItem.getSource()).equals(FeedItem.Source.MASTER_CARD) ||
                FeedItem.Source.valueOf(feedItem.getSource()).equals(FeedItem.Source.FASTER_PAYMENTS_OUT))
                && FeedItem.Status.SETTLED.equals(FeedItem.Status.valueOf(feedItem.getStatus()));

        Flux<SavingsGoalTransferResponseV2> savingsGoalTransferResponseV2 = apiService.getAllAccounts(getAuthorizationHeader(serverRequest))
                .flatMapIterable(Accounts::getAccounts)
                .flatMap(accounts1 -> Mono.just(accounts1)
                        .flatMap(accountV2 -> apiService.getUpdatedFeedItemsSince(getAuthorizationHeader(serverRequest), accountV2.getAccountUid(), accountV2.getDefaultCategory(), start)
                                .flatMap(feedItems -> Mono.just(RoundupCalculator.filterAndRoundupFeedItems(feedItems.getFeedItems().stream(), 100, feedItemFilter)))
                                .flatMap(roundUpTotal -> {
                                            SavingsGoalRequestV2 newGoalRequest = new SavingsGoalRequestV2(String.format("My Saving Goal %s", start), "GBP");
                                            return apiService.createSavingsGoal(getAuthorizationHeader(serverRequest), accountV2.getAccountUid(), newGoalRequest)
                                                    .flatMap(createOrUpdateSavingsGoalResponseV2 -> {
                                                        String transferUUID = UUID.randomUUID().toString();
                                                        TopUpRequestV2 topUpRequestV2 = new TopUpRequestV2("GBP", roundUpTotal);
                                                        return apiService.transferMoneyToSavingGoal(getAuthorizationHeader(serverRequest), accountV2.getAccountUid(), createOrUpdateSavingsGoalResponseV2.getSavingsGoalUid(), transferUUID, topUpRequestV2);
                                                    });
                                        }
                                )
                        )
                );

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(savingsGoalTransferResponseV2, SavingsGoalTransferResponseV2.class);
    }

    private String getAuthorizationHeader(ServerRequest request) {
        return request.headers().asHttpHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }
}
