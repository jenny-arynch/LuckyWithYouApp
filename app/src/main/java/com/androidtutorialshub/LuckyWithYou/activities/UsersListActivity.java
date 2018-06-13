package com.androidtutorialshub.LuckyWithYou.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.adapters.UsersRecyclerAdapter;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;

import java.util.List;
import java.util.Random;

/**
 *
 */

public class UsersListActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatActivity activity = UsersListActivity.this;
    private TextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    //private AppCompatButton appCompatButtonBack;
    private String userEmail;
    private ImageView imageView;


    private User currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().setTitle("");





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


        initViews();
        initObjects();
        initListeners();
        setRandomImage();
    }

    private void setUserData(){

        currentUser=databaseHelper.getUser(userEmail);
        //textViewName.setText("Hello, "+currentUser.getName().toString());



    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        //appCompatButtonBack= (AppCompatButton) findViewById(R.id.appCompatButtonBack);
        imageView= (ImageView) findViewById(R.id.imageView);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);

    }
    private void initListeners() {

        //appCompatButtonBack.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        //listUsers = new ArrayList<>();
        databaseHelper = new DatabaseHelper(activity);
        setUserData();
        usersRecyclerAdapter = new UsersRecyclerAdapter(currentUser);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);




       // String emailFromIntent = getIntent().getStringExtra("EMAIL");

       // getDataFromSQLite();
    }
    private void setRandomImage(){

        Random r = new Random();
        int min = 1;
        int max = 6;
        int num = r.nextInt(max - min + 1) + min;

        switch (num) {
            case 1:
                imageView.setImageResource(R.drawable.cancer1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.avatar2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.avatar3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.avatar4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.avatar5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.avatar6);
                break;

        }

        //  imageView.refreshDrawableState();

    }

    /**
     * This method is to fetch all user records from SQLite
     */
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
        Intent intentRegister=null;

        /*
        switch (v.getId()) {
            case R.id.appCompatButtonBack:
                // Navigate to Main
                intentRegister = new Intent(getApplicationContext(), MainActivity.class);
                break;

        }*/
        if (intentRegister != null) {
            intentRegister.putExtra("EMAIL", userEmail.toString());
            startActivity(intentRegister);

        }
    }
}
