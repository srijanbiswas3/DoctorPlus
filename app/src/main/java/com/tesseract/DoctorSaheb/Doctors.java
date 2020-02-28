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
    String location;
    String gender;
    String yoe;
    String mobile;
    String age;

    public Doctors(String name, String email, String type, String profileimg, String about, String qualifications, String workplace, String location, String gender, String yoe,String mobile,String age) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.profileimg = profileimg;
        this.about = about;
        this.qualifications = qualifications;
        this.workplace = workplace;
        this.location = location;
        this.gender = gender;
        this.yoe = yoe;
        this.mobile=mobile;
        this.age=age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Doctors(){
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYoe() {
        return yoe;
    }

    public void setYoe(String yoe) {
        this.yoe = yoe;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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


