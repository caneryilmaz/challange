package com.cnrylmz.challengemobilist.Utils;

import com.cnrylmz.challengemobilist.api.model.Feed;

import java.util.Comparator;

/**
 * Created by Caner on 08.05.2019.
 */

public class CustomComparatorTime implements Comparator<Feed> {
    @Override
    public int compare(Feed feed, Feed t1) {
        return t1.getCreatedAt().compareTo(feed.getCreatedAt());
    }
}
