package com.cnrylmz.challengemobilist.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cnrylmz.challengemobilist.R;
import com.cnrylmz.challengemobilist.Utils.DateUtil;
import com.cnrylmz.challengemobilist.api.helper.ItemTouchHelperCallback;
import com.cnrylmz.challengemobilist.api.model.Feed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Caner on 08.05.2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ItemTouchHelperCallback.ItemTouchHelperAdapter{

    public List<Feed> itemlist;

    OnClickListener onClickListener;

    @Override
    public void onItemDismiss(int position) {

    }

    public interface OnClickListener {
        void onClick(int pos);
    }

    public RecyclerViewAdapter(List<Feed> itemList) {
        itemlist = itemList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setItemlist(List<Feed> itemlist) {
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_profile_card, parent, false);
        ButterKnife.bind(this, view);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        populateItemRows((ItemViewHolder) viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return itemlist == null ? 0 : itemlist.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_photo)
        ImageView imageFeed;
        @Bind(R.id.feed_name)
        TextView textName;
        @Bind(R.id.feed_follower_count)
        TextView textFollowerCount;
        @Bind(R.id.feed_time)
        TextView textFeedTime;

        @Bind(R.id.feed_follow_image)
        ImageView imageFollow;

        @Bind(R.id.container)
        RelativeLayout containerLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void populateItemRows(final ItemViewHolder viewHolder, final int position) {
        final Feed item = itemlist.get(position);
        viewHolder.imageFollow.setTag(R.drawable.ic_action_followed);

        Glide.with(viewHolder.imageFeed.getContext())
                .load(item.getPhoto()).into(viewHolder.imageFeed);

        viewHolder.textName.setText(item.getName());
        viewHolder.textFollowerCount.setText(item.getFollowerCount() + " " +
                viewHolder.textFollowerCount.getContext().getString(R.string.feed_followers_title));

        viewHolder.textFeedTime.setText(DateUtil.covertTimeToText(item.getCreatedAt()));

        viewHolder.containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTagValidation(viewHolder);
            }
        });
    }

    private int getDrawableId(ImageView iv) {
        return (int) iv.getTag();
    }

    private void setTagValidation(ItemViewHolder viewHolder) {
        switch (getDrawableId(viewHolder.imageFollow)) {
            case R.drawable.ic_action_followed: {
                viewHolder.imageFollow.setImageResource(R.drawable.ic_follow_not);
                viewHolder.imageFollow.setTag(R.drawable.ic_follow_not);
                break;
            }
            case R.drawable.ic_follow_not: {
                viewHolder.imageFollow.setImageResource(R.drawable.ic_action_followed);
                viewHolder.imageFollow.setTag(R.drawable.ic_action_followed);
                break;
            }
        }
    }


}