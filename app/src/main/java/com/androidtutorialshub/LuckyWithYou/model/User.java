package com.androidtutorialshub.LuckyWithYou.model;


import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {

    public String countofposts;
    public String data_display;
    public String name;
    public String newmessages;
    public String password;
    public String score;
    public String typeOfCancer;
    public String usermail;

    public String toString() {
        return "User [countofposts=" + countofposts
                + ", data_display=" + data_display
                + ", name=" + name
                + ", newmessages=" + newmessages
                + ", password=" + password
                + ", score=" + score
                + ", typeOfCancer=" + typeOfCancer
                +", usermail=" + usermail +"]";
    }
    public void updateUser(User user){

        countofposts = user.countofposts;
        data_display = user.data_display;
        name = user.name;
        newmessages = user.newmessages;
        password = user.password;
        score = user.score;
        typeOfCancer = user.typeOfCancer;
        usermail = user.usermail;


    }


    public User() {
        countofposts = "0";
        data_display = "0";
        name = "DELETETHISUSER";
        newmessages = "0";
        password = "DELETETHISUSER";
        score = "0";
        typeOfCancer = "ALL";
        usermail = "DELETETHISUSER";
    }

    /*
    private User(Parcel in) {
        countofposts = in.readString();
        data_display = in.readString();
        name = in.readString();
        newmessages = in.readString();
        password = in.readString();
        score = in.readString();
        typeOfCancer = in.readString();
        usermail = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countofposts);
        dest.writeString(data_display);
        dest.writeString(name);
        dest.writeString(newmessages);
        dest.writeString(password);
        dest.writeString(score);
        dest.writeString(typeOfCancer);
        dest.writeString(usermail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
*/

/*
    public User() {
        countofposts = "0";
        data_display = "0";
        name = "null";
        newmessages = "0";
        password = "DELETETHISUSER";
        score = "0";
        typeOfCancer = "null";
        usermail = "DELETETHISUSER";
    }


    public void addProp(String key, String val) {

        switch (key) {
            case "countofposts":
                countofposts = val;
                break;
            case "data_display":
                data_display = val;
                break;
            case "name":
                name = val;
                break;
            case "newmessages":
                newmessages = val;
                break;
            case "password":
                password = val;
                break;
            case "typeOfCancer":
                typeOfCancer = val;
                break;
            case "usermail":
                usermail = val;
                break;
            case "score":
                score = val;
                break;
        }

    }
    @Override
    public String toString() {
        return "User{" +
                "countofposts='" + countofposts + '\'' +
                ", data_display=" + data_display +'\'' +
                ", name=" + name +'\'' +
                ", newmessages=" + newmessages +'\'' +
                ", password=" + password +'\'' +
                ", usermail=" + usermail +'\'' +
                ", score=" + score +
                '}';
    }*/

    public String getcountofposts() {
        return countofposts;
    }

    public void setcountofposts(String countofposts) {
        this.countofposts = countofposts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getnewmessages() {
        return newmessages;
    }

    public void setnewmessages(String newmessages) {
        this.newmessages = newmessages;
    }

    public String getEmail() {
        return typeOfCancer;
    }

    public void setEmail(String usermail) {
        this.usermail = usermail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCancerType() {
        return typeOfCancer;
    }

    public void setCancerType(String typeOfCancer) {
        this.typeOfCancer = typeOfCancer;
    }

    public String getDataDisplay() {
        return data_display;
    }

    public void setDataDisplay(String data_display) {
        this.data_display = data_display;
    }

    public String getScore() {
        if (score == null)
            score = "0";

        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}



