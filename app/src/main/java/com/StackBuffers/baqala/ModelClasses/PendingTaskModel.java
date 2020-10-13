package com.StackBuffers.baqala.ModelClasses;

public class PendingTaskModel {

    double lat, lng;
    String name, city, pStatus;

    public PendingTaskModel(double lat, double lng, String name, String city, String pStatus) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.city = city;
        this.pStatus = pStatus;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }
}
