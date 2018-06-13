package com.androidtutorialshub.LuckyWithYou.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;

public class AboutCancerActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton appCompatButtonBrain;
    private AppCompatButton appCompatBreast;
    private AppCompatButton appCompatMelanoma;
    private AppCompatButton appCompatSkin;

    private String userEmail;
    private User currentUser;
    private AppCompatActivity activity = AboutCancerActivity.this;
    private DatabaseHelper databaseHelper;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cancer);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userEmail= null;
            } else {
                userEmail= extras.getString("EMAIL");
            }
        } else {
            userEmail= (String) savedInstanceState.getSerializable("EMAIL");
        }
        databaseHelper = new DatabaseHelper(activity);
        currentUser=databaseHelper.getUser(userEmail);


        initViews();
        initListeners();
        editVisibleButtons();
    }

    private void editVisibleButtons(){
        if(currentUser.getDataDisplay().equals("0")){
            if(currentUser.getCancerType().equals("Brain")){
                appCompatBreast.setVisibility(View.INVISIBLE);
                appCompatMelanoma.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);

            }
            else if(currentUser.getCancerType().equals("Melanoma")){
                appCompatBreast.setVisibility(View.INVISIBLE);
                appCompatButtonBrain.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);
            }
            else if(currentUser.getCancerType().equals("Breast")){
                appCompatMelanoma.setVisibility(View.INVISIBLE);
                appCompatButtonBrain.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);
            }
            else if(currentUser.getCancerType().equals("Skin")){
                appCompatMelanoma.setVisibility(View.INVISIBLE);
                appCompatButtonBrain.setVisibility(View.INVISIBLE);
                appCompatBreast.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            appCompatBreast.setVisibility(View.VISIBLE);
            appCompatMelanoma.setVisibility(View.VISIBLE);
            appCompatSkin.setVisibility(View.VISIBLE);
            appCompatButtonBrain.setVisibility(View.VISIBLE);

        }

    }
    private void initViews() {
        appCompatButtonBrain = (AppCompatButton) findViewById(R.id.appCompatButtonBrain);
        appCompatBreast = (AppCompatButton) findViewById(R.id.appCompatBreast);
        appCompatMelanoma = (AppCompatButton) findViewById(R.id.appCompatMelanoma);
        appCompatSkin = (AppCompatButton) findViewById(R.id.appCompatSkin);

    }
    private void initListeners() {
        appCompatButtonBrain.setOnClickListener(this);
        appCompatBreast.setOnClickListener(this);
        appCompatMelanoma.setOnClickListener(this);
        appCompatSkin.setOnClickListener(this);

    }

    public void onClick(View v) {
        Intent intentRegister;
        intentRegister = new Intent(getApplicationContext(), AboutCancerStart.class);
        intentRegister.putExtra("EMAIL", userEmail.toString());

        switch (v.getId()) {
            case R.id.appCompatButtonBrain: {
                intentRegister.putExtra("CANCER", "Brain");
            }
            case R.id.appCompatBreast: {
                intentRegister.putExtra("CANCER", "Breast");
            }
            case R.id.appCompatMelanoma: {
                intentRegister.putExtra("CANCER", "Melanoma");
            }
            case R.id.appCompatSkin: {
                intentRegister.putExtra("CANCER", "Skin");
            }
        }

        startActivity(intentRegister);

    }
}
