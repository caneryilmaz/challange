package com.cnrylmz.challengemobilist.Utils;

import com.cnrylmz.challengemobilist.api.model.Feed;

import java.util.Comparator;

/**
 * Created by Caner on 08.05.2019.
 */

public class CustonComparatorFollower implements Comparator<Feed> {
    @Override
    public int compare(Feed feed, Feed t1) {
        return feed.getFollowerCount().compareToIgnoreCase(t1.getFollowerCount());
    }
}
