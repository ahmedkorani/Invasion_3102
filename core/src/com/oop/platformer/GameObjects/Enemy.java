package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.util.Assets;

import com.badlogic.gdx.utils.Array;

public class Enemy extends GameObjects {


    private Array<Vector2> path;


    public Enemy(World world, Vector2 position, Level1 level1Screen, Array<Vector2> path) {
        super(world, position, level1Screen);
        this.path = path;

//        setBounds(path.get(0).x, path.get(0).y,10 / GameClass.PPM,5 / GameClass.PPM);
//        setRegion((TextureRegion) Assets.instance.bulletAssets.bulletAnimation.getKeyFrame(0,true));

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
        bodyShape.setAsBox(35 / (2f),50 / (2f)); //Set As Box takes half width and half height as arguments
        FixtureDef fixtureDef = new FixtureDef();



        fixtureDef.density=1f;
        fixtureDef.restitution=1f;
//        fixtureDef.shape = bodyShape;
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / GameClass.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }
    private int direction = 1;
    public void update(float delta)
    {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        Vector2 A = path.get(0), B = path.get(1);
        System.out.println(body.getPosition().y + "A: " + A.y);
        System.out.println(body.getPosition().y + "B: " + B.y);
        if(body.getPosition().y >= A.y*0.01f)
            direction = -1;
        if(body.getPosition().y <= B.y*0.01f)
            direction = 1;
        body.setLinearVelocity((A.x-B.x)*0.01f/(0.02f) *delta, (A.y-B.y)*0.01f/(0.02f)*delta*direction);


    }

    public Array<Vector2> getPath() {
        return path;
    }


}
