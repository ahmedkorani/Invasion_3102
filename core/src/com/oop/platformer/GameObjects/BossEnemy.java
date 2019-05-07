package com.oop.platformer.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class BossEnemy extends  Enemy {



    public BossEnemy(World world, Vector2 spritePosition, Array<Vector2> path, float[] durations) {
        super(world, spritePosition, path, durations);
    }

    @Override
    public void initSprite() {
        setBounds(0, 0, 90 / GameClass.PPM, 90 / GameClass.PPM);
        setRegion(Assets.instance.bossEnemyAssets.flyingAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void updateSprite() {
        setRegion(Assets.instance.bossEnemyAssets.flyingAnimation.getKeyFrame(stateTime, true));

        if(body.getLinearVelocity().x > 0)
            setFlip(false, false);
        else
            setFlip(true, false);

    }

    @Override
    public void setHealthPoints() {
        healthPoints = 10;
    }

    @Override
    public void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(spritePosition);
        body = world.createBody(bodyDef);
        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(153 / (2f) / GameClass.PPM, 256 / (2f) / GameClass.PPM);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
//        fixtureDef.shape = bodyShape;
        CircleShape shape = new CircleShape();
        shape.setRadius(45 / GameClass.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }
}
