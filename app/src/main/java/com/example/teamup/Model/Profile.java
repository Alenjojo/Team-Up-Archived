package com.example.teamup.Model;

public class Profile {
    private String uid;
    private String username;
    private String profile_image;

    public Profile(String uid, String username, String profile_image){
        this.uid = uid;
        this.username = username;
        this.profile_image = profile_image;
    }

    public Profile(){
    }

    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getProfile_image(){
        return profile_image;
    }

    public void setProfile_image(String profile_image){
        this.profile_image = profile_image;
    }
}
