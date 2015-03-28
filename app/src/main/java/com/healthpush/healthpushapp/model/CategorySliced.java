package com.healthpush.healthpushapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ravikiran on 28/03/15.
 */
public class CategorySliced implements  Serializable{

    public Article articles = new Article();
    public Video videos = new Video();

    public static class Article implements Serializable{
       public String count = "";
       public ArrayList<ArticleFeed> feed = new ArrayList<ArticleFeed>();
    }

    public static class Video implements Serializable{
        public String count = "";
        public ArrayList<ArticleFeed> feed = new ArrayList<ArticleFeed>();
    }


    public static class ArticleFeed implements Serializable{
        public String category = "";
        public String desc = "";
        public String image  = "";
        public String title  = "";
        public String type = "";
        public String url = "";
    }
}
