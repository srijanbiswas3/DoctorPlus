package com.tesseract.DoctorSaheb;

import android.widget.ImageView;

public class Doctors {

    public String name;
    public String email;
    public String type;
    public String profileimg;
    public Doctors() {
    }

    public Doctors(String name, String email, String type, String profileimg) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.profileimg = profileimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }
}
