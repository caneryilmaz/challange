package com.cnrylmz.challengemobilist.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cnrylmz.challengemobilist.R;
import com.cnrylmz.challengemobilist.api.ApiModule;
import com.cnrylmz.challengemobilist.api.service.UserInfoService;

import butterknife.ButterKnife;

/**
 * Created by Caner on 08.05.2019.
 */

public abstract class BaseProfileActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        openProfile();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void openProfile();

    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public UserInfoService getUserService() {
        UserInfoService userService = ApiModule.getClient().create(UserInfoService.class);
        return userService;
    }
}
