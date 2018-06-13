package com.androidtutorialshub.LuckyWithYou.snake.screen.game;

import com.androidtutorialshub.LuckyWithYou.snake.HealtySnakeGame;
import com.androidtutorialshub.LuckyWithYou.snake.assets.AssetDescriptors;
import com.androidtutorialshub.LuckyWithYou.snake.collision.CollisionListener;
import com.androidtutorialshub.LuckyWithYou.snake.common.GameManager;
import com.androidtutorialshub.LuckyWithYou.snake.screen.MenuScreen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class GameScreen extends ScreenAdapter {

    private final HealtySnakeGame game;
    private final AssetManager assetManager;
    private final CollisionListener listener;

    private GameController controller;
    private GameRenderer renderer;

    private Sound foodSound;
    private Sound loseSound;

    public GameScreen(HealtySnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();

        listener = new CollisionListener() {
            @Override
            public void hitFood() {
                foodSound.play();
            }

            @Override
            public void lose() {
                loseSound.play();
            }
        };
    }

    @Override
    public void show() {
        foodSound = assetManager.get(AssetDescriptors.FOOD_SOUND);
        loseSound = assetManager.get(AssetDescriptors.LOSE_SOUND);

        controller = new GameController(listener);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        if (GameManager.INSTANCE.isGameOver()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
