package com.example.iptv.ui.favorite;

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
import com.example.iptv.databinding.ActivityFavoriteBinding;
import com.example.iptv.ui.TitleBar;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.DialogUtil;

public class FavoriteActivity extends AppCompatActivity {
    ActivityFavoriteBinding binding;
    FavoriteViewModel favoriteViewModel;
    public static FavoriteActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        binding.setFavorite(favoriteViewModel);

        binding.setTitleBar(new TitleBar(FavoriteActivity.this, R.string.Favorite, View.VISIBLE));

        favoriteViewModel.getCurrentStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch (status.getType()) {
                    case Constant.SHOW_FAV_LIST:
                        LinearLayoutManager manager = new LinearLayoutManager(FavoriteActivity.this);
                        binding.rvFavoriteProgramList.setLayoutManager(manager);
                        binding.rvFavoriteProgramList.addItemDecoration(new DividerItemDecoration(FavoriteActivity.this, DividerItemDecoration.VERTICAL));
                        binding.rvFavoriteProgramList.setAdapter(favoriteViewModel.getmFavoriteProgramAdapter());
                        break;
                    case Constant.SHOW_DIALOG_STATUS:
                        DialogUtil.getInstance().show(FavoriteActivity.this);
                        break;

                    case Constant.DISMISS_DIALOG_STATUS:
                        DialogUtil.getInstance().dismiss();
                        break;
                }
            }
        });

    }

}
