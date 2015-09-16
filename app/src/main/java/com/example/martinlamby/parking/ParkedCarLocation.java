package com.example.martinlamby.parking;

/**
 * Created by martinlamby on 16.09.15.
 */
public class ParkedCarLocation {

    private double latitude;
    private double longitude;
    private String username;

    public ParkedCarLocation(double latitude, double longitude, String username) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
    }

    public ParkedCarLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
