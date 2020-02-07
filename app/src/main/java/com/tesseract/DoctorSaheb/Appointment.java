package com.tesseract.DoctorSaheb;

import android.widget.Adapter;

public class Appointment {
    String doctorname;
    String username;
    String status;
    public Appointment(){}
    public Appointment(String doctorname, String username, String status) {
        this.doctorname = doctorname;
        this.username = username;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
