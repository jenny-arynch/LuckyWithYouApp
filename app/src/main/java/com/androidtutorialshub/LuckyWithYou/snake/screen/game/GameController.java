package com.androidtutorialshub.LuckyWithYou.snake.screen.game;

import com.androidtutorialshub.LuckyWithYou.snake.collision.CollisionListener;
import com.androidtutorialshub.LuckyWithYou.snake.common.GameManager;
import com.androidtutorialshub.LuckyWithYou.snake.config.GameConfig;
import com.androidtutorialshub.LuckyWithYou.snake.entity.BodyPart;
import com.androidtutorialshub.LuckyWithYou.snake.entity.Direction;
import com.androidtutorialshub.LuckyWithYou.snake.entity.Food;
import com.androidtutorialshub.LuckyWithYou.snake.entity.Snake;
import com.androidtutorialshub.LuckyWithYou.snake.entity.SnakeHead;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);


    private final CollisionListener listener;

    private Snake snake;
    private float timer;

    private Food food;

    // == constructors ==
    public GameController(CollisionListener listener) {
        this.listener = listener;

        snake = new Snake();
        food = new Food();

        restart();
    }

    // == public methods ==
    public void update(float delta) {
        GameManager.INSTANCE.updateDisplayScore(delta);

        if (GameManager.INSTANCE.isPlaying()) {
            queryInput();
            queryDebugInput();

            timer += delta;
            if (timer >= GameConfig.MOVE_TIME) {
                timer = 0;
                snake.move();

                checkOutOfBounds();
                checkCollision();
            }

            spawnFood();
        } else {
            checkForRestart();
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    // == private methods ==
    private void queryInput() {
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snake.setDirection(Direction.LEFT);
        } else if (rightPressed) {
            snake.setDirection(Direction.RIGHT);
        } else if (upPressed) {
            snake.setDirection(Direction.UP);
        } else if (downPressed) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void queryDebugInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            snake.insertBodyPart();
        }
    }

    private void checkOutOfBounds() {
        SnakeHead head = snake.getHead();

        // check x bounds (left/right)
        if (head.getX() >= GameConfig.WORLD_WIDTH) {
            head.setX(0);
        } else if (head.getX() < 0) {
            head.setX(GameConfig.WORLD_WIDTH - GameConfig.SNAKE_SPEED);
        }

        // check y bounds (up/down)
        if (head.getY() >= GameConfig.MAX_Y) {
            head.setY(0);
        } else if (head.getY() < 0) {
            head.setY(GameConfig.MAX_Y - GameConfig.SNAKE_SPEED);
        }
    }

    private void spawnFood() {
        if (!food.isAvailable()) {
            float foodX = MathUtils.random((int) (GameConfig.WORLD_WIDTH - GameConfig.FOOD_SIZE));
            float foodY = MathUtils.random((int) (GameConfig.MAX_Y - GameConfig.FOOD_SIZE));
            food.setAvailable(true);

            food.setPosition(foodX, foodY);
        }
    }

    private void checkCollision() {
        // head <-> food collision
        SnakeHead head = snake.getHead();
        Rectangle headBounds = head.getBounds();
        Rectangle foodBounds = food.getBounds();

        boolean overlaps = Intersector.overlaps(headBounds, foodBounds);

        if (food.isAvailable() && overlaps) {
            listener.hitFood();
            snake.insertBodyPart();
            food.setAvailable(false);
            GameManager.INSTANCE.incrementScore(GameConfig.FOOD_SCORE);
        }

        // head <-> body parts
        for (BodyPart bodyPart : snake.getBodyParts()) {
            if (bodyPart.isJustAdded()) {
                bodyPart.setJustAdded(false);
                continue;
            }

            Rectangle bodyPartBounds = bodyPart.getBounds();

            if (Intersector.overlaps(bodyPartBounds, headBounds)) {
                listener.lose();
                GameManager.INSTANCE.updateHighScore();
                GameManager.INSTANCE.setGameOver();
            }
        }
    }

    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            restart();
        }
    }

    private void restart() {
        GameManager.INSTANCE.reset();
        snake.reset();
        food.setAvailable(false);
        timer = 0;
    }

}
