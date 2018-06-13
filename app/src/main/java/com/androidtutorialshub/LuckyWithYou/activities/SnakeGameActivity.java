package com.androidtutorialshub.LuckyWithYou.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.androidtutorialshub.LuckyWithYou.snake.HealtySnakeGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class SnakeGameActivity extends AndroidApplication{

    private String userEmail;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new HealtySnakeGame(), config);

    }
}
