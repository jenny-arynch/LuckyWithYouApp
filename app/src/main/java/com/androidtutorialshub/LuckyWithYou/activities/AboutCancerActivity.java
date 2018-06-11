package com.androidtutorialshub.LuckyWithYou.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;
import com.androidtutorialshub.LuckyWithYou.model.User;

public class AboutCancerActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton appCompatButtonBrain;
    private AppCompatButton appCompatBreast;
    private AppCompatButton appCompatMelanoma;
    private AppCompatButton appCompatSkin;

    private String userEmail;
    private String userPassword;
    private User currentUser;
    private AppCompatActivity activity = AboutCancerActivity.this;
    //private DatabaseHelper databaseHelper;
    private FireBaseHelper firebaseData;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cancer);
        getSupportActionBar();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userEmail= null;
                userPassword=null;
            } else {
                userEmail= extras.getString("EMAIL");
                userPassword= extras.getString("PASSWORD");
            }
        } else {
            userEmail= (String) savedInstanceState.getSerializable("EMAIL");
            userPassword= (String) savedInstanceState.getSerializable("PASSWORD");
        }

        //databaseHelper = new DatabaseHelper(activity);
        //currentUser=databaseHelper.getUser(userEmail);

        firebaseData=new FireBaseHelper(this.getApplicationContext());
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        initViews();
        initListeners();
        editVisibleButtons();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_back).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:

                Intent intentRegister = new Intent(getApplicationContext(), MainActivity.class);
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                intentRegister.putExtra("currentUser", currentUser);
                startActivity(intentRegister);
                finish();
                return (true);
            case R.id.action_logout:
                intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                //currentUser=firebaseData.getUser(currentUser.usermail,currentUser.password);
                intentRegister.putExtra("currentUser", currentUser);
                startActivity(intentRegister);
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void editVisibleButtons(){
        if(currentUser.data_display.equals("0")){
            if(currentUser.typeOfCancer.equals("Brain")){
                appCompatBreast.setVisibility(View.INVISIBLE);
                appCompatMelanoma.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);

            }
            else if(currentUser.typeOfCancer.equals("Melanoma")){
                appCompatBreast.setVisibility(View.INVISIBLE);
                appCompatButtonBrain.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);
            }
            else if(currentUser.typeOfCancer.equals("Breast")){
                appCompatMelanoma.setVisibility(View.INVISIBLE);
                appCompatButtonBrain.setVisibility(View.INVISIBLE);
                appCompatSkin.setVisibility(View.INVISIBLE);
            }
            else if(currentUser.typeOfCancer.equals("Skin")){
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
        intentRegister.putExtra("PASSWORD", userPassword.toString());
        intentRegister.putExtra("currentUser", currentUser);


        switch (v.getId()) {
            case R.id.appCompatButtonBrain: {
                intentRegister.putExtra("CANCER", "Brain");
                break;
            }
            case R.id.appCompatBreast: {
                intentRegister.putExtra("CANCER", "Breast");
                break;
            }
            case R.id.appCompatMelanoma: {
                intentRegister.putExtra("CANCER", "Melanoma");
                break;
            }
            case R.id.appCompatSkin: {
                intentRegister.putExtra("CANCER", "Skin");
                break;
            }
        }

        startActivity(intentRegister);

    }
}
