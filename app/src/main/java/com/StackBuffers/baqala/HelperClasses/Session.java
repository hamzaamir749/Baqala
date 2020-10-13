package com.StackBuffers.baqala.HelperClasses;

public class Session {

    int id;
    String image;
    String name;
    String address;

    public Session(int id, String image, String name, String address) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}




