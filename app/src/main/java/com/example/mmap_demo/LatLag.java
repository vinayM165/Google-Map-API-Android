package com.example.mmap_demo;

public class LatLag {
    private double Longitude,Latitude;
    String ID;
    LatLag(){

    }

    public LatLag(double Latitude,double Longitude){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
    public LatLag(String ID,double Latitude,double Longitude){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.ID = ID;
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

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}
