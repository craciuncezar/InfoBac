package io.github.craciuncezar.infobac.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DiscussionThread implements Serializable {
    private String title;
    private String author;
    private String message;
    private int likes;
    private Date dateCreated;
    private ArrayList<DiscussionComment> comments;

    public DiscussionThread(String title, String message, String author,  Date dateCreated, int likes, ArrayList<DiscussionComment> comments) {
        this.title = title;
        this.author = author;
        this.message = message;
        this.likes = likes;
        this.dateCreated = dateCreated;
        this.comments = comments;
    }

    public DiscussionThread(String title, String message, String author){
        this.title = title;
        this.message = message;
        this.author = author;
        this.likes = 0;
        this.dateCreated = new Date();
        this.comments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void removeLike(){
        this.likes--;
    }

    public void incerementLikes(){
        this.likes++;
    }

    public int getCommentsNumber(){
        return this.comments.size();
    }

    public void addComment(DiscussionComment comment){
        this.comments.add(comment);
    }

    public ArrayList<DiscussionComment> getComments() {
        return comments;
    }

}