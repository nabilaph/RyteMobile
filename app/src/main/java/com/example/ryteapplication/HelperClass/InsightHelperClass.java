package com.example.ryteapplication.HelperClass;

public class InsightHelperClass {

    int likesCount;
    int storiesCount;
    String userid;

    public InsightHelperClass() {
    }

    public InsightHelperClass(int likesCount, int storiesCount, String userid) {
        this.likesCount = likesCount;
        this.storiesCount = storiesCount;
        this.userid = userid;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getStoriesCount() {
        return storiesCount;
    }

    public void setStoriesCount(int storiesCount) {
        this.storiesCount = storiesCount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
