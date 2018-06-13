package com.androidtutorialshub.LuckyWithYou.activities;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.adapters.UsersRecyclerAdapter;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;
import com.androidtutorialshub.LuckyWithYou.sql.FireBaseHelper;
import com.androidtutorialshub.LuckyWithYou.model.User;

import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NestedScrollView nestedScrollView;

    private AppCompatActivity activity = MainActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private AppCompatButton appCompatButtonTrivia;
    private AppCompatButton appCompatButtonProfile;
    private AppCompatButton appCompatButtonForum;
    private AppCompatButton appCompatButtonCancer;
    private AppCompatButton appCompatButtonSnake;
    private String userEmail;
    private String userPassword;
    private User currentUser;
   private FireBaseHelper firebaseData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

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

        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        firebaseData=new FireBaseHelper(this.getApplicationContext());

        try {
            //firebaseData=new FireBaseHelper(this.getApplicationContext());

        }
        catch (Exception e){


        }

        initViews();
        initListeners();
        //initObjects();
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
            case R.id.action_back:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return (true);
            case R.id.action_logout:
                Intent intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
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

    private void initViews() {
       // textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
       // recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        appCompatButtonTrivia = (AppCompatButton) findViewById(R.id.appCompatButtonTrivia);
        appCompatButtonProfile= (AppCompatButton) findViewById(R.id.appCompatButtonProfile);
        appCompatButtonForum= (AppCompatButton) findViewById(R.id.appCompatButtonForum);
        appCompatButtonCancer= (AppCompatButton) findViewById(R.id.appCompatButtonCancer);
        appCompatButtonSnake= (AppCompatButton) findViewById(R.id.appCompatButtonSnake);

    }
    private void initListeners() {
        appCompatButtonTrivia.setOnClickListener(this);
        appCompatButtonProfile.setOnClickListener(this);
        appCompatButtonForum.setOnClickListener(this);
        appCompatButtonCancer.setOnClickListener(this);
        appCompatButtonSnake.setOnClickListener(this);
    }
    private void initObjects() {

     //   usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

     //   RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
      //  recyclerViewUsers.setLayoutManager(mLayoutManager);
     //   recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
     //   recyclerViewUsers.setHasFixedSize(true);
     //   recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public void onClick(View v) {
        Intent intentRegister = null;

        switch (v.getId()) {
            case R.id.appCompatButtonTrivia:
                // Navigate to Trivia
                intentRegister = new Intent(getApplicationContext(), TriviaGameActivity.class);
                break;
            case R.id.appCompatButtonProfile:
                // Navigate to Profile
                intentRegister = new Intent(getApplicationContext(), UsersListActivity.class);
                break;
            case R.id.appCompatButtonForum:
                // Navigate to Profile
                intentRegister = new Intent(getApplicationContext(), WebForumActivity.class);
                break;

            case R.id.appCompatButtonCancer:
                // Navigate to Profile
                intentRegister = new Intent(getApplicationContext(), AboutCancerActivity.class);
                break;

            case R.id.appCompatButtonSnake:
                intentRegister.putExtra("EMAIL", userEmail.toString());
                intentRegister.putExtra("PASSWORD", userPassword.toString());
                currentUser=firebaseData.getUser(currentUser.usermail,currentUser.password);
                intentRegister.putExtra("currentUser", currentUser);


        }

        if (intentRegister != null) {
            intentRegister.putExtra("EMAIL", userEmail.toString());
            intentRegister.putExtra("PASSWORD", userPassword.toString());
            currentUser=firebaseData.getUser(currentUser.usermail,currentUser.password);
            intentRegister.putExtra("currentUser", currentUser);
            startActivity(intentRegister);

        }
    }
}
