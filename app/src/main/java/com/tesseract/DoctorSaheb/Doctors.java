package com.tesseract.DoctorSaheb;

import android.widget.ImageView;

public class Doctors {

    String name;
    String email;
    String type;
    String profileimg;
    String about;
    String qualifications;
    String workplace;

    public Doctors() {
    }
    public Doctors(String name, String email, String type, String profileimg, String about, String qualifications, String workplace) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.profileimg = profileimg;
        this.about = about;
        this.qualifications = qualifications;
        this.workplace = workplace;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
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
