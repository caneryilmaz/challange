
package com.cnrylmz.challengemobilist.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserInfoResponse implements Parcelable {

    private User user;
    private List<Feed> feed = null;
    private int feedTotal;

    public User getUser() {
        return user;
    }

    public List<Feed> getFeed() {
        return feed;
    }

    public int getFeedTotal() {
        return feedTotal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.feed);
        dest.writeInt(this.feedTotal);
    }

    public UserInfoResponse() {
    }

    protected UserInfoResponse(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.feed = in.createTypedArrayList(Feed.CREATOR);
        this.feedTotal = in.readInt();
    }

    public static final Parcelable.Creator<UserInfoResponse> CREATOR = new Parcelable.Creator<UserInfoResponse>() {
        @Override
        public UserInfoResponse createFromParcel(Parcel source) {
            return new UserInfoResponse(source);
        }

        @Override
        public UserInfoResponse[] newArray(int size) {
            return new UserInfoResponse[size];
        }
    };
}
