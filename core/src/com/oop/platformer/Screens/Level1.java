package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.GameClass;
import com.oop.platformer.Scenes.Hud;


public class Level1 implements Screen {

    private GameClass game; //instance of Game Class to view it through setScreen in GameClass.java

    private OrthographicCamera gameCam; //game camera instance to move with the player character

    private Viewport gamePort; //Manages a Camera and determines how world coordinates are mapped to and from the screen.

    private TmxMapLoader mapLoader; //loads the level from assets

    private TiledMap map;//reference for the map itself

    private  Hud hud;
    private OrthogonalTiledMapRenderer renderer;

    public Level1(GameClass game){

        this.game = game;
        gameCam = new OrthographicCamera();

        //I think StretchViewport is better - Oracle
        gamePort = new FitViewport(game.V_WIDTH,game.V_HEIGHT,gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/untitled.tmx");
        hud = new Hud(game.batch);
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);
    }

    //User Input handling function
    public void handleInput(float deltaTime){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            gameCam.position.x += 150*deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            gameCam.position.x -= 150*deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.F3))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.graphics.setWindowedMode(game.V_WIDTH*2,game.V_HEIGHT*2);

    }


    //update the game state
    public void update(float deltaTime){

        handleInput(deltaTime);
        gameCam.update();
        renderer.setView(gameCam); //tells our renderer to draw only what camera can see in our game world

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        hud.dispose();
    }


}
