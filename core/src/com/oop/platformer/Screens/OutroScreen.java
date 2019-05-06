package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class OutroScreen implements Screen {

    private GameClass gameClass;

    private boolean playerState;
    private boolean playerWon;
    private boolean playerLost;

    private Viewport viewport;

    public OutroScreen(GameClass gameClass, boolean playerState) {
        this.gameClass = gameClass;

        this.playerState = playerState;

        if(playerState)
            playerWon = true;
        else
            playerLost = true;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    private void update(float deltaTime){
        //Begin drawing
        gameClass.batch.begin();
        gameClass.batch.draw(Assets.instance.mainMenuAssets.mainBackground, 0, 0, GameClass.V_WIDTH, GameClass.V_HEIGHT);
        if(playerWon)
            Assets.instance.customFont.font.draw(gameClass.batch,"Your efforts brought you victory",200,300);
        else if(playerLost){
            Assets.instance.customFont.font.draw(gameClass.batch,"You have tried so hard !",200,300);
        }
        //End drawing
        gameClass.batch.end();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
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
