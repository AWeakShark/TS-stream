package com.example.iptv.ui.about;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.iptv.R;
import com.example.iptv.databinding.ActivityAboutBinding;
import com.example.iptv.ui.TitleBar;
import com.example.iptv.utils.VersionCodeUtil;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;
    AboutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AboutActivity.this, R.layout.activity_about);
        viewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        binding.setAbout(viewModel);
        binding.setTitleBar(new TitleBar(AboutActivity.this,R.string.about, View.VISIBLE));
        viewModel.version = VersionCodeUtil.getVersionCodeUtil(this);

    }

}
