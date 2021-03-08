package com.example.iptv.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.iptv.adapter.HistoryAdapter;
import com.example.iptv.base.BaseViewModel;
import com.example.iptv.base.Status;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.TsDataManager;

public class HistorySearchViewModel extends BaseViewModel  {

    private Status mStatus = getStatus();
    private HistoryAdapter historyAdapter;

    public HistorySearchViewModel(@NonNull Application application) {
        super(application);
        historyAdapter = new HistoryAdapter(getApplication().getApplicationContext(), TsDataManager.getInstance().getmHistorList());
    }

    public void onClickDeleteHistory() {
        TsDataManager.getInstance().getmHistorList().clear();
        mStatus.setType(Constant.DELETE_HISTORY_STATUS);
        getCurrentStatus().setValue(mStatus);
    }

    public HistoryAdapter getHistoryAdapter() {
        return historyAdapter;
    }

}
