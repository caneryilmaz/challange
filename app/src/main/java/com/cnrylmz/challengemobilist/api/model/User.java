
package com.cnrylmz.challengemobilist.api.model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String profilePhoto;
    private String coverPhoto;
    private String id;
    private String name;
    private String email;
    private String bio;

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profilePhoto);
        dest.writeString(this.coverPhoto);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.bio);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.profilePhoto = in.readString();
        this.coverPhoto = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.bio = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
