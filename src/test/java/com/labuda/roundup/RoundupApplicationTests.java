package com.labuda.roundup;

import com.labuda.roundup.api.ApiService;
import com.labuda.roundup.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
class RoundupApplicationTests {

    Logger logger = LoggerFactory.getLogger(RoundupApplicationTests.class);

    @Autowired
    ApiService service;

    @Autowired
    RoundupConfiguration configuration;

    @Autowired
    private String token;

    @Autowired
    private String apiUrl;

    @Autowired
    private DateTimeFormatter apiDateTimeFormatter;

    @Test
    void testContextAndPropertiesLoaded() {
        Assert.notNull(service, "Did not inject service.");
        Assert.notNull(service, "Did not inject configuration.");
        Assert.notNull(apiUrl, "Could not read properties file.");
        Assert.hasText(token, "Could not read the token. Place it in the file called token and add it to the classpath.");
    }

    @Test
    void testAtleastOneAccountExists() {
        Accounts accounts = service.getAllAccounts(token).block();
        Assert.notEmpty(accounts.getAccounts(), "No accounts exist.");
    }

    @Test
    void testAtleastOneFeedItemExists() {
        Accounts accounts = service.getAllAccounts(token).block();
        AccountV2 accountV2 = accounts.getAccounts().get(0);
        LocalDateTime localDateTime = LocalDateTime.now();
        FeedItems feedItems = service.getFeedItemsBetween(
                token,
                accountV2.getAccountUid(),
                accountV2.getDefaultCategory(),
                localDateTime.minusDays(7).format(apiDateTimeFormatter),
                localDateTime.format(apiDateTimeFormatter)
        ).block();
        Assert.notEmpty(feedItems.getFeedItems(), "No feed items exist.");
    }

    @Test
    void testCreateSavingsGoal() {
        Accounts accounts = service.getAllAccounts(token).block();
        AccountV2 accountV2 = accounts.getAccounts().get(0);
        SavingsGoalRequestV2 savingsGoalRequestV2 = new SavingsGoalRequestV2("test1", "GBP");
        savingsGoalRequestV2.setTarget(new CurrencyAndAmount("GBP", 100));
        CreateOrUpdateSavingsGoalResponseV2 result = service.createSavingsGoal(token, accountV2.getAccountUid(), savingsGoalRequestV2).block();
        logger.info(result.toString());
    }

    @Test
    void transferMoneyToSavingGoal() {
        Accounts accounts = service.getAllAccounts(token).block();
        AccountV2 accountV2 = accounts.getAccounts().get(0);
        SavingsGoalRequestV2 savingsGoalRequestV2 = new SavingsGoalRequestV2();
        savingsGoalRequestV2.setName("test1");
        savingsGoalRequestV2.setCurrency("GBP");
        savingsGoalRequestV2.setTarget(new CurrencyAndAmount("GBP", 100));
        CreateOrUpdateSavingsGoalResponseV2 result = service.createSavingsGoal(token, accountV2.getAccountUid(), savingsGoalRequestV2).block();
        String savingsGoalUid = result.getSavingsGoalUid();
        String transferUid = UUID.randomUUID().toString();
        TopUpRequestV2 topUpRequestV2 = new TopUpRequestV2();
        topUpRequestV2.setAmount(new CurrencyAndAmount("GBP", 100));
        SavingsGoalTransferResponseV2 response = service.transferMoneyToSavingGoal(token, accountV2.getAccountUid(), savingsGoalUid, transferUid, topUpRequestV2).block();
        Assert.isTrue(response.isSuccess(), "Succesfully added money to saving goal");
    }

    @Test
    void testApiDateTimeFormat() {
        LocalDateTime testDateTime = LocalDateTime.of(2020, 2, 6, 16, 0, 0);
        Assert.isTrue(apiDateTimeFormatter.format(testDateTime).equals("2020-02-06T16:00:00.000Z"), "DateTimeFormatTest");
    }
}
