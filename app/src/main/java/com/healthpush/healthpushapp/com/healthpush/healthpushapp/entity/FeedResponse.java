package com.healthpush.healthpushapp.com.healthpush.healthpushapp.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class FeedResponse implements Serializable {

    public ArrayList<Feed> feeds = new ArrayList<>();

    public static class Feed {
        public String category;
        public String desc;
        public String title;
        public String type;
        public String url;

        public boolean read;
    }
}
