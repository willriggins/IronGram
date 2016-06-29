package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by will on 6/28/16.
 */
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User sender;

    @ManyToOne
    User recipient;

    @Column(nullable = false)
    String filename;

    LocalDateTime time;

    int duration;

    boolean pub;

    public Photo() {
    }

    public Photo(User sender, User recipient, String filename, int duration, boolean pub) {
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.duration = duration;
        this.pub = pub;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }
}
