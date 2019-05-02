package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.util.Assets;

public class Enemy extends GameObjects {

    private Path path;
    private TextureRegion droneEnemy;

    public boolean destroyed;
    public boolean isSetToDestroy;
    private int healthPoints = 3;
    private float stateTime;

    public Enemy(World world, Vector2 position, Array<Vector2> path) {
        super(world, position);

        destroyed = false;
        isSetToDestroy = false;
        stateTime = 0;

        this.path = new Path(path.size);
        for (Vector2 p : path) {
            this.path.AddPoint(p, 1f);
        }
        this.path.Reset();

        TextureAtlas atlas = new TextureAtlas(Constants.GIGAGAL_TEXTURE_ATLAS);
        droneEnemy = new TextureRegion(atlas.findRegion(Constants.ENEMY));
        setBounds(0, 0, 35 / GameClass.PPM, 50 / GameClass.PPM);
        setRegion(droneEnemy);
        setRegion(Assets.instance.droneEnemyAssets.idleAnimation.getKeyFrame(stateTime,true));
    }

    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(35 / (2f) / GameClass.PPM, 50 / (2f) / GameClass.PPM); //Set As Box takes half width and half height as arguments
        FixtureDef fixtureDef = new FixtureDef();


        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
//        fixtureDef.shape = bodyShape;
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / GameClass.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float delta) {

        if (isSetToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            Assets.instance.audio.enemyDestroyed.play();
        } else if (!destroyed){
            stateTime+=delta;
            setRegion(Assets.instance.droneEnemyAssets.idleAnimation.getKeyFrame(stateTime, true));
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
<<<<<<< HEAD

            if (path.UpdatePath(body.getPosition())) {

                body.setLinearVelocity(path.GetVelocity().x, path.GetVelocity().y);
                System.out.println(delta + ": " + body.getLinearVelocity());
=======
            if (first == 1) {
                body.setLinearVelocity(this.path.GetVelocity().x * 2, this.path.GetVelocity().y * 2);
                first = 0;
            }

        /*
        Vector2 A = path.get(0), B = path.get(1);
//        System.out.println(body.getPosition().y + "A: " + A.y);
//        System.out.println(body.getPosition().y + "B: " + B.y);
        if(body.getPosition().y >= A.y*0.01f)
            direction = -1;
        if(body.getPosition().y <= B.y*0.01f)
            direction = 1;
        body.setLinearVelocity((A.x-B.x)*0.01f/(0.02f) *delta, (A.y-B.y)*0.01f/(0.02f)*delta*direction);
        */
//            System.out.println("body pos:" + body.getPosition());
//            System.out.println("curr pos:" + path.points.get(path.currentPointIndex));
//            System.out.println("next pos:" + path.points.get(path.nextPointIndex));


            if (path.UpdatePath(body.getPosition())) {
                body.setLinearVelocity(path.GetVelocity().x * 2, path.GetVelocity().y * 2);
>>>>>>> 48dd9c4fe700dbd495cd99e8115933673b400e44
            }
        }

    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
    }

    public boolean setToDestroy() {
        //if the enemy has health points left it's not destroyed and hp is decreased
        if (healthPoints == 0){
            isSetToDestroy = true;
            return true;
        }
        else{
            healthPoints--;
            Assets.instance.audio.enemyHit.play();
            return false;
        }

    }

    public class Path {

        Array<Vector2> points;
        Array<Float> durations;
        Vector2 velocity;

        int countPoints;
        int currentPointIndex;
        int nextPointIndex;

        static final float CHECK_RADIUS = 1f / GameClass.PPM;

        Path(int countPoints) {

            points = new Array<Vector2>();
            durations = new Array<Float>();
            velocity = new Vector2();
            this.countPoints = countPoints;

        }

        void AddPoint(Vector2 pos, float duration) {
            points.add(pos);
            durations.add(duration);
        }

        void Reset() {
            currentPointIndex = 0;
            nextPointIndex = 0;
            SetNextPointVelocity();
        }

        Vector2 GetCurrentPoint() {
            return points.get(currentPointIndex);
        }

        boolean UpdatePath(Vector2 bodyPosition) {
            return ReachedNextPoint(bodyPosition);
        }

        boolean ReachedNextPoint(Vector2 bodyPosition) {
            Vector2 nextPointPosition = points.get(nextPointIndex);
            float d = nextPointPosition.dst2(bodyPosition);
            if (d < CHECK_RADIUS) {

                currentPointIndex = nextPointIndex;
                nextPointIndex = GetNextPoint();
                SetNextPointVelocity();
                return true;
            }
            return false;
        }

        int GetNextPoint() {
            return  (currentPointIndex + 1)%countPoints;

        }

        void SetNextPointVelocity() {
            Vector2 nextPosition = points.get(nextPointIndex);
            Vector2 currentPosition = points.get(currentPointIndex);
            float dx = nextPosition.x - currentPosition.x;
            float dy = nextPosition.y - currentPosition.y;
            float time = durations.get(nextPointIndex);

            velocity.set(dx / time , dy / time);

        }

        Vector2 GetVelocity() {
            return velocity;
        }


    }
}


