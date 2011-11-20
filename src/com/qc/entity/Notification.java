package com.qc.entity;

public class Notification {
    private int id;
    private String username;
    private int userIcon;
    private String content;
    private int commentId;

    public Notification(int id, String username, int userIcon, String content, int commentId) {
        setId(id);
        setUsername(username);
        setUserIcon(userIcon);
        setContent(content);
        setCommentId(commentId);
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
