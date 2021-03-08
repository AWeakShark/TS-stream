package com.example.iptv.ui.program;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iptv.R;
import com.example.iptv.base.Status;
import com.example.iptv.databinding.ActivityProgramBinding;
import com.example.iptv.ui.ProgramBottom;
import com.example.iptv.ui.history.HistorySearchFragment;
import com.example.iptv.utils.Constant;
import com.example.iptv.utils.DialogUtil;
import com.example.iptv.utils.SoftKeyboardUtil;
import com.example.iptv.utils.TsDataManager;


public class ProgramActivity extends AppCompatActivity {

    public static ProgramActivity instance = null;
    private HistorySearchFragment mHistorySearchFragment;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private ActivityProgramBinding mActivityProgramBinding;
    private ProgramViewModel mProgramViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        mActivityProgramBinding = DataBindingUtil.setContentView(ProgramActivity.this, R.layout.activity_program);
        mProgramViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);
        mActivityProgramBinding.setViewModel(mProgramViewModel);
        init();
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(ProgramActivity.this);
        mActivityProgramBinding.recyclerViewProgram.setLayoutManager(manager);
        mActivityProgramBinding.recyclerViewProgram.setAdapter(mProgramViewModel.getmProgramInfoAdapter());
        mActivityProgramBinding.recyclerViewProgram.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mActivityProgramBinding.bottom1.setBottom(new ProgramBottom(this, getDrawable(R.drawable.bottom_icom_live), R.string.program_live));
        mActivityProgramBinding.bottom2.setBottom(new ProgramBottom(this, getDrawable(R.drawable.bottom_icon_about_normal), R.string.about));
        mActivityProgramBinding.bottom2.textViewProgramBottom.setTextColor(getResources().getColor(R.color.program_menu_text_about));

//        set editText's font
        Typeface typeface = Typeface.createFromAsset(getAssets(), Constant.FONT_ROBOTO_REGULAR);
        mActivityProgramBinding.editTextSearch.setTypeface(typeface);
        onStatusChanged();
    }

    private void onStatusChanged() {
        mProgramViewModel.getCurrentStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                switch (status.getType()) {
                    case Constant.SHOW_FRAGMENT_STATUS:
                        mHistorySearchFragment = new HistorySearchFragment();
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mActivityProgramBinding.recyclerViewProgram.setVisibility(View.GONE);
                        changeFavToCancel();
                        mFragmentTransaction.add(R.id.fram_layout_history_search, mHistorySearchFragment).commit();
                        break;

                    case Constant.BACK_PROGRAM_LIST_STATUS:
                        mProgramViewModel.getmProgramInfoAdapter().update(TsDataManager.getInstance().getmProgramList());
                        SoftKeyboardUtil.hideSoftKeyboard(ProgramActivity.this);
                        mActivityProgramBinding.editTextSearch.clearFocus();
                        mActivityProgramBinding.editTextSearch.getText().clear();
                        changeCancelToFav();
                        mActivityProgramBinding.recyclerViewProgram.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().hide(mHistorySearchFragment).commit();
                        break;

                    case Constant.SHOW_PROGRAM_LIST_STATUS://change list and show
                        mActivityProgramBinding.recyclerViewProgram.setAdapter(mProgramViewModel.getmProgramInfoAdapter());
                        mActivityProgramBinding.recyclerViewProgram.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().hide(mHistorySearchFragment).commit();
                        break;

                    case Constant.FARVORITE_STATUS:
                        Toast.makeText(ProgramActivity.this, R.string.if_favored, Toast.LENGTH_SHORT).show();
                        break;

                    case Constant.ALREADLY_FARVORITE_STATUS:
                        Toast.makeText(ProgramActivity.this, R.string.favored, Toast.LENGTH_SHORT).show();
                        break;

                    case Constant.SHOW_DIALOG_STATUS1:
                        DialogUtil.getInstance().show(ProgramActivity.this);
                        break;

                    case Constant.DISMISS_DIALOG_STATUS:
                        DialogUtil.getInstance().dismiss();
                        break;
                }
            }
        });
    }

    public void changeFavToCancel() {
        mActivityProgramBinding.textViewFavoriteProgram.setVisibility(View.GONE);
        mActivityProgramBinding.textViewCancel.setVisibility(View.VISIBLE);
    }

    public void changeCancelToFav() {
        mActivityProgramBinding.textViewCancel.setVisibility(View.GONE);
        mActivityProgramBinding.textViewFavoriteProgram.setVisibility(View.VISIBLE);
    }

    public HistorySearchFragment getmHistorySearchFragment() {
        return mHistorySearchFragment;
    }

}

