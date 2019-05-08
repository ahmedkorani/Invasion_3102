package com.oop.platformer.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class DroneEnemy extends Enemy {

    public DroneEnemy(World world, Vector2 spritePosition, Array<Vector2> path, float[] durations) {
        super(world, spritePosition, path, durations);
    }

    @Override
    public void initSprite() {
        setBounds(0, 0, 25 / GameClass.PPM, 35 / GameClass.PPM);
        setRegion(Assets.instance.droneEnemyAssets.idleAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void updateSprite() {
        setRegion(Assets.instance.droneEnemyAssets.idleAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void setHealthPoints() {
        healthPoints = 3;
    }

    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(spritePosition);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;

        CircleShape shape = new CircleShape();
        shape.setRadius(14 / GameClass.PPM);

        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
    }


}
