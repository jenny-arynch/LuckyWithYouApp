package com.androidtutorialshub.LuckyWithYou.snake.screen.game;

import com.androidtutorialshub.LuckyWithYou.snake.HealtySnakeGame;
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
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static java.lang.Math.abs;

public class GameController implements InputProcessor {

    // == constants ==
    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);
    private static Vector3 temp = new Vector3();

    private OrthographicCamera camera;

    // == attributes ==
    private final CollisionListener listener;

    private Snake snake;
    private float timer;

    private Food food;
	
	private Viewport viewPort;

    private boolean flag = true;

    private static int counter =0;

    // == constructors ==
    public GameController(CollisionListener listener) {
        this.listener = listener;

        snake = new Snake();
        food = new Food();
		
		this.camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(this);

        viewPort = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

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
		/**
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
        }*/
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
	
	@Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        float snakeX = snake.getHead().getX();
        float snakeY = snake.getHead().getY();

        float newTouchX = screenX/118;
        float newTouchY =  screenY/96;

        /**
        boolean aSquare =(0 < screenY && screenY < GameConfig.HEIGHT / 2) && (0 < screenX && screenX < GameConfig.WIDTH / 2);
        boolean bSquare = (GameConfig.WIDTH/2 < screenX && screenX < GameConfig.WIDTH) && (0<screenY && screenY <GameConfig.HEIGHT/2);
        boolean cSquare = (0<screenX&&screenX<GameConfig.WIDTH/2)&&(GameConfig.HEIGHT/2<screenY && screenY<GameConfig.HEIGHT);
        boolean dSquare = (GameConfig.WIDTH/2<screenX&&screenX<GameConfig.WIDTH)&&(GameConfig.HEIGHT/2<screenY&&screenY<GameConfig.HEIGHT);
        */


        if((0 < screenY && screenY < GameConfig.HEIGHT / 2) && (0 < screenX && screenX < GameConfig.WIDTH / 2)){
            //a
            newTouchY = (screenY/96)+15;
            newTouchX = screenX/118;

        }
        else if((GameConfig.WIDTH/2 < screenX && screenX < GameConfig.WIDTH) && (0<screenY && screenY <GameConfig.HEIGHT/2)){
            //b
            newTouchY = (screenY/96)+15;
            newTouchX = screenX/118;

        }
        else if((0<=screenX&&screenX<=GameConfig.WIDTH/2)&&(GameConfig.HEIGHT/2<=screenY && screenY<GameConfig.HEIGHT)){
            //c

            newTouchY = abs((screenY/96)-15);
            newTouchX = screenX/118;

        }
        else if((GameConfig.WIDTH/2<screenX&&screenX<GameConfig.WIDTH)&&(GameConfig.HEIGHT/2<screenY&&screenY<GameConfig.HEIGHT)){
            //d
            newTouchY = abs((screenY/96)-15);
            newTouchX = screenX/118;


        }


        Direction dir = snake.getDirection();

        switch(dir){
            case UP:{
                if(newTouchX>snakeX){
                    snake.setDirection(Direction.RIGHT);
                    break;
                }
                else if(newTouchX<snakeX){
                    snake.setDirection(Direction.LEFT);
                    break;
                }


                break;
            }
            case DOWN:{
                if(newTouchX>snakeX){

                    snake.setDirection(Direction.RIGHT);
                    break;
                }
                else if(newTouchX<snakeX){

                    snake.setDirection(Direction.LEFT);
                    break;
                }

                break;
            }

            case LEFT:{
                if(newTouchY>snakeY){
                    snake.setDirection(Direction.UP);
                    break;
                }
                if(newTouchY<snakeY){
                    snake.setDirection(Direction.DOWN);
                    break;
                }

                break;
            }

            case RIGHT:{

                if(flag){
                    flag=false;
                    snake.setDirection(Direction.UP);
                    break;

                }
                else if(newTouchY>snakeY){
                    snake.setDirection(Direction.UP);
                    break;
                }
                else if(newTouchY<snakeY){

                    snake.setDirection(Direction.DOWN);
                    break;
                }



                break;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Gdx.app.log("Touch Up","");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        Gdx.app.log("Touch Dragged","");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
