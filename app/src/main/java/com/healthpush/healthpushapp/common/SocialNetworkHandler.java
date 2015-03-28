package com.healthpush.healthpushapp.common;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class SocialNetworkHandler {

    private static SocialNetworkHandler instance;
    public static SocialNetworkHandler getInstance() {
        if (instance == null) {
            instance = new SocialNetworkHandler();
        }
        return instance;
    }
    private SocialNetworkHandler() {

    }
}
