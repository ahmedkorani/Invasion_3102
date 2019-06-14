package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class OutroScreen implements Screen {

    public static boolean playerWon;
    public static boolean playerLost;
    private GameClass gameClass;
    private Viewport viewport;

    public OutroScreen(GameClass gameClass, boolean playerState) {
        this.gameClass = gameClass;

        playerWon = false;
        playerLost = false;

        if (playerState)
            playerWon = true;
        else
            playerLost = true;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    private void update(float deltaTime) {
        //Begin drawing
        gameClass.batch.begin();
        gameClass.batch.draw(Assets.instance.mainMenuAssets.mainBackground, 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        Assets.instance.customFont.font.draw(gameClass.batch, "Space Go to Main Menu", 30, 1050);
        Assets.instance.customFont.font.draw(gameClass.batch, "ESC Exit", 1650, 1050);
        if (playerWon) {
            Assets.instance.customFont.font.draw(gameClass.batch, "Your efforts have brought you to victory!", 280, 300);
            Assets.instance.customFont.font.draw(gameClass.batch, "Robots have left the city.", 520, 200);
        } else if (playerLost) {
            Assets.instance.customFont.font.draw(gameClass.batch, "Better luck next time.", 600, 300);
            Assets.instance.customFont.font.draw(gameClass.batch, "Robots have conquered the city!", 450, 200);
        }
        //End drawing
        gameClass.batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (Assets.instance.audio.winMusic.isPlaying()) {
                Assets.instance.audio.winMusic.stop();
            }

            if (Assets.instance.audio.loseMusic.isPlaying()) {
                Assets.instance.audio.loseMusic.stop();
            }

            gameClass.endOutro();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            GameClass.isMusicPaused = !GameClass.isMusicPaused;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        gameClass.batch.setProjectionMatrix(viewport.getCamera().combined);

        update(delta);
        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
