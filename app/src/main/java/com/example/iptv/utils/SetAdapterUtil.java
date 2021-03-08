package com.example.iptv.utils;

import android.content.Context;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SetAdapterUtil {

    public static void setAdapter(Context context, RecyclerView rvItem,RecyclerView.Adapter adaptor){
        rvItem.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(manager);
        rvItem.setAdapter(adaptor);
    }
}
