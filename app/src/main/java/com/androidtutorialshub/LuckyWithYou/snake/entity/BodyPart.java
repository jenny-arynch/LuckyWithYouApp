package com.androidtutorialshub.LuckyWithYou.snake.entity;

import com.androidtutorialshub.LuckyWithYou.snake.config.GameConfig;
import com.androidtutorialshub.LuckyWithYou.snake.entity.EntityBase;

public class BodyPart extends EntityBase {

    private boolean justAdded = true;

    public BodyPart() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }

    public boolean isJustAdded() {
        return justAdded;
    }

    public void setJustAdded(boolean justAdded) {
        this.justAdded = justAdded;
    }

}
