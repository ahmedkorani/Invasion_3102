package com.oop.platformer.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.util.Assets;

import com.badlogic.gdx.utils.Array;

public class Enemy extends GameObjects {


//    private Array<Vector2> path;

    Path path;

    public Enemy(World world, Vector2 position, Level1 level1Screen, Array<Vector2> path) {
        super(world, position, level1Screen);
        this.path = new Path(path.size);
        for(Vector2 p : path)
        {
            this.path.AddPoint(p, 0.02f);
        }
        this.path.Reset();


//        this.path = path;


        TextureAtlas atlas = new TextureAtlas(Constants.GIGAGAL_TEXTURE_ATLAS);
        TextureRegion droneEnemy = new TextureRegion(atlas.findRegion(Constants.ENEMY));
        setBounds(0,0,35 / GameClass.PPM,50 / GameClass.PPM);
        setRegion(droneEnemy);
    }

    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type=BodyDef.BodyType.KinematicBody;

        body = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(35 / (2f) / GameClass.PPM,50 / (2f) / GameClass.PPM); //Set As Box takes half width and half height as arguments
        FixtureDef fixtureDef = new FixtureDef();



        fixtureDef.density=1f;
        fixtureDef.restitution=1f;
//        fixtureDef.shape = bodyShape;
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / GameClass.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }
    private int first = 1;
    public void update(float delta)
    {

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if(first ==1) {
            body.setLinearVelocity(this.path.GetVelocity().x*delta, this.path.GetVelocity().y*delta);
            first = 0;
        }

        /*
        Vector2 A = path.get(0), B = path.get(1);
        System.out.println(body.getPosition().y + "A: " + A.y);
        System.out.println(body.getPosition().y + "B: " + B.y);
        if(body.getPosition().y >= A.y*0.01f)
            direction = -1;
        if(body.getPosition().y <= B.y*0.01f)
            direction = 1;
        body.setLinearVelocity((A.x-B.x)*0.01f/(0.02f) *delta, (A.y-B.y)*0.01f/(0.02f)*delta*direction);
        */
        System.out.println("body pos:" + body.getPosition());
        System.out.println("curr pos:" + path.points.get(path.currentPointIndex));
        System.out.println("next pos:" + path.points.get(path.nextPointIndex));


        if(path.UpdatePath(body.getPosition())){
            body.setLinearVelocity(path.GetVelocity().x*delta, path.GetVelocity().y*delta);
        }

    }

//    public Array<Vector2> getPath() {
//        return path;
//    }



    public class Path {
        Array<Vector2> points;
        Array<Float> timeInstants;
        Vector2 velocity;
        int countPoints;
        int currentPointIndex;
        int nextPointIndex;
        int direction = 1;
        static final float CHECK_RADIUS=1f/GameClass.PPM;
         Path(int countPoints){

            points=new Array<Vector2>();
            timeInstants=new Array<Float>();
            velocity=new Vector2();
            this.countPoints = countPoints;


        }
         void AddPoint(Vector2 pos,float time){
            points.add(pos);
            timeInstants.add(time);
        }

         void Reset(){
            currentPointIndex=0;
            nextPointIndex=GetNextPoint();
            SetNextPointVelocity();
        }
         Vector2 GetCurrentPoint(){
            return points.get(currentPointIndex);
        }
        boolean UpdatePath(Vector2 bodyPosition){
            return ReachedNextPoint(bodyPosition);
        }
        boolean ReachedNextPoint(Vector2 bodyPosition){
            Vector2 nextPointPosition=points.get(nextPointIndex);
            float d=nextPointPosition.dst2(bodyPosition);
            System.out.println("D: " + d );
            if(d<CHECK_RADIUS){

                currentPointIndex=nextPointIndex;
                nextPointIndex=GetNextPoint();
                SetNextPointVelocity();
                return true;
            }
            return false;
        }
        int GetNextPoint(){
            int nextPoint=currentPointIndex+direction;
            if(nextPoint==points.size){
                nextPoint=0;
            }else if(nextPoint==-1){
                nextPoint=points.size-1;
            }
            return nextPoint;
        }
        void SetNextPointVelocity(){
            Vector2 nextPosition=points.get(nextPointIndex);
            Vector2 currentPosition=points.get(currentPointIndex);
            float dx=nextPosition.x-currentPosition.x;
            float dy=nextPosition.y-currentPosition.y;
            float time=timeInstants.get(nextPointIndex);
            velocity.set(dx/time,dy/time);
        }
        Vector2 GetVelocity(){
            return velocity;
        }


    }


}


