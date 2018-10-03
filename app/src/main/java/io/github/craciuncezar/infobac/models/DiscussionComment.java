package io.github.craciuncezar.infobac.models;

import java.io.Serializable;
import java.util.Date;

public class DiscussionComment implements Serializable {
    private String author;
    private String message;
    private Date createdDate = new Date();
    private int likes = 0;

    public DiscussionComment(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public DiscussionComment(String author, String message, Date createdDate, int likes) {
        this.author = author;
        this.message = message;
        this.createdDate = createdDate;
        this.likes = likes;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public int getLikes() {
        return likes;
    }

    public Date getCreatedDate() { return createdDate; }

    public void incrementLikes(){ likes++;}

    public void removeLike() {likes--;}

}
