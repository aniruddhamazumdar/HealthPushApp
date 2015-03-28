package com.healthpush.healthpushapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class TokenResponse implements Serializable {
//    public String fb_id = "";
//    public String id = "";
//    public String x_auth_token = "";

    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("x-auth-token")
    @Expose
    private String xAuthToken;

    /**
     *
     * @return
     * The fbId
     */
    public String getFbId() {
        return fbId;
    }

    /**
     *
     * @param fbId
     * The fb_id
     */
    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The xAuthToken
     */
    public String getXAuthToken() {
        return xAuthToken;
    }

    /**
     *
     * @param xAuthToken
     * The x-auth-token
     */
    public void setXAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }
}
