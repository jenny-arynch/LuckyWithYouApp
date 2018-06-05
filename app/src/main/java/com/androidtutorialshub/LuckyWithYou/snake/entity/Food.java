package com.androidtutorialshub.LuckyWithYou.snake.entity;

import com.androidtutorialshub.LuckyWithYou.snake.config.GameConfig;

public class Food extends EntityBase {

    private boolean available;

    public Food() {
        setSize(GameConfig.FOOD_SIZE, GameConfig.FOOD_SIZE);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
