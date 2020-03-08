package com.tesseract.DoctorSaheb;

public class Event {

    String image,image2;
    String heading;
    String title;
    String description;
    String date;
    String recent;
    String author;
    String link;

    public Event() {
    }

    public Event(String image, String heading, String title, String description, String date, String recent, String authour,String image2,String link) {
        this.image = image;
        this.heading = heading;
        this.title = title;
        this.description = description;
        this.date = date;
        this.recent = recent;
        this.author = author;
        this.image2=image2;
        this.link=link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authour) {
        this.author = authour;
    }
}


