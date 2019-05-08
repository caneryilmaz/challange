package com.cnrylmz.challengemobilist.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnrylmz.challengemobilist.R;
import com.cnrylmz.challengemobilist.api.model.User;
import com.cnrylmz.challengemobilist.utils.CustomComparatorTime;
import com.cnrylmz.challengemobilist.utils.CustonComparatorFollower;
import com.cnrylmz.challengemobilist.utils.EndlessRecyclerViewScrollListener;
import com.cnrylmz.challengemobilist.api.model.Feed;
import com.cnrylmz.challengemobilist.api.model.UserInfoResponse;
import com.cnrylmz.challengemobilist.base.BaseFragment;
import com.cnrylmz.challengemobilist.ui.activity.MainActivity;
import com.cnrylmz.challengemobilist.ui.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Caner on 08.05.2019.
 */

public class UserProfileFragment extends BaseFragment {

    private static final String USER_DATA = "user_data";

    @Bind(R.id.recycler_profile)
    RecyclerView recyclerProfile;

    @Bind(R.id.text_sorting_time)
    TextView textSortingTime;

    @Bind(R.id.text_sorting_popularity)
    TextView textSortingPopularity;

    @Bind(R.id.button_sorting_time)
    LinearLayout backgroundTimeTab;

    @Bind(R.id.button_sorting_pop)
    LinearLayout backgroundPopTab;

    private UserInfoResponse userData;
    private RecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Feed> allFeeds = new ArrayList<>();

    private Set<Feed> hashFeed = new HashSet<Feed>();


    private LinearLayoutManager linearLayoutManager;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_user_profile;
    }


    public static UserProfileFragment newInstance(UserInfoResponse userInfoResponse) {
        Bundle args = new Bundle();
        args.putParcelable(USER_DATA, userInfoResponse);
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button_sorting_time)
    public void onClickSortingTime() {
        changeTabTimeColor();


        Comparator<Feed> comparator = new CustomComparatorTime();

        Collections.sort(allFeeds, comparator);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.button_sorting_pop)
    public void onClickSortingPopularity() {
        changeTabPopColor();

        Comparator<Feed> comparator = new CustonComparatorFollower();

        Collections.sort(allFeeds, comparator);
        recyclerViewAdapter.notifyDataSetChanged();

    }


    private void changeTabTimeColor() {
        textSortingTime.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        backgroundTimeTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.sorting_selected));

        textSortingPopularity.setTextColor(ContextCompat.getColor(getContext(), R.color.sorting_passive));
        backgroundPopTab.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void changeTabPopColor() {
        textSortingPopularity.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        backgroundPopTab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.sorting_selected));

        textSortingTime.setTextColor(ContextCompat.getColor(getContext(), R.color.sorting_passive));
        backgroundTimeTab.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        userData = getArguments().getParcelable(USER_DATA);
        super.onViewCreated(view, bundle);

        allFeeds.addAll(userData.getFeed());

        initRecycler();
        initAdapter();
        initScrollListener();


    }


    private void initRecycler() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProfile.setLayoutManager(linearLayoutManager);
    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(allFeeds);
        recyclerProfile.setAdapter(recyclerViewAdapter);
    }

    private void initScrollListener() {
        recyclerProfile.setLayoutManager(linearLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPagingData(page);
                ((MainActivity) getActivity()).showProgress();

            }
        };
        recyclerProfile.addOnScrollListener(scrollListener);
    }


    private void loadPagingData(int paging) {
        ((MainActivity) getActivity()).getUserService()
                .getUserInfos(paging)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(final UserInfoResponse value) {
                        List<Feed> moreData = value.getFeed();
                        for (Feed feed : moreData) {
                            if (!hashFeed.contains(feed)) {

                                allFeeds.add(feed);
                                hashFeed.add(feed);

                            }
                        }

                        final int curSize = recyclerViewAdapter.getItemCount();

                        getView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerViewAdapter.notifyItemRangeInserted(curSize, allFeeds.size() - 1);
                                ((MainActivity) getActivity()).hideProgress();
                            }
                        }, 1500);

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

}
