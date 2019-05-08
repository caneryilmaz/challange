package com.cnrylmz.challengemobilist.ui.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cnrylmz.challengemobilist.R;
import com.cnrylmz.challengemobilist.api.model.User;
import com.cnrylmz.challengemobilist.utils.AppUtils;
import com.cnrylmz.challengemobilist.api.model.UserInfoResponse;
import com.cnrylmz.challengemobilist.base.BaseProfileActivity;
import com.cnrylmz.challengemobilist.ui.fragment.UserProfileFragment;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseProfileActivity {

    boolean doubleBackToExitPressedOnce = false;


    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.text_name)
    TextView textName;

    @Bind(R.id.text_bio)
    TextView textBio;

    @Bind(R.id.profile_image)
    CircleImageView profileImage;

    @Bind(R.id.image_cover)
    ImageView coverImage;

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
                        bindInfo(value.getUser());
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

    private void bindInfo(User user) {
        textName.setText(user.getName());
        textBio.setText(user.getBio());
        Glide.with(this).load(user.getProfilePhoto()).into(profileImage);
        Glide.with(this).load(user.getCoverPhoto()).into(coverImage);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
