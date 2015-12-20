package com.angga.project.anggata;


import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class LogHelper {

    private static LogHelper logHelper;
    private List<LogItem> logItems;


    private LogHelper() {
        logItems = new ArrayList<LogItem>();
    }

    public static LogHelper getInstance() {
        if(logHelper == null) {
            logHelper = new LogHelper();
        }
        return logHelper;
    }

    public List<LogItem> getLogItems() {
        return logItems;
    }

    public void addToLog(Location location, long time) {
        LogItem logItem = new LogItem(location, time, calculateVelocity(location, time));
        logItems.add(logItem);
    }

    private double calculateVelocity(Location location, long time) {
        if(logItems.size() == 0)
            return 0;
        else {
            double distance = location.distanceTo(logItems.get(logItems.size()-1).getLocation());
            double speedMPS = distance / time;
            double speedKPS = (speedMPS * 3600.0) / 1000.0;
            return speedKPS;
        }
    }
}
