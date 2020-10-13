package com.StackBuffers.baqala.ModelClasses;

public class NoticeBoardModel {
    String title,description,DateTime;

    public NoticeBoardModel(String title, String description, String dateTime) {
        this.title = title;
        this.description = description;
        this.DateTime = dateTime;
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

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
