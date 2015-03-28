package com.healthpush.healthpushapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniruddhamazumdar on 28/03/15.
 */
public class ProfileResponse implements Serializable {
    @Expose
    private List<Activity> activities = new ArrayList<Activity>();
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @Expose
    private String id;
    @Expose
    private List<String> interests = new ArrayList<String>();
    @SerializedName("last_checked_in_at")
    @Expose
    private String lastCheckedInAt;
    @SerializedName("last_checked_in_loc")
    @Expose
    private String lastCheckedInLoc;
    @Expose
    private String name;
    @Expose
    private String points;

    /**
     *
     * @return
     * The activities
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     *
     * @param activities
     * The activities
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

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
     * The interests
     */
    public List<String> getInterests() {
        return interests;
    }

    /**
     *
     * @param interests
     * The interests
     */
    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    /**
     *
     * @return
     * The lastCheckedInAt
     */
    public String getLastCheckedInAt() {
        return lastCheckedInAt;
    }

    /**
     *
     * @param lastCheckedInAt
     * The last_checked_in_at
     */
    public void setLastCheckedInAt(String lastCheckedInAt) {
        this.lastCheckedInAt = lastCheckedInAt;
    }

    /**
     *
     * @return
     * The lastCheckedInLoc
     */
    public String getLastCheckedInLoc() {
        return lastCheckedInLoc;
    }

    /**
     *
     * @param lastCheckedInLoc
     * The last_checked_in_loc
     */
    public void setLastCheckedInLoc(String lastCheckedInLoc) {
        this.lastCheckedInLoc = lastCheckedInLoc;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The points
     */
    public String getPoints() {
        return points;
    }

    /**
     *
     * @param points
     * The points
     */
    public void setPoints(String points) {
        this.points = points;
    }

    public static class Activity {

        @Expose
        private String activity;
        @Expose
        private String latlong;
        @SerializedName("place_name")
        @Expose
        private String placeName;
        @Expose
        private Integer timestamp;

        /**
         *
         * @return
         * The activity
         */
        public String getActivity() {
            return activity;
        }

        /**
         *
         * @param activity
         * The activity
         */
        public void setActivity(String activity) {
            this.activity = activity;
        }

        /**
         *
         * @return
         * The latlong
         */
        public String getLatlong() {
            return latlong;
        }

        /**
         *
         * @param latlong
         * The latlong
         */
        public void setLatlong(String latlong) {
            this.latlong = latlong;
        }

        /**
         *
         * @return
         * The placeName
         */
        public String getPlaceName() {
            return placeName;
        }

        /**
         *
         * @param placeName
         * The place_name
         */
        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        /**
         *
         * @return
         * The timestamp
         */
        public Integer getTimestamp() {
            return timestamp;
        }

        /**
         *
         * @param timestamp
         * The timestamp
         */
        public void setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
        }

    }
}


