package com.example.iptv.ui.program;

import android.app.Application;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.iptv.adapter.ProgramInfoAdapter;
import com.example.iptv.base.BaseViewModel;
import com.example.iptv.base.Status;
import com.example.iptv.interfaces.OnRecyclerItemClickListener;
import com.example.iptv.interfaces.OnRecyclerItemLongClickListener;
import com.example.iptv.ts.epginfo.ProgramInfoDisplay;
import com.example.iptv.ui.favorite.FavoriteActivity;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.PlayVideoUtil;
import com.example.iptv.utils.TsDataManager;

import java.util.ArrayList;
import java.util.List;

public class ProgramViewModel extends BaseViewModel {

    private ProgramInfoAdapter mProgramInfoAdapter;
    private Status mStatus = getStatus();
    private ProgramInfoDisplay mProgramInfoDisplay = null;
    public ObservableField<String> editTextContent = new ObservableField<>("");
    private boolean mIsSearchList = false;
    private List<ProgramInfoDisplay> mUpdatePrograms;

    public ProgramViewModel(@NonNull final Application application) {
        super(application);

        mProgramInfoAdapter = new ProgramInfoAdapter(application.getApplicationContext(), TsDataManager.getInstance().getmProgramList());
//        click program list
        mProgramInfoAdapter.setRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int Position) {
                mStatus.setType(Constant.SHOW_DIALOG_STATUS1);
                getCurrentStatus().setValue(mStatus);
                if (mIsSearchList) {
                    mProgramInfoDisplay = mUpdatePrograms.get(Position);
                } else {
                    mProgramInfoDisplay = TsDataManager.getInstance().getmProgramList().get(Position);
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        PlayVideoUtil.play(ProgramActivity.instance, Integer.parseInt(mProgramInfoDisplay.getmProgramNumber()), mProgramInfoDisplay.getmServiceName());
                        mStatus.setType(Constant.DISMISS_DIALOG_STATUS);
                        getCurrentStatus().postValue(mStatus);
                    }
                }.start();
            }
        });

//        long click program list
        mProgramInfoAdapter.setRecyclerItemClickLongListener(new OnRecyclerItemLongClickListener() {
            @Override
            public void onRecyclerItemLongClickListener(int Position) {
                boolean isFavorite = false;
                if (mIsSearchList) {
                    mProgramInfoDisplay = mUpdatePrograms.get(Position);
                } else {
                    mProgramInfoDisplay = TsDataManager.getInstance().getmProgramList().get(Position);
                }
                for (int i = 0; i < TsDataManager.getInstance().getmFavList().size(); i++) {
                    if (TsDataManager.getInstance().getmFavList().get(i).getmProgramNumber().equals(mProgramInfoDisplay.getmProgramNumber())) {
                        mStatus.setType(Constant.FARVORITE_STATUS);
                        getCurrentStatus().setValue(mStatus);
                        isFavorite = true;
                        break;
                    }
                }
                if (!isFavorite) {
                    TsDataManager.getInstance().getmFavList().add(mProgramInfoDisplay);
                    mStatus.setType(Constant.ALREADLY_FARVORITE_STATUS);
                    getCurrentStatus().setValue(mStatus);
                }
            }
        });
        getCurrentStatus().setValue(mStatus);
    }


    //    click search icon
    public void onClickSearch() {
        String s = editTextContent.get();
        if (!s.equals("")) {
            for (int i = 0; i < TsDataManager.getInstance().getmHistorList().size(); i++) {
                if (s.equals(TsDataManager.getInstance().getmHistorList().get(i))) {
                    TsDataManager.getInstance().getmHistorList().remove(i);
                }
            }
            TsDataManager.getInstance().getmHistorList().add(s);
        }
    }

    //    click favorite icon
    public void onClickFavorite(View view) {
        Intent intent = new Intent(view.getContext(), FavoriteActivity.class);
        view.getContext().startActivity(intent);
    }

    //    click cancel icon
    public void onClickCancel() {
        mIsSearchList = false;
        mStatus.setType(Constant.BACK_PROGRAM_LIST_STATUS);
        getCurrentStatus().setValue(mStatus);
    }

    //    focus editText
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) {
            mStatus.setType(Constant.SHOW_FRAGMENT_STATUS);
            v.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (ProgramActivity.instance.getmHistorySearchFragment().isVisible() || mIsSearchList) {
                            mStatus.setType(Constant.BACK_PROGRAM_LIST_STATUS);
                            getCurrentStatus().setValue(mStatus);
                        }
                    }
                    return true;
                }
            });
        }
        getCurrentStatus().setValue(mStatus);
    }


    //    when editText changes
    public TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mIsSearchList = true;
            if (s.toString().length() == 0) {
                mUpdatePrograms = TsDataManager.getInstance().getmProgramList();
                mProgramInfoAdapter.update(mUpdatePrograms);
            } else {
                mUpdatePrograms = new ArrayList<>();
                for (ProgramInfoDisplay p : TsDataManager.getInstance().getmProgramList()) {
                    if (p.getmServiceName().toLowerCase().contains(s.toString().toLowerCase())) {
                        mUpdatePrograms.add(p);
                    }
                }
                mProgramInfoAdapter.update(mUpdatePrograms);
                mStatus.setType(Constant.SHOW_PROGRAM_LIST_STATUS);
                getCurrentStatus().setValue(mStatus);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public ProgramInfoAdapter getmProgramInfoAdapter() {
        return mProgramInfoAdapter;
    }

    public ProgramInfoDisplay getmProgramInfoDisplay() {
        return mProgramInfoDisplay;
    }

}
