package com.example.iptv.ts.epginfo;

import java.io.Serializable;

public class ProgramInfoDisplay implements Serializable {
    private String mProgramNumber = "";
    private String mServiceName = "";
    private String time = "";
    private StringBuilder eventText = null;


    public ProgramInfoDisplay(String mProgramNumber, String mServiceName) {
        this.mProgramNumber = mProgramNumber;
        this.mServiceName = mServiceName;
    }

    public ProgramInfoDisplay(String time, StringBuilder eventText) {
        this.time = time;
        this.eventText = eventText;
    }

    public ProgramInfoDisplay(String mProgramNumber, String mServiceName, String time, StringBuilder eventText) {
        this.mProgramNumber = mProgramNumber;
        this.mServiceName = mServiceName;
        this.time = time;
        this.eventText = eventText;
    }

    public String getmProgramNumber() {
        return mProgramNumber;
    }

    public void setmProgramNumber(String mProgramNumber) {
        this.mProgramNumber = mProgramNumber;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StringBuilder getEventText() {
        return eventText;
    }

    public void setEventText(StringBuilder eventText) {
        this.eventText = eventText;
    }
}
