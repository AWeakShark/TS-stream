package com.example.iptv.ui.selectfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iptv.R;
import com.example.iptv.base.Status;
import com.example.iptv.databinding.ActivityMainBinding;
import com.example.iptv.ui.TitleBar;
import com.example.iptv.ui.program.ProgramActivity;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.DialogUtil;
import com.example.iptv.utils.PermissionUtil;

//只做UI操作，更新ui，根据状态来更新Ui
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;
    private MainViewModel mActivityMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.verifyStoragePermissions(this);//Get access permission
        mActivityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mActivityMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mActivityMainBinding.setModel(mActivityMainViewModel);
//        set Title bar
        mActivityMainBinding.setTitleBar(new TitleBar(MainActivity.this, R.string.select_file, View.INVISIBLE));
        onStatusChanged();
    }


    private void onStatusChanged() {
//        Status changes
        mActivityMainViewModel.getCurrentStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch (status.getType()) {
//                    show dialog
                    case Constant.SHOW_DIALOG_STATUS:
                        DialogUtil.getInstance().show(MainActivity.this);
                        break;
//                    set adapter
                    case Constant.SHOW_FILE_LIST_STATUS:
                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        mActivityMainBinding.recyclerViewFileList.setLayoutManager(manager);
                        mActivityMainBinding.recyclerViewFileList.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                        mActivityMainBinding.recyclerViewFileList.setAdapter(mActivityMainViewModel.getmFileAdapter());
                        break;

                    case Constant.TO_PROGRAM_ACTIVITY:
                        Intent intent = new Intent(MainActivity.this, ProgramActivity.class);
                        startActivity(intent);
                        DialogUtil.getInstance().dismiss();
                        break;
                }
            }
        });
    }
}


