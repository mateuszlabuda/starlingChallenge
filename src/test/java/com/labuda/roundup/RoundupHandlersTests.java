package com.labuda.roundup;

import com.labuda.roundup.api.ApiService;
import com.labuda.roundup.model.AccountV2;
import com.labuda.roundup.model.Accounts;
import com.labuda.roundup.model.FeedItem;
import com.labuda.roundup.model.FeedItems;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.containsString;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoundupHandlersTests {

    Logger logger = LoggerFactory.getLogger(RoundupHandlersTests.class);

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ApiService apiService;

    @Autowired
    private String token;

    @BeforeEach
    public void setUp() {
        Assert.hasText(token, "Could not read the token. Place it in the file called token and add it to the classpath.");
    }

    @Autowired
    private DateTimeFormatter apiDateTimeFormatter;

    private String accountUuid;
    private String categoryUuid;

    @BeforeAll
    public void setUp2() {
        Accounts accounts = apiService.getAllAccounts(token).block();
        Assert.notEmpty(accounts.getAccounts(), "No accounts exist.");
        AccountV2 accountV2 = accounts.getAccounts().get(0);
        accountUuid = accountV2.getAccountUid();
        categoryUuid = accountV2.getDefaultCategory();
    }

    @Test
    public void testNoAuthorizationHeaderReturns403MessageInTheBody() {
        webTestClient.get()
                .uri("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("message")
                .value(containsString("403"));
    }

    @Test
    public void testGetAccounts() {
        webTestClient.get()
                .uri("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void test404isReturnedOnIncorrectEndpoint() {
        webTestClient.get()
                .uri("/wrongEndpoint")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testExactlyOneAccountExists() {
        webTestClient.get()
                .uri("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AccountV2.class)
                .hasSize(1);
    }

    @Test
    public void testGetTransactionsBetweenReturn400MessageInTheBodyOnBadRequest() {
        LocalDateTime now = LocalDateTime.now();
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/account/{accountUid}/category/{categoryUid}/transactions-between")
                        .queryParam("minTransactionTimestamp", apiDateTimeFormatter.format(now))
                        .queryParam("maxTransactionTimestamp", apiDateTimeFormatter.format(now.minusDays(1)))
                        .build(accountUuid, categoryUuid))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("message")
                .value(containsString("400"));
    }

    @Test
    public void testGetFeedItemsReturns200() {
        LocalDateTime now = LocalDateTime.now();
        EntityExchangeResult<FeedItems> feedItems = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/account/{accountUid}/category/{categoryUid}/transactions-between")
                        .queryParam("minTransactionTimestamp", apiDateTimeFormatter.format(now.minusDays(365)))
                        .queryParam("maxTransactionTimestamp", apiDateTimeFormatter.format(now))
                        .build(accountUuid, categoryUuid))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedItems.class)
                .returnResult();

        Assert.notEmpty(feedItems.getResponseBody().getFeedItems(), "FeedItems were empty. Are there any transactions?");
        feedItems.getResponseBody().getFeedItems().forEach(fi -> logger.debug(
                "DIRECTION -> {} -> {} -> {} {}",
                fi.getDirection(),
                fi.getSource(),
                fi.getStatus(),
                fi.getAmount()));
    }

    @Test
    public void testTransactionsSinceATimestampReturn200() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        EntityExchangeResult<FeedItems> feedItems = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/account/{accountUid}/category/{categoryUid}")
                        .queryParam("changesSince", apiDateTimeFormatter.format(start))
                        .build(accountUuid, categoryUuid))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedItems.class)
                .returnResult();

        Assert.notEmpty(feedItems.getResponseBody().getFeedItems(), "FeedItems were empty. Are there any transactions?");
        for (FeedItem fi : feedItems.getResponseBody().getFeedItems()) {
            logger.debug(
                    "DIRECTION -> {} -> {} -> {} {}",
                    fi.getDirection(),
                    fi.getSource(),
                    fi.getStatus(),
                    fi.getAmount());
        }

    }
}
