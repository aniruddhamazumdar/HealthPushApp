package com.healthpush.healthpushapp.model;

import java.io.Serializable;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class NetworkFeedItem implements Serializable {

    public String user_pic;
    public String name;
    public String like_count;
    public String time_stamp;
    public String text;
    public boolean is_map = false;
    public boolean is_article = false;
    public boolean is_video = false;
}
