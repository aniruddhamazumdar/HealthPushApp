package com.healthpush.healthpushapp.model;

import java.io.Serializable;

/**
 * Created by ravikiran on 27/03/15.
 */
public class Article implements  Serializable{

    public ArticleData articleData = new ArticleData();
    public static class ArticleData implements Serializable{
        public String name = "";
        public String url = "";
    }




}
