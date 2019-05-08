package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.Bullet;
import com.oop.platformer.GameObjects.Enemy;
import com.oop.platformer.GameObjects.Player;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.util.CollisionHandler;
import com.oop.platformer.util.LevelManager;

public abstract class GameLevel implements Screen {
    private GameClass gameClass;
    private OrthographicCamera gameCam; //game camera instance to move with the player character
    private Viewport gamePort; //Manages a Camera and determines how world coordinates are mapped to and from the screen.
    protected TiledMap map;//reference for the map itself
    private Hud hud;
    protected OrthogonalTiledMapRenderer renderer;
    protected World world;

    // for rendering debugging
    protected Box2DDebugRenderer floorDebugger;

    protected Player player;

    protected Array<Bullet> bullets;
    protected Array<Enemy> enemies;

    public GameLevel(GameClass gameClass)
    {
        this.gameClass = gameClass;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(GameClass.V_WIDTH / GameClass.PPM, GameClass.V_HEIGHT / GameClass.PPM, gameCam);
        loadLevelMap();
        hud = new Hud(gameClass.batch);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameClass.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //(0, -8) - Gravity on y equals -8
        world = new World(new Vector2(0, -8), true);
        //Adding contact listener to listen for collisions between bodies, with level manager with our game Objects
        world.setContactListener(CollisionHandler.instance);
        addObjectsToTheWorld();

        setInLevelManager();


    }

    public GameClass getGameClass() {
        return gameClass;
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public Hud getHud() {
        return hud;
    }
    public OrthogonalTiledMapRenderer getRenderer(){
        return renderer;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    protected abstract void loadLevelMap();
    protected abstract void addObjectsToTheWorld();
    protected  abstract void setInLevelManager();

    //update the game state
    private void update(float deltaTime) {
        /*
        set timeStamp and velocity
        to avoid CPU & GPU speed differences
        */
        LevelManager.instance.update(deltaTime);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //separate our update logic from render
//        if(delta >= 0.01f) //experimental
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        // uncomment if you want to see the outline of all bodies in the world
        // floorDebugger.render(world, gameCam.combined);

        gameClass.batch.setProjectionMatrix(gameCam.combined);
        gameClass.batch.begin();

        player.draw(gameClass.batch);

        for (Bullet bullet : bullets) {
            bullet.draw(gameClass.batch);
        }

        for (Enemy enemy : enemies)
            enemy.draw(gameClass.batch);

        gameClass.batch.end();

        //Draw Hud
        gameClass.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
