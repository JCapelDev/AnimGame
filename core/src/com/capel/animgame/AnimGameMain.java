package com.capel.animgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;



public class AnimGameMain implements Screen {
    final AnimGameLauncher game;
    Rectangle skeleton;
    OrthographicCamera camera;

    private static final int FRAME_COLS = 7, FRAME_ROWS = 1;

    // Objects used
    Animation<TextureRegion> walkAnimationRight;
    Animation<TextureRegion> walkAnimationLeft;// Must declare frame type (TextureRegion)
    Texture walkSheet;
    Texture walkSheetLeft;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    public Animation<TextureRegion> runningAnimation;

    boolean facing;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public AnimGameMain(final AnimGameLauncher game) {
        this.game = game;

        walkSheet = new Texture(Gdx.files.internal("TestKeleton1.png"));
        backgroundTexture = new Texture("HOMAMBackground.png");
        backgroundSprite =new Sprite(backgroundTexture);


        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFramesRight = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFramesRight[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimationRight = new Animation<TextureRegion>(0.150f, walkFramesRight);

        walkSheetLeft = new Texture(Gdx.files.internal("TestKeletonLeft.png"));


        TextureRegion[][] tmpleft = TextureRegion.split(walkSheetLeft, walkSheetLeft.getWidth() / FRAME_COLS, walkSheetLeft.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFramesLeft = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int indexleft = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFramesLeft[indexleft++] = tmpleft[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimationRight = new Animation<TextureRegion>(0.050f, walkFramesRight);
        walkAnimationLeft = new Animation<TextureRegion>(0.050f, walkFramesLeft);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        skeleton = new Rectangle();
        skeleton.x = 800 / 2 - 64 / 2;
        skeleton.y = 20;
        skeleton.width = 49;
        skeleton.height = 76;
        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0

        stateTime = 0f;
        facing=true;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        camera.update();


        game.spriteBatch.setProjectionMatrix(camera.combined);

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);
        game.spriteBatch.begin();
        renderBackground();
        //game.spriteBatch.draw(currentFrame, 380, 50); // Draw current frame at (50, 50)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.spriteBatch.draw(walkAnimationLeft.getKeyFrame(stateTime,true), skeleton.x, skeleton.y);
            if(skeleton.x > 0) {
                skeleton.x -= 100 * Gdx.graphics.getDeltaTime();
            }
            facing=false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.spriteBatch.draw(walkAnimationRight.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
            if(skeleton.x < 750) {
                skeleton.x += 120 * Gdx.graphics.getDeltaTime();
            }
            facing=true;

        }else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(facing){
                game.spriteBatch.draw(walkAnimationRight.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
                if(skeleton.y < 430)
                skeleton.y += 120 * Gdx.graphics.getDeltaTime();
            }else{
                game.spriteBatch.draw(walkAnimationLeft.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
                if(skeleton.y < 430)
                skeleton.y += 120 * Gdx.graphics.getDeltaTime();
            }

        }else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(facing){
                game.spriteBatch.draw(walkAnimationRight.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
                if(skeleton.y > 0)
                skeleton.y -= 100 * Gdx.graphics.getDeltaTime();
            }else{
                game.spriteBatch.draw(walkAnimationLeft.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
                if(skeleton.y > 0)
                skeleton.y -= 100 * Gdx.graphics.getDeltaTime();
            }
        }else if(!Gdx.input.isTouched()){
            if(facing){
                game.spriteBatch.draw(walkAnimationRight.getKeyFrames()[1], skeleton.x, skeleton.y,skeleton.width,skeleton.height);
            }else {
                game.spriteBatch.draw(walkAnimationLeft.getKeyFrames()[1], skeleton.x, skeleton.y,skeleton.width,skeleton.height);
            }

        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(touchPos.x > 0 && touchPos.x < 750) {
                skeleton.x = (int) (touchPos.x - 64 / 2);
            }
            if(touchPos.y > 0 && touchPos.y < 430) {
                skeleton.y = (int) (touchPos.y - 64 / 2);
            }
            if(touchPos.x < 800/2){
                facing= true;
            }else{
                facing= false;
            }
            if(facing) {
                game.spriteBatch.draw(walkAnimationLeft.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
            }else{
                game.spriteBatch.draw(walkAnimationRight.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
            }
        }
        game.spriteBatch.end();


    }

        @Override
        public void resize ( int width, int height){

        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }

        @Override
        public void dispose () {

            walkSheet.dispose();
        }

        public void renderBackground() {
        backgroundSprite.draw(game.spriteBatch);
    }
    }

