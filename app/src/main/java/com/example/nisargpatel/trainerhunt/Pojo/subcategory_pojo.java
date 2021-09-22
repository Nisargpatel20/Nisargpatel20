package com.example.nisargpatel.trainerhunt.Pojo;


import android.content.Context;

import java.util.ArrayList;

public class subcategory_pojo {
    private String image;
    private int sid;
    private String name;

    public subcategory_pojo() {

        this.setId(sid);
        this.setName(name);
        this.setImage(image);
            }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return sid;
    }

    public void setId(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}