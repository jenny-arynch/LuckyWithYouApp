package com.androidtutorialshub.LuckyWithYou.model;


public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String cancer_type;
    private String data_display;
    private String score;

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCancerType() {
        return cancer_type;
    }

    public void setCancerType(String cancer_type) {
        this.cancer_type = cancer_type;
    }

    public String getDataDisplay() {
        return data_display;
    }

    public void setDataDisplay(String data_display) {
        this.data_display = data_display;
    }

    public String getScore() {
        if(score==null)
            score="0";

        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
