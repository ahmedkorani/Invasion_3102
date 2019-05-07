package com.oop.platformer.Screens;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.*;
import com.oop.platformer.util.LevelManager;

public class Level2 extends GameLevel {

    public Level2(GameClass gameClass) {
        super(gameClass);

    }
    @Override
    protected void loadLevelMap() {
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(Constants.MAP2);
    }

    @Override
    protected void addObjectsToTheWorld() {
        renderFloor();
        Array<Vector2> playerCheckpoints = new Array<Vector2>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            //Shaped as rectangles in the map objects
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            playerCheckpoints.add(new Vector2(rect.getX(), rect.getY()));
        }

        //Adds player to the world in spritePosition (30,90)
        player = new Player(world, new Vector2(52 / GameClass.PPM, 190 / GameClass.PPM), playerCheckpoints); //!!!!!!!!!Reset this to 90

        bullets = new Array<Bullet>();
        enemies = new Array<Enemy>();

        addEnemies();
    }
    private void addEnemies()
    {

        for(int i = 0; i<map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class).size; i+=2)
        {
            Array<Vector2> path = new Array<Vector2>();
            float[] durations = new float[2];
            MapObject object = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class).get(i);

            RectangleMapObject rect = (RectangleMapObject) object;

            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));
            object = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class).get(i+1);
            rect = (RectangleMapObject) object;
            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));

            durations[0] = durations[1] = 3 / path.get(0).dst2(path.get(1));
            enemies.add(new TurretEnemy(world, path.get(0), path, durations));

        }

        for(int i = 0; i<map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).size; i+=4)
        {
            Array<Vector2> path = new Array<Vector2>();
            float[] durations = new float[4];

            MapObject object = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).get(i);
            RectangleMapObject rect = (RectangleMapObject) object;
            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));

            object = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).get(i+1);
            rect = (RectangleMapObject) object;
            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));

            object = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).get(i+2);
            rect = (RectangleMapObject) object;
            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));

            object = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).get(i+3);
            rect = (RectangleMapObject) object;
            path.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));

            durations[0] = durations[1] = durations[2] = durations[3] = 2 / path.get(0).dst2(path.get(1));
            enemies.add(new DroneEnemy(world, path.get(0), path, durations));
        }
        Array<Vector2> enemyPath = new Array<Vector2>();
        float[] enemyDurations;
        for(int i = 0; i<3; i++)
        {
            MapObject object = map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class).get(i);
            RectangleMapObject rect = (RectangleMapObject) object;
            enemyPath.add(new Vector2(rect.getRectangle().getX() / GameClass.PPM, (rect.getRectangle().getY()) / GameClass.PPM));
        }
        enemyDurations = new float[]{0.9f, 1.2f, 1.5f};
        enemies.add(new BossEnemy(world, enemyPath.get(0), enemyPath, enemyDurations));



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

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
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
    @Override
    protected void setInLevelManager() {
        LevelManager.instance.setLevel(this);
    }
}
