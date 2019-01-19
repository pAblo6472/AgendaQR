package com.example.personal.agenda;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Contact extends SugarRecord implements Serializable {
    public String image;
    public String name;
    public String phone;
    public String lat;
    public String lng;

    //constructor
    public Contact(String image, String name, String phone, String lat, String lng) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
    }

    public Contact() {
    }
}
