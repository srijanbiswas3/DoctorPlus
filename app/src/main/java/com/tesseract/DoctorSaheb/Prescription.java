package com.tesseract.DoctorSaheb;


public class Prescription {
    String username;
    String doctorname;
    String image;

    public Prescription() {
    }

    public Prescription(String username, String doctorname, String image) {
        this.username = username;
        this.doctorname = doctorname;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
