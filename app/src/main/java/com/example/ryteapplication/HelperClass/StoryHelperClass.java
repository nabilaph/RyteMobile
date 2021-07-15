package com.example.ryteapplication.HelperClass;

public class StoryHelperClass {

    String date;
    int likesCount;
    String storyContent;
    String userid;
    String username;

    public StoryHelperClass() {
    }

    public StoryHelperClass(String date, int likesCount, String storyContent, String userid, String username) {
        this.date = date;
        this.likesCount = likesCount;
        this.storyContent = storyContent;
        this.userid = userid;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
