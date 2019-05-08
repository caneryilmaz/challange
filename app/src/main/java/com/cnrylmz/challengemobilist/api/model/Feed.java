
package com.cnrylmz.challengemobilist.api.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {

    private String photo;
    private String Name;
    private String FollowerCount;
    private String CreatedAt;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getFollowerCount() {
        return FollowerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.FollowerCount = followerCount;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.CreatedAt = createdAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photo);
        dest.writeString(this.Name);
        dest.writeString(this.FollowerCount);
        dest.writeString(this.CreatedAt);
    }

    public Feed() {
    }

    protected Feed(Parcel in) {
        this.photo = in.readString();
        this.Name = in.readString();
        this.FollowerCount = in.readString();
        this.CreatedAt = in.readString();
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };


}
