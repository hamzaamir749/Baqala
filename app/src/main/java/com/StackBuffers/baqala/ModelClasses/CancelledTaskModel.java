package com.StackBuffers.baqala.ModelClasses;

public class CancelledTaskModel {

    double lat, lng;
    String name, city, canStatus;

    public CancelledTaskModel(double lat, double lng, String name, String city, String canStatus) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.city = city;
        this.canStatus = canStatus;
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

    public String getCanStatus() {
        return canStatus;
    }

    public void setCanStatus(String canStatus) {
        this.canStatus = canStatus;
    }
}
