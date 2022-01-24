package com.example.authentication;

public class LocationHelper {

    private double Longitude;
    private double Latitude;
    private String Names;

    public LocationHelper(double longitude, double latitude, String names) {
        Longitude = longitude;
        Latitude = latitude;
        Names = names;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;


    }

}
