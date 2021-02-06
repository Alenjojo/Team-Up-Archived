package com.example.teamup.Model;

public class Chats {
    private String message;
    private String reciver;
    private String sender;

    public Chats(String message, String reciver, String sender){
        this.message = message;
        this.reciver = reciver;
        this.sender = sender;
    }

    public Chats(){
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getReciver(){
        return reciver;
    }

    public void setReciver(String reciver){
        this.reciver = reciver;
    }

    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender = sender;
    }
}
