package com.example.iptv.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iptv.R;

public class CommonTitleUtil {
    private ImageView mIvBack;
    private TextView mTvTitle;

    public CommonTitleUtil(View v){
        mIvBack = v.findViewById(R.id.iv_back);
        mTvTitle = v.findViewById(R.id.tv_title);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public ImageView getmIvBack() {
        return mIvBack;
    }

    public TextView getmTvTitle() {
        return mTvTitle;
    }
}
