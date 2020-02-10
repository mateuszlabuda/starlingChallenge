package com.labuda.roundup.logic;

import com.labuda.roundup.model.FeedItem;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This class is responsible for rounding up the transactions and calculating the aggregate complements
 * It can also filter feed items while aggregating
 * It takes scaling parameter in case of non decimal currencies (eg. 1 Pound = 1*20 Shilling = 1*20*12 Pence)
 */
public class RoundupCalculator {

    public static long filterAndRoundupFeedItems(Stream<FeedItem> feedItems, long scaling, Predicate<FeedItem> feedItemFilter) {
        return roundUpFeedItems(feedItems.filter(feedItemFilter), scaling);
    }

    public static long roundUpFeedItems(Stream<FeedItem> feedItems, long scaling) {
        return feedItems
                .map(FeedItem::getAmount)
                .mapToLong(amount -> roundUp(amount.getMinorUnits(), scaling))
                .sum();
    }

    public static long roundUp(long minorUnits, long scaling) {
        return minorUnits % scaling > 0 ? scaling - (minorUnits % scaling) : 0;
    }
}
