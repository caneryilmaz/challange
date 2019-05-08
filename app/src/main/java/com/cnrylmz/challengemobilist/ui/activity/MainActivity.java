package com.cnrylmz.challengemobilist.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cnrylmz.challengemobilist.R;
import com.cnrylmz.challengemobilist.Utils.AppUtils;
import com.cnrylmz.challengemobilist.api.model.UserInfoResponse;
import com.cnrylmz.challengemobilist.base.BaseProfileActivity;
import com.cnrylmz.challengemobilist.ui.fragment.UserProfileFragment;

import butterknife.Bind;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseProfileActivity {

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void openProfile() {
        if (AppUtils.getInstance().isNetworkConnected(this)) {
            goProfile();
        } else {
            Toast.makeText(this, getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void goProfile() {
        getUserService().getUserInfos(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserInfoResponse value) {
                        fillUserData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void fillUserData(UserInfoResponse response) {
        progressBar.setVisibility(View.GONE);
        addFragment(UserProfileFragment.newInstance(response));
    }

}
