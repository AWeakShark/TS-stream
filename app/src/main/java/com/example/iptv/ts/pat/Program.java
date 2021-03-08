package com.example.iptv.ts.pat;

public class Program {
    int mProgramNumber = 0;
    int mProgramMapPid = 0;

    public Program(int programNumber, int programMapPid) {
        this.mProgramNumber = programNumber;
        this.mProgramMapPid = programMapPid;
    }

    public int getmProgramNumber() {
        return mProgramNumber;
    }

    public int getmProgramMapPid() {
        return mProgramMapPid;
    }
}