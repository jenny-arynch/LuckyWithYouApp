package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.helpers.InputValidation;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;
import com.androidtutorialshub.LuckyWithYou.model.User;

import android.widget.Button;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private String userEmail;
    private String userPassword;


    private static final int ANON_MODE = 100;
    private static final int CREATE_MODE = 101;
    private int buttonMode = ANON_MODE;
    private Button softButton;
    private User currentUser;
    private  FireBaseHelper firebaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar();
        try{
        initViews();
        initListeners();
        initObjects();


            firebaseData=new FireBaseHelper(this.getApplicationContext());

        }
        catch (Exception e){

            textInputEditTextEmail.setText("jen@gmail.com");
            textInputEditTextPassword.setText("1234");

        }

        try {

            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    userEmail = null;
                    userPassword = null;
                } else {
                    userEmail = extras.getString("EMAIL");
                    userPassword = extras.getString("PASSWORD");

                }
            } else {
                userEmail = (String) savedInstanceState.getSerializable("EMAIL");
                userPassword = (String) savedInstanceState.getSerializable("PASSWORD");

            }

            if (userEmail != null && userPassword != null) {
                textInputEditTextEmail.setText(userEmail);
                textInputEditTextPassword.setText(userPassword);

            }
        }
        catch (Exception e){
            textInputEditTextEmail.setText("jen@gmail.com");
            textInputEditTextPassword.setText("1234");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.action_logout).setVisible(true);
        menu.findItem(R.id.action_back).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:
                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finishAndRemoveTask();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromFirebse();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromFirebse(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if(firebaseData.checkForUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim())>=0)
        {
            Intent accountsIntent = new Intent(activity, MainActivity.class);//TODO
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            accountsIntent.putExtra("PASSWORD", textInputEditTextPassword.getText().toString().trim());
            //emptyInputEditText();
            currentUser=firebaseData.getUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim());



            accountsIntent.putExtra("currentUser", currentUser);


            SharedPreferences settings = getSharedPreferences(textInputEditTextEmail.getText().toString(), 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
            editor.commit();

            startActivity(accountsIntent);
        }
        else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }



    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, MainActivity.class);//TODO
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();

            SharedPreferences settings = getSharedPreferences(textInputEditTextEmail.getText().toString(), 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
            editor.commit();

            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
