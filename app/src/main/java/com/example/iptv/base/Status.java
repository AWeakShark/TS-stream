package com.example.iptv.base;

import androidx.lifecycle.LiveData;

public class Status extends LiveData<Status> {
    private int type;

    public Status(){}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
