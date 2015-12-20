package com.angga.project.anggata;


import android.location.Location;


public class LogItem {
    private Location location;
    private Long time;
    private double v;

    public LogItem(Location location, long time, double v) {
        this.time = time;
        this.location = location;
        this.v = v;
    }

    public Location getLocation() {
        return location;
    }

    public Long getTime() {
        return time;
    }

    public double getV() {
        return v;
    }

    public String getLatString() {
        return String.valueOf(location.getLatitude());
    }

    public String getLonString() {
        return String.valueOf(location.getLongitude());
    }

    public String getVString() {
        return String.valueOf(v);
    }

    public String getTimeString() {
        return String.valueOf(time);
    }


}
