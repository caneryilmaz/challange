package com.cnrylmz.challengemobilist.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnrylmz.challengemobilist.R;

import butterknife.ButterKnife;

/**
 * Created by Caner on 08.05.2019.
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @LayoutRes
    public abstract int setLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(getActivity());
    }
}
