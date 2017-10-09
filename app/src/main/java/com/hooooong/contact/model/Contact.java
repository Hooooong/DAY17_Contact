package com.hooooong.contact.model;

/**
 * Created by Android Hong on 2017-09-26.
 */

public class Contact {
    private int id;
    private String name;
    private String number;
    private String thumbnail;

    public Contact() {
    }

    public Contact(int id, String name, String number, String thumbnail) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
