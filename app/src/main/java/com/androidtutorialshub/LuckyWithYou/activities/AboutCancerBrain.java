package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.androidtutorialshub.LuckyWithYou.R;

public class AboutCancerBrain extends AppCompatActivity implements View.OnClickListener{
    private String userEmail;
    private String cansertype;
    private AppCompatButton appCompatback;
    private AppCompatButton appCompatnext;
    private int currentIndex=0;
    private String[] main_string= {"about1 BRAIN", "about2 BRAIN","about3 BRAIN", "about4 BRAIN", "about4 BRAIN"};

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_cancer_brain);

        setEmail(savedInstanceState);
        setCancertype(savedInstanceState);

        initListeners();
        initViews();

        setNextText();//start
    }
    private void initListeners() {
        appCompatback.setOnClickListener(this);
        appCompatnext.setOnClickListener(this);


    }
    private void initViews() {
        appCompatnext = (AppCompatButton) findViewById(R.id.appCompatnext);
        appCompatback = (AppCompatButton) findViewById(R.id.appCompatback);
    }
    private void setEmail(Bundle savedInstanceState){
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
    }
    private void setCancertype(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                cansertype= null;
            } else {
                cansertype= extras.getString("CANCER");
            }
        } else {
            cansertype= (String) savedInstanceState.getSerializable("CANCER");
        }
    }

    public void onClick(View v) {
        Intent intentRegister;
        intentRegister = new Intent(getApplicationContext(), AboutCancerBrain.class);
        intentRegister.putExtra("EMAIL", userEmail.toString());

        switch (v.getId()) {
            case R.id.appCompatback: {
                if(currentIndex<5)
                    getPrevText();

            }
            case R.id.appCompatnext: {
                if(currentIndex>0)
                    setNextText();
            }
        }
        startActivity(intentRegister);

    }

    private void setNextText(){

        appCompatnext.setText(main_string[currentIndex]);                //R.string.main_cancer[currentIndex]);
        currentIndex++;
    }

    private void getPrevText(){
        currentIndex--;
        //appCompatnext.setText(R.string.main_cancer[currentIndex]);
        appCompatnext.setText(main_string[currentIndex]);    }

}
