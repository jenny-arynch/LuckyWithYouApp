package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.app.Dialog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.model.TriviaQues;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;
import com.androidtutorialshub.LuckyWithYou.model.User;

public class TriviaGameActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatActivity activity = TriviaGameActivity.this;

    private AppCompatButton appCompatButtonTriviaAns;
    private AppCompatRadioButton radioButton1;
    private AppCompatRadioButton radioButton2;
    private AppCompatRadioButton radioButton3;
    private AppCompatRadioButton radioButton4;
    private ImageButton imageHint;
    private TextView hellotxt;
    private TextView score;
    private TextView hintText;
    //private TextView correctAnswer;
    private ImageView imageView;
    private AppCompatButton appCompatButtonBack;
    private ArrayList<TriviaQues> trivQues=new ArrayList<TriviaQues>();
    private int currentQues=-1;
    private int countOfHints=0;
    //private DatabaseHelper databaseHelper;
    private String userPassword;
    private FireBaseHelper firebaseData;
    final Context context = this;
    private String userEmail;
    private User currentUser;

    private Dialog dialog;
    private AppCompatButton start;
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
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
       // currentUser=firebaseData.getUser(userEmail,userPassword);

        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        if(currentUser.score==null)
            currentUser.score="0";

        initViews();
        initListeners();
        readTriviaQues();





        alterDialogStartGame("Start To Play","PLAY");



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
                firebaseData.updateUser(currentUser);
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

    protected void alterDialogStartGame(String title, String message){



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.lightbulb);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("No",

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int id) {
                                //update user data
                                //databaseHelper.updateUser(currentUser);
                                firebaseData.updateUser(currentUser);
                                // if this button is clicked, close
                                // current activity
                                TriviaGameActivity.this.finish();
                            }
                        })
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                                score.setText("Score: ".concat(currentUser.score));
                                startGame();
                            }
                        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    protected void alterDialogHint(String message){

        //correctAnswer.setText("");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Hint");
        alertDialogBuilder.setIcon(R.drawable.lightbulb);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    protected void alterDialogChooseAnswer(){

        //correctAnswer.setText("");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Answer");
        alertDialogBuilder.setIcon(R.drawable.lightbulb);

        // set dialog message
        alertDialogBuilder
                .setMessage("Please choose answer")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    protected Dialog createDialog() {
        dialog = new Dialog(context);
        dialog.setTitle("Start Trivia Game");

       // Context context = TriviaGameActivity.this;
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_trivia);

       // start = (AppCompatButton) dialog.findViewById(R.id.start);

        return dialog;
    }

    private void startGame(){

        //score.setText("Score: ".concat(currentUser.getScore()));
        //correctAnswer.setText("");

        if(radioButton1.isChecked())
            radioButton1.setChecked(false);
        else if(radioButton2.isChecked())
            radioButton2.setChecked(false);
        else if(radioButton3.isChecked())
            radioButton3.setChecked(false);
        else if(radioButton4.isChecked())
            radioButton4.setChecked(false);
        hintText.setText("");

        int index=getQues();

        if(index>=0){
            showQues(index);
        }
        else{
            Intent intentRegister = new Intent(getApplicationContext(), MainActivity.class);

            //show message
            alertUserAboutError();

            //end of the game
            startActivity(intentRegister);
        }

    }

    private void alertUserAboutError() {
      //  Bundle messageArgs = new Bundle();
      //  messageArgs.putString(EndOfGameDialog.TITLE_ID, "New Title");
      //  messageArgs.putString(EndOfGameDialog.MESSAGE_ID, "New Message Body.");

      //  EndOfGameDialog dialog = new EndOfGameDialog();
      //  dialog.setArguments(messageArgs);
     //   dialog.show(getFragmentManager(), "error_dialog");
    }

    private void showQues(int index){

        hellotxt.setText(trivQues.get(index).getQues());
        radioButton1.setText(trivQues.get(index).getAns1());
        radioButton2.setText(trivQues.get(index).getAns2());
        radioButton3.setText(trivQues.get(index).getAns3());
        radioButton4.setText(trivQues.get(index).getAns4());
        currentQues=index;
        setRandomImage();
    }

    private int getQues(){

        Random rand = new Random();
        int size=trivQues.size()-1;
        int index = rand.nextInt(size);
        int countOfQues=1;

        while (trivQues.get(index).checkUsed()!=false){
            if(countOfQues==trivQues.size()){
                index=-1;
                break;
            }
            index = rand.nextInt(size);
            countOfQues++;
        }


        return index;
    }

    private boolean readTriviaQues(){

        boolean result=true;

        TextView txtView = (TextView)findViewById(R.id.hellotxt);

        InputStream inputStream = getResources().openRawResource(R.raw.trivia);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {

                byteArrayOutputStream.write(i);

                i = inputStream.read();

            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //TODO build array of Ques.
        String text=byteArrayOutputStream.toString();

        int index=text.indexOf('#');
        String subQue="";
        TriviaQues q;
        while (index>0){
            subQue=text.substring(0,index+1);
            text=text.substring(index+1, text.length());
            q=new TriviaQues(subQue);
            trivQues.add(q);
            index=text.indexOf('#');
        }



        return  result;
    }

    private void setRandomImage(){

        Random r = new Random();
        int min = 1;
        int max = 10;
        int num = r.nextInt(max - min + 1) + min;

        switch (num) {
            case 1:
                imageView.setImageResource(R.drawable.cancer1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.cancer2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.cancer3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.cancer4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.cancer5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.cancer6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.cancer7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.cancer8);
                break;
            case 9:
                imageView.setImageResource(R.drawable.cancer9);
                break;
            case 10:
                imageView.setImageResource(R.drawable.cancer10);
                break;
        }

      //  imageView.refreshDrawableState();

    }

    private void initViews() {

        appCompatButtonTriviaAns = (AppCompatButton) findViewById(R.id.appCompatButtonTriviaAns);
        appCompatButtonBack = (AppCompatButton) findViewById(R.id.appCompatButtonBack);
        start = (AppCompatButton) findViewById(R.id.start);
        imageView= (ImageView) findViewById(R.id.imageicon);

        radioButton1=(AppCompatRadioButton) findViewById(R.id.radioButton1);
        radioButton2=(AppCompatRadioButton) findViewById(R.id.radioButton2);
        radioButton3=(AppCompatRadioButton) findViewById(R.id.radioButton3);
        radioButton4=(AppCompatRadioButton) findViewById(R.id.radioButton4);
        hellotxt =(TextView) findViewById(R.id.hellotxt);
        hintText =(TextView) findViewById(R.id.hintText);
        score =(TextView) findViewById(R.id.score);

       // correctAnswer =(TextView) findViewById(R.id.correctAnswer);

        imageHint=(ImageButton)findViewById(R.id.imageHint);
    }

    private void initListeners() {
        appCompatButtonTriviaAns.setOnClickListener(this);
        appCompatButtonBack.setOnClickListener(this);
        imageHint.setOnClickListener(this);
        //start.setOnClickListener(this);

    }

    public void onClick(View v) {
        Intent intentRegister;

            switch (v.getId()) {
                case R.id.appCompatButtonTriviaAns: {
                    // TODO
                    if(!radioButton1.isChecked()&&!radioButton2.isChecked()&&!radioButton3.isChecked()&&!radioButton4.isChecked()){
                        alterDialogChooseAnswer();
                        break;
                    }
                    else if (checkAns() == true) {
                        //mark Quest as used
                        trivQues.get(currentQues).markAsUsed();
                        //getNextQues
                        int var = Integer.parseInt(currentUser.score)+10;
                        currentUser.score=String.valueOf(var);
                        score.setText("Score: ".concat(currentUser.score));
                        alterDialogStartGame("Game","Correct Answer!!! Continue to Play?");
                        startGame();

                    }
                    else {
                        //getHint
                        if (countOfHints < 3) {
                            showHint(countOfHints);
                            countOfHints++;
                            showQues(currentQues);
                            break;

                        }
                        //no more hints - show current answer
                        else{
                            countOfHints=0;
                            int var = Integer.parseInt(currentUser.score)+5;
                            currentUser.score=String.valueOf(var);
                            score.setText("Score: ".concat(currentUser.score));
                            alterDialogStartGame("Game","Sorry, Correct Answer: ".concat(trivQues.get(currentQues).getCorrectAns()));
                            startGame();

                        }
                    }
                    break;
                }
                case R.id.appCompatButtonBack: {
                    //update user score
                    //databaseHelper.updateUser(currentUser);
                    firebaseData.updateUser(currentUser);
                    // Navigate to Profile
                    intentRegister = new Intent(getApplicationContext(), MainActivity.class);
                    //send user email
                    if (intentRegister != null) {
                        intentRegister.putExtra("EMAIL", userEmail.toString());
                        intentRegister.putExtra("PASSWORD", userPassword.toString());
                        intentRegister.putExtra("currentuser", currentUser);

                    }

                    startActivity(intentRegister);
                    break;
                }
                case R.id.start: {
                    // dialog.cancel();

                    //  startGame();
                    break;
                }
                case R.id.imageHint: {
                    //getHint
                    if (countOfHints < 3) {
                        showHint(countOfHints);
                        countOfHints++;
                        showQues(currentQues);
                    }
                    break;
                }
            }

    }

    private void showHint(int hintNumber){

        String hint="";
        switch (hintNumber){
            case 0:{
                hint= "First Hint";
                break;
            }
            case 1:
            {
                hint= "Second Hint";
                break;

            }
            case 2:{
                hint="Third Hint";
                break;

            }
        }
        hintText.setText(hint);

        alterDialogHint(hint);
    }


    private boolean checkAns(){
        boolean result=true;

        if(radioButton1.isChecked() && trivQues.get(currentQues).getAns().equals("1"))
        {
            return result;
        }
        else if(radioButton2.isChecked()  && trivQues.get(currentQues).getAns().equals("2"))
        {
            return result;
        }
        else if(radioButton3.isChecked()  && trivQues.get(currentQues).getAns().equals("3"))
        {
            return result;
        }
        else if(radioButton4.isChecked()&& trivQues.get(currentQues).getAns().equals("4"))
        {
            return result;
        }

        return false;
    }

}

