package com.androidtutorialshub.LuckyWithYou.activities;

import android.os.Bundle;
import com.androidtutorialshub.LuckyWithYou.snake.HealtySnakeGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class SnakeGameActivity extends AndroidApplication{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new HealtySnakeGame(), config);

    }
}
