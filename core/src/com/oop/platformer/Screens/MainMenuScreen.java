package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.Player;

public class MainMenuScreen implements Screen {

    private GameClass gameClass;

    public MainMenuScreen(GameClass gameClass){
        this.gameClass = gameClass;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameClass.setScreen(new Level1());
        }

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameClass.batch.begin();

        GameClass.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
