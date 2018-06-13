package com.androidtutorialshub.LuckyWithYou.snake;


import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceView;

import com.androidtutorialshub.LuckyWithYou.R;
import com.androidtutorialshub.LuckyWithYou.activities.SnakeGameActivity;
import com.androidtutorialshub.LuckyWithYou.snake.screen.LoadingScreen;
import com.androidtutorialshub.LuckyWithYou.snake.screen.MenuScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

public class HealtySnakeGame extends Game {

    private AssetManager assetManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        setScreen(new LoadingScreen(this));

    }

    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }


}
