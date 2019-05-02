package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;


public class StartScreen implements Screen {

    private GameClass gameClass;

    private Viewport viewport;

    public StartScreen(GameClass gameClass) {

        this.gameClass = gameClass;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();



    }

    private void update(float deltaTime){
        handleInput();
        //Begin drawing
        gameClass.batch.begin();

        gameClass.batch.draw(Assets.instance.mainMenuAssets.mainBackground, 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        Assets.instance.customFont.font.draw(gameClass.batch, "PRESS ENTER",viewport.getWorldWidth()/4 + 270 , viewport.getWorldHeight()/4);

        //End drawing
        gameClass.batch.end();
    }

    private void handleInput(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.M))
            GameClass.pauseMusic = !GameClass.pauseMusic;

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            gameClass.beginIntro();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
