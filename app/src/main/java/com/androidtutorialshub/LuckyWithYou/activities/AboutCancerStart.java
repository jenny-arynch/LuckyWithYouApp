package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;

public class AboutCancerStart extends AppCompatActivity implements View.OnClickListener{
    private String userEmail;
    private String userPassword;
    private String cansertype;
    private AppCompatButton back;
    private AppCompatButton next;
    private AppCompatButton back2;
    private AppCompatButton next2;
    private TextView textviewabout;
    private int currentIndex=0;
    //private Toolbar toolbar;

    private String[] main_string= {"about1 MAIN", "about2 MAIN","about3 MAIN", "about4 MAIN", "about5 MAIN"};
    private String[] brain_string= {"about1 BRAIN", "about2 BRAIN","about3 BRAIN", "about4 BRAIN", "about5 BRAIN"};
    private String[] breast_string= {"about1 BREAST", "about2 BREAST","about3 BREAST", "about4 BREAST", "about5 BREAST"};
    private String[] melanoma_string= {"about1 MELANOMA", "about2 MELANOMA","about3 MELANOMA", "about4 MELANOMA", "about5 MELANOMA"};
    private String[] skin_string= {"about1 SKIN", "about2 SKIN","about3 SKIN", "about4 SKIN", "about5SKIN"};
    private User currentUser;

    private boolean flipButtons=true;
    private String[] current_string;
    private static int MAX_ABOUT=5;
    private int MAX_NEXT_STRING; //main_string size + next_string size
    private FireBaseHelper firebaseData;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_cancer_start);
        getSupportActionBar();


        firebaseData=new FireBaseHelper(this.getApplicationContext());
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        setEmail(savedInstanceState);
        setCancertype(savedInstanceState);


        initViews();
        initListeners();
        changeButton();
        textviewabout.setText(main_string[currentIndex]);

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
                Intent intentRegister = new Intent(getApplicationContext(), AboutCancerActivity.class);
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

    private void initListeners() {

        back.setOnClickListener(this);
        next.setOnClickListener(this);
        back2.setOnClickListener(this);
        next2.setOnClickListener(this);

    }
    private void initViews() {
        next = (AppCompatButton) findViewById(R.id.next);
        back = (AppCompatButton) findViewById(R.id.back);
        next2 = (AppCompatButton) findViewById(R.id.next2);
        back2 = (AppCompatButton) findViewById(R.id.back2);
        textviewabout =(TextView) findViewById(R.id.textviewabout);

    }
    private void setEmail(Bundle savedInstanceState){
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
      //  currentUser = (User) getIntent().getSerializableExtra("currentUser");

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

        switch (cansertype){
            case "Brain":
                current_string=brain_string;
                MAX_NEXT_STRING=MAX_ABOUT+brain_string.length;
                break;
            case "Breast":
                current_string=breast_string;
                MAX_NEXT_STRING=MAX_ABOUT+breast_string.length;
                break;
            case "Melanoma":
                current_string=melanoma_string;
                MAX_NEXT_STRING=MAX_ABOUT+melanoma_string.length;
                break;
            case "Skin":
                current_string=skin_string;
                MAX_NEXT_STRING=MAX_ABOUT+skin_string.length;
                break;

        }

    }


    public void onClick(View v) {
        Intent intentRegister=null;

        int id=v.getId();
        int buttonCase=-1;
        if(id==R.id.next||id==R.id.next2)
            buttonCase=0;
        else if(id==R.id.back ||id==R.id.back2)
            buttonCase=1;


        switch (buttonCase) {
            case 0:
                if(currentIndex>=0 && currentIndex<MAX_ABOUT-1)
                    setNextText();

                else if(currentIndex>=MAX_ABOUT-1 && currentIndex<MAX_NEXT_STRING-1){
                    setNextTextType();
                }
                else if(currentIndex==MAX_NEXT_STRING-1){
                    textviewabout.setText("Now you can go to PLAY TRIVIA GAME");
                    currentIndex++;
                    next.setText("PLAY");
                }
                else if (currentIndex>MAX_NEXT_STRING-1){
                    intentRegister = new Intent(getApplicationContext(), TriviaGameActivity.class);
                    intentRegister.putExtra("EMAIL", userEmail.toString());
                    intentRegister.putExtra("PASSWORD", userPassword.toString());
                    intentRegister.putExtra("currentUser", currentUser);
                    startActivity(intentRegister);
                    finish();
                }
                changeButton();
                break;

            case 1:
                if(currentIndex>0 && currentIndex<=MAX_ABOUT) {
                    getPrevText();
                    changeButton();
                    break;
                }
                else if(currentIndex>MAX_ABOUT && currentIndex<MAX_NEXT_STRING-1) {
                    getPrevTextType();
                    changeButton();
                    break;

                }
                else{

                    intentRegister = new Intent(getApplicationContext(), AboutCancerActivity.class);
                    intentRegister.putExtra("EMAIL", userEmail.toString());
                    intentRegister.putExtra("PASSWORD", userPassword.toString());
                    intentRegister.putExtra("currentUser", currentUser);

                    startActivity(intentRegister);
                    break;

                }

        }


    }

    private void setNextText(){

        currentIndex++;
        textviewabout.setText(main_string[currentIndex]);

    }

    private void getPrevText(){
        currentIndex--;
        textviewabout.setText(main_string[currentIndex]);

    }
    private void setNextTextType(){
        currentIndex++;

        textviewabout.setText(current_string[currentIndex-MAX_ABOUT]);
    }

    private void getPrevTextType(){
        currentIndex--;
        textviewabout.setText(current_string[currentIndex-MAX_ABOUT]);

    }


    private void changeButton(){
        if(flipButtons){
            back2.setVisibility(View.INVISIBLE);
            next2.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);

            flipButtons=false;
            return;
        }
        else{
            back2.setVisibility(View.VISIBLE);
            next2.setVisibility(View.VISIBLE);
            back.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            flipButtons=true;
            return;
        }
    }

}
