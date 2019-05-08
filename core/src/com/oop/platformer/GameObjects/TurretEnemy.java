package com.oop.platformer.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class TurretEnemy extends Enemy {
    public TurretEnemy(World world, Vector2 spritePosition, Array<Vector2> path, float[] durations) {
        super(world, spritePosition, path, durations);
    }

    @Override
    public void initSprite() {

        setBounds(0, 0, 20 / GameClass.PPM, 18 / GameClass.PPM);
        setRegion(Assets.instance.turretEnemyAssets.idleAnimation.getKeyFrame(stateTime, true));

    }

    @Override
    public void updateSprite() {
        setRegion(Assets.instance.turretEnemyAssets.idleAnimation.getKeyFrame(stateTime, true));

    }

    @Override
    public void setHealthPoints() {
        healthPoints = 2;
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
        shape.setRadius(15 / GameClass.PPM);

        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
    }
}
