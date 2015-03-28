package com.healthpush.healthpushapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ravikiran on 28/03/15.
 */
public class FeedDesc implements Serializable{

    public ArrayList<Feeds> feed = new ArrayList<Feeds>();
    public static class Feeds implements Serializable{
        public String category = "";
        public String desc = "";
        public String image  = "";
        public String title  = "";
        public String type = "";
        public String url = "";
    }
}
