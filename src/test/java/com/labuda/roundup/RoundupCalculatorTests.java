package com.labuda.roundup;

import com.labuda.roundup.logic.RoundupCalculator;
import com.labuda.roundup.model.CurrencyAndAmount;
import com.labuda.roundup.model.FeedItem;
import com.labuda.roundup.model.FeedItems;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.labuda.roundup.model.FeedItem.*;

@ActiveProfiles("test")
@SpringBootTest
public class RoundupCalculatorTests {

    @Test
    void testRoundUpCalculatorReturnsCorrectValue() {
        FeedItems feedItems = new FeedItems();
        ArrayList<FeedItem> feedItemsList = new ArrayList<>();
        feedItemsList.add(getAllowedFi(435));
        feedItemsList.add(getAllowedFi(520));
        feedItemsList.add(getAllowedFi(87));
        feedItems.setFeedItems(feedItemsList);
        Assertions.assertEquals(158, RoundupCalculator.roundUpFeedItems(feedItems.getFeedItems().stream(), 100));
    }

    @ParameterizedTest
    @MethodSource("provideNumbersToRoundShillings")
    void TestRoundUpShilling(long test, long expectedResult) {
        Assertions.assertEquals(expectedResult, RoundupCalculator.roundUp(test, 12));
    }

    @ParameterizedTest
    @MethodSource("provideNumbersToRound")
    void TestRoundUpMethod(long test, long expectedResult) {
        Assertions.assertEquals(expectedResult, RoundupCalculator.roundUp(test, 100));
    }

    private static Stream<Arguments> provideNumbersToRound() {
        return Stream.of(
                Arguments.of(12, 88),
                Arguments.of(100, 0),
                Arguments.of(200, 0),
                Arguments.of(300, 0),
                Arguments.of(1, 99),
                Arguments.of(0, 0),
                Arguments.of(99, 1)
        );
    }

    private static Stream<Arguments> provideNumbersToRoundShillings() {
        return Stream.of(
                Arguments.of(12, 0),
                Arguments.of(11, 1),
                Arguments.of(0, 0),
                Arguments.of(1, 11),
                Arguments.of(2, 10),
                Arguments.of(10, 2),
                Arguments.of(7, 5)
        );
    }

    static FeedItem getAllowedFi(int amount) {
        return getFeedItem(amount, Status.SETTLED, Source.MASTER_CARD, Direction.OUT);
    }

    static FeedItem getFeedItem(int amount, FeedItem.Status status, FeedItem.Source source, FeedItem.Direction direction) {
        CurrencyAndAmount currencyAndAmount = new CurrencyAndAmount();
        currencyAndAmount.setCurrency("GBP");
        currencyAndAmount.setMinorUnits(amount);
        FeedItem fi = new FeedItem();
        fi.setAmount(currencyAndAmount);
        fi.setStatus(status.name());
        fi.setSource(source.name());
        fi.setDirection(direction.name());
        return fi;
    }

}
