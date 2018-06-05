package com.androidtutorialshub.LuckyWithYou.snake.entity;

import com.androidtutorialshub.LuckyWithYou.snake.config.GameConfig;

public class SnakeHead extends EntityBase {

    public SnakeHead() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }

    // == public methods ==
    public void updateX(float amount) {
        x += amount;
        updateBounds();
    }

    public void updateY(float amount) {
        y += amount;
        updateBounds();
    }

}
