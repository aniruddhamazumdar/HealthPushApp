package com.healthpush.healthpushapp.common;

import com.androidsocialnetworks.lib.SocialNetworkManager;
import com.androidsocialnetworks.lib.impl.TwitterSocialNetwork;

/**
 * Created by aniruddhamazumdar on 27/03/15.
 */
public class SocialNetworkHandler {

    public TwitterSocialNetwork twitterSocialNetwork;

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
