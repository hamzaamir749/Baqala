package com.StackBuffers.baqala.ModelClasses;

public class HistoryModel {

    double lat, lng;
    String name, city, tStatus,route_id;

    public HistoryModel(double lat, double lng, String name, String city, String tStatus, String route_id) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.city = city;
        this.tStatus = tStatus;
        this.route_id = route_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
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

    public String gettStatus() {
        return tStatus;
    }

    public void settStatus(String tStatus) {
        this.tStatus = tStatus;
    }
}
