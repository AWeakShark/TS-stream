package com.example.iptv.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.iptv.R;
import com.example.iptv.base.Status;
import com.example.iptv.databinding.FragmentHistorySearchBinding;
import com.example.iptv.utils.Constant;

import java.util.ArrayList;


public class HistorySearchFragment extends Fragment{

    private FragmentHistorySearchBinding mHistorySearchBinding;
    private HistorySearchViewModel mHistoryViewModel;
    public HistorySearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mHistorySearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_history_search,container,false);
        mHistoryViewModel = new ViewModelProvider(this).get(HistorySearchViewModel.class);
        mHistorySearchBinding.setHistoryViewModel(mHistoryViewModel);
        initView();
        return mHistorySearchBinding.getRoot();


    }



    private void initView() {

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mHistorySearchBinding.recyclerViewHistoryProgram.setLayoutManager(manager);
        mHistorySearchBinding.recyclerViewHistoryProgram.setAdapter(mHistoryViewModel.getHistoryAdapter());

        mHistoryViewModel.getCurrentStatus().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch (status.getType()){
                    case Constant.DELETE_HISTORY_STATUS:
                        mHistoryViewModel.getHistoryAdapter().update(new ArrayList<String>());
                        break;
                }
            }
        });

    }


}
