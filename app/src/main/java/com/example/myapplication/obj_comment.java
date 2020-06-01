package com.example.myapplication;

public class obj_comment {
    private String name;
    private String comment;
    private String createddate;
    private String place;


    public obj_comment() {
    }

    public obj_comment(String name, String comment, String createddate, String Place) {
        this.name = name;
        this.comment = comment;
        this.createddate = createddate;
        this.place=Place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}

