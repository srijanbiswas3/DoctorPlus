package com.tesseract.DoctorSaheb;

public class Member {
    private String Email;
    private String Password;
    private String Mobile;
    private String Name;
    private String Gender;

    public Member() {

    }

    public Member(String email, String password, String mobile, String name, String gender) {
        Email = email;
        Password = password;
        Mobile = mobile;
        Name = name;
        Gender = gender;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
