package com.oop.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.*;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.util.CollisionHandler;
import com.oop.platformer.util.LevelManager;


public class Level1 implements Screen {

    private GameClass gameClass;

    private OrthographicCamera gameCam; //game camera instance to move with the player character

    private Viewport gamePort; //Manages a Camera and determines how world coordinates are mapped to and from the screen.

    private TiledMap map;//reference for the map itself

    private Hud hud;
    private OrthogonalTiledMapRenderer renderer;

    private World world;

    // for rendering debugging
    private Box2DDebugRenderer floorDebugger;

    private LevelManager levelManager;

    private Player player;

    public Array<Bullet> bullets;
    public Array<Enemy> enemies;


    public Level1(GameClass gameClass) {

        this.gameClass = gameClass;
        //setup camera and window
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(GameClass.V_WIDTH / GameClass.PPM, GameClass.V_HEIGHT / GameClass.PPM, gameCam);

        //Load Map
        //loads the level from assets
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(Constants.MAP);

        hud = new Hud(gameClass.batch);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameClass.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //(0, -8) - Gravity on y equals -8
        world = new World(new Vector2(0, -8), true);

        renderFloor();

        addObjectsToTheWorld();
        levelManager = new LevelManager(gameClass, this, player, enemies, hud, world, bullets, gameCam);
        //Adding contact listener to listen for collisions between bodies, with level manager with our game Objects
        world.setContactListener(new CollisionHandler(levelManager));
    }

    private void addObjectsToTheWorld() {
        //Adds player to the world in spritePosition (30,90)
        player = new Player(world, new Vector2(450 / GameClass.PPM, 200 / GameClass.PPM)); //!!!!!!!!!Reset this to 90
//        droneEnemyArrayList.add(new DroneEnemy(world,new Vector2(220 / GameClass.PPM, 150 / GameClass.PPM),this));
        bullets = new Array<Bullet>();
        enemies = new Array<Enemy>();


        Array<Vector2> path = new Array<Vector2>();
        Array<Vector2> path2 = new Array<Vector2>();

        path.add(new Vector2(700 / GameClass.PPM, 200 / GameClass.PPM));
        path.add(new Vector2(300 / GameClass.PPM, 200 / GameClass.PPM));

        path2.add(new Vector2(900 / GameClass.PPM, 200 / GameClass.PPM));
        path2.add(new Vector2(800 / GameClass.PPM, 200 / GameClass.PPM));


//        path.add(new Vector2(650/GameClass.PPM, 50/GameClass.PPM));
//        droneEnemy = new Enemy(world, new Vector2(250/GameClass.PPM, 200/ GameClass.PPM), this, path);

        enemies.add(new DroneEnemy(world, path.get(0), path));
//        droneEnemy = new DroneEnemy(world, path.get(0), path);
        enemies.add(new TurretEnemy(world, path2.get(0), path2));
//        turretEnemy = new TurretEnemy(world, path2.get(0), path2);
    }

    private void renderFloor() {
        floorDebugger = new Box2DDebugRenderer();

        //defines what the body consists of
        BodyDef floorBodyDef = new BodyDef();

        PolygonShape floorShape = new PolygonShape();

        //to add bodies to the world
        FixtureDef floorFixtureDef = new FixtureDef();

        Body floor;

        //Create Floor Objects which's in the 4th layer
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            //Shaped as rectangles in the map objects
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            floorBodyDef.type = BodyDef.BodyType.StaticBody;

            //getX return the start of rect then add half of the width to get the center
            //The same for Y
            floorBodyDef.position.set((rect.getX() + rect.getWidth() / 2) / GameClass.PPM, (rect.getY() + rect.getHeight() / 2) / GameClass.PPM);

            floor = world.createBody(floorBodyDef);

            floorShape.setAsBox(rect.getWidth() / 2 / GameClass.PPM, rect.getHeight() / 2 / GameClass.PPM);
            floorFixtureDef.shape = floorShape;
            floor.createFixture(floorFixtureDef).setUserData("Floor");
        }
    }

    //update the game state
    private void update(float deltaTime) {

        levelManager.update(deltaTime);
        /*
        set timeStamp and velocity 
        to avoid CPU & GPU speed differences
        */
        world.step(1 / 60f, 60, 2);
        player.update(deltaTime);

        //Bullet update
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        for (Enemy enemy : enemies)
            enemy.update(deltaTime);

        gameCam.position.x = player.body.getWorldCenter().x;
        gameCam.update();
        renderer.setView(gameCam); //tells our renderer to draw only what camera can see in our game world
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

        floorDebugger.render(world, gameCam.combined); //remove this line to remove green debugging lines on objects

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