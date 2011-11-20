package com.qc.entity;

public class Comment {
    private int id;
    private String username;
    private int userIcon;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
