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
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.adapters.UsersRecyclerAdapter;
import com.androidtutorialshub.LuckyWithYou.model.User;
import com.androidtutorialshub.LuckyWithYou.snake.HealtySnakeGame;
import com.androidtutorialshub.LuckyWithYou.sql.DatabaseHelper;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SnakeGameActivity extends AndroidApplication {

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new HealtySnakeGame(), config);



       // super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_users_list);
        //getSupportActionBar().setTitle("");





        /**
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
        setRandomImage();*/


    }

}
