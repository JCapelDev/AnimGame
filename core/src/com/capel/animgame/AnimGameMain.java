package com.capel.animgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.Rectangle;

public class AnimGameMain implements Screen {
    final AnimGameLauncher game;
    Rectangle skeleton;

    private static final int FRAME_COLS = 7, FRAME_ROWS = 1;

    // Objects used
    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    public Animation<TextureRegion> runningAnimation;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public AnimGameMain(final AnimGameLauncher game) {
        this.game = game;

        walkSheet = new Texture(Gdx.files.internal("TestKeleton1.png"));


        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(0.150f, walkFrames);

        skeleton = new Rectangle();
        skeleton.x = 800 / 2 - 64 / 2;
        skeleton.y = 20;
        skeleton.width = 49;
        skeleton.height = 76;
        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0

        stateTime = 0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        game.spriteBatch.begin();
        //game.spriteBatch.draw(currentFrame, 380, 50); // Draw current frame at (50, 50)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.spriteBatch.draw(walkAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
            skeleton.x -= 100 * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.spriteBatch.draw(walkAnimation.getKeyFrame(stateTime, true), skeleton.x, skeleton.y);
            skeleton.x += 120 * Gdx.graphics.getDeltaTime();

        }else{
            game.spriteBatch.draw(walkAnimation.getKeyFrames()[1], skeleton.x, skeleton.y,skeleton.width,skeleton.height);
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
    }

