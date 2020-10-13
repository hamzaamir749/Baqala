package com.StackBuffers.baqala.ModelClasses;

public class HomeModel {
    double lat, lng;
    int storeId,routeId;
    String name, city;

    public HomeModel(double lat, double lng, int storeId, int routeId, String name, String city) {
        this.lat = lat;
        this.lng = lng;
        this.storeId = storeId;
        this.routeId = routeId;
        this.name = name;
        this.city = city;
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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
}
