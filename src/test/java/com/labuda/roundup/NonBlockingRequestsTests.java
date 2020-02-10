package com.labuda.roundup;

import com.labuda.roundup.api.ApiService;
import com.labuda.roundup.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.stream.LongStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NonBlockingRequestsTests {

    Logger logger = LoggerFactory.getLogger(NonBlockingRequestsTests.class);

    @Autowired
    private ApiService apiService;

    @Autowired
    private String token;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebClient webClient;

    private String accountUuid;
    private String categoryUuid;

    @BeforeAll
    public void setUp2() {
        Accounts accounts = apiService.getAllAccounts(token).block();
        Assert.notEmpty(accounts.getAccounts(), "No accounts found.");
        AccountV2 accountV2 = accounts.getAccounts().get(0);

        accountUuid = accountV2.getAccountUid();
        categoryUuid = accountV2.getDefaultCategory();
    }

    @Disabled
    @Test
    public void testCreateSavingGoalsTransferMoneyDeleteSavingGoals() {
        int multiplier = 30;
        long startTime = System.nanoTime();
        Flux<Void> result = Flux.fromStream(LongStream.range(0, multiplier).boxed())
                .flatMap(aLong -> {
                    SavingsGoalRequestV2 savingsGoalRequestV2 = new SavingsGoalRequestV2(String.format("SavingGoal -%d", aLong), "GBP");
                    return apiService.createSavingsGoal(token, accountUuid, savingsGoalRequestV2)
                            .flatMap(createOrUpdateSavingsGoalResponseV2 -> {
                                TopUpRequestV2 topUpRequestV2 = new TopUpRequestV2("GBP", 1);
                                return apiService.transferMoneyToSavingGoal(token, accountUuid, createOrUpdateSavingsGoalResponseV2.getSavingsGoalUid(), UUID.randomUUID().toString(), topUpRequestV2)
                                        .flatMap(savingsGoalTransferResponseV2 -> apiService.deleteSavingGoal(token, accountUuid, createOrUpdateSavingsGoalResponseV2.getSavingsGoalUid()));
                            });
                });
        result.blockLast();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        logger.info("Processed {} requests in {} ms", multiplier * 3, timeElapsed / 1000000);
    }

    @Disabled
    @Test
    public void testMultiplePayments() {
        SavingsGoalRequestV2 savingsGoalRequestV2 = new SavingsGoalRequestV2("TestSavingGoal", "GBP");
        CreateOrUpdateSavingsGoalResponseV2 createOrUpdateSavingsGoalResponseV2 = apiService.createSavingsGoal(token, accountUuid, savingsGoalRequestV2).block();

        Flux<Object> result = Flux.fromStream(LongStream.range(0, 50).boxed())
                .flatMap(aLong -> {
                    TopUpRequestV2 topUpRequestV2 = new TopUpRequestV2("GBP", 1);
                    return apiService.transferMoneyToSavingGoal(token, accountUuid, createOrUpdateSavingsGoalResponseV2.getSavingsGoalUid(), UUID.randomUUID().toString(), topUpRequestV2)
                            .retry();
                });

        result.blockLast();

        apiService.deleteSavingGoal(token, accountUuid, createOrUpdateSavingsGoalResponseV2.getSavingsGoalUid()).block();
    }

    @Test
    public void testDeleteAllSavingGoals() {
        Flux<Object> flux = apiService.getAllSavingGoals(token, accountUuid)
                .flatMapIterable(SavingsGoalsV2::getSavingsGoalList)
                .flatMap(savingsGoalV2 -> apiService.deleteSavingGoal(token, accountUuid, savingsGoalV2.getSavingsGoalUid()));
        flux.blockLast();
    }

}
