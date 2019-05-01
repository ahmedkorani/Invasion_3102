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

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    public StartScreen(GameClass gameClass) {

        this.gameClass = gameClass;

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(GameClass.screenWidth, GameClass.screenHeight, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        //Declaring font and some of it's properties
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.RETRO_FONT));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 54;
        fontParameter.borderWidth = 3;
        fontParameter.borderColor = Color.PINK;
        fontParameter.color = Color.CYAN;
        //Assigning the font generator to the bitmap font
        font = fontGenerator.generateFont(fontParameter);

    }

    private void update(float deltaTime){
        handleInput();
        //Begin drawing
        gameClass.batch.begin();

        gameClass.batch.draw(Assets.instance.mainMenuAssets.mainBackground, 0, 0, GameClass.screenWidth, GameClass.screenHeight);
        font.draw(gameClass.batch, "PRESS ENTER",viewport.getWorldWidth()/4 + 270 , viewport.getWorldHeight()/4);

        //End drawing
        gameClass.batch.end();
    }

    private void handleInput(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.M))
            GameClass.musicPause = !GameClass.musicPause;

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
