package com.labuda.roundup.api;

import com.labuda.roundup.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service class that sends requests to Starling API
 */

@Component
public class ApiService {

    @Autowired
    private WebClient webClient;

    public Mono<Accounts> getAllAccounts(String authorization) {
        return webClient
                .get()
                .uri("/accounts")
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(Accounts.class));
    }

    public Mono<FeedItems> getUpdatedFeedItemsSince(String authorization, String accountUid, String categoryUid, String start) {
        return webClient
                .get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/feed/account/{accountUid}/category/{categoryUid}");
                    if (start != null) uriBuilder.queryParam("changesSince", start);
                    return uriBuilder.build(accountUid, categoryUid);
                })
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(FeedItems.class));
    }

    public Mono<FeedItems> getFeedItemsBetween(String authorization, String accountUid, String categoryUid, String start, String end) {
        return webClient
                .get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/feed/account/{accountUid}/category/{categoryUid}/transactions-between");
                    if (start != null) uriBuilder.queryParam("minTransactionTimestamp", start);
                    if (end != null) uriBuilder.queryParam("maxTransactionTimestamp", end);
                    return uriBuilder.build(accountUid, categoryUid);
                })
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(FeedItems.class));
    }

    public Mono<CreateOrUpdateSavingsGoalResponseV2> createSavingsGoal(String authorization, String accountUid, SavingsGoalRequestV2 savingsGoalRequestV2) {
        return webClient
                .put()
                .uri("/account/{accountUid}/savings-goals", accountUid)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(BodyInserters.fromValue(savingsGoalRequestV2))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(CreateOrUpdateSavingsGoalResponseV2.class));
    }

    public Mono<SavingsGoalTransferResponseV2> transferMoneyToSavingGoal(String authorization, String accountUid, String savingsGoalUid, String transferUid, TopUpRequestV2 topUpRequestV2) {
        return webClient
                .put()
                .uri("/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}", accountUid, savingsGoalUid, transferUid)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(BodyInserters.fromValue(topUpRequestV2))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(SavingsGoalTransferResponseV2.class));
    }

    public Mono<SavingsGoalsV2> getAllSavingGoals(String authorization, String accountUid) {
        return webClient
                .get()
                .uri("/account/{accountUid}/savings-goals", accountUid)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(SavingsGoalsV2.class));
    }

    public Mono<SavingsGoalTransferResponseV2> addMoneySavingsGoal(String authorization, String accountUid, String savingsGoalUid, String transferUid, TopUpRequestV2 topUpRequestV2) {
        return webClient
                .put()
                .uri("/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}", accountUid, savingsGoalUid, transferUid)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(BodyInserters.fromValue(topUpRequestV2))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(SavingsGoalTransferResponseV2.class));
    }

    public Mono<Void> deleteSavingGoal(String authorization, String accountUid, String savingsGoalUid) {
        return webClient
                .delete()
                .uri("/account/{accountUid}/savings-goals/{savingsGoalUid}", accountUid, savingsGoalUid)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .exchange().then();
    }
}
