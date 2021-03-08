package com.example.iptv.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


public class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
    private MutableLiveData<Status> mCurrentStatus;
    private Status mStatus = new Status();

    public MutableLiveData<Status> getCurrentStatus() {
        if (mCurrentStatus == null) {
            mCurrentStatus = new MutableLiveData<Status>();
        }
        return mCurrentStatus;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }
}
