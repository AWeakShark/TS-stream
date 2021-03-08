package com.example.iptv.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.iptv.adapter.FavoriteProgramAdapter;
import com.example.iptv.base.BaseViewModel;
import com.example.iptv.base.Status;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.PlayVideoUtil;
import com.example.iptv.utils.TsDataManager;

public class FavoriteViewModel extends BaseViewModel {
    private Status mStatus = getStatus();
    private FavoriteProgramAdapter mFavoriteProgramAdapter;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);




        mFavoriteProgramAdapter = new FavoriteProgramAdapter(application.getApplicationContext(), TsDataManager.getInstance().getmFavList());
        mStatus.setType(Constant.SHOW_FAV_LIST);
        getCurrentStatus().setValue(mStatus);

        mFavoriteProgramAdapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(final int Position) {
                mStatus.setType(Constant.SHOW_DIALOG_STATUS);
                getCurrentStatus().setValue(mStatus);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        PlayVideoUtil.play(FavoriteActivity.instance, Integer.parseInt(TsDataManager.getInstance().getmFavList().get(Position).getmProgramNumber()),
                                TsDataManager.getInstance().getmFavList().get(Position).getmServiceName());
                        mStatus.setType(Constant.DISMISS_DIALOG_STATUS);
                        getCurrentStatus().postValue(mStatus);
                    }
                }.start();
            }
        });
    }

    public FavoriteProgramAdapter getmFavoriteProgramAdapter() {
        return mFavoriteProgramAdapter;
    }
}
