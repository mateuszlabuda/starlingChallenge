package com.labuda.roundup.model;

import java.util.List;

public class FeedItems {

    private List<FeedItem> feedItems;

    public FeedItems() {
    }

    public FeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }
}
