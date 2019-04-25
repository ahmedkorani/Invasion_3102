package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;

public class DroneEnemy extends GameObjects {

    private boolean top = false;

    public DroneEnemy(World world, Vector2 position, Level1 level1Screen) {
        super(world, position, level1Screen);
        TextureAtlas atlas = new TextureAtlas(Constants.GIGAGAL_TEXTURE_ATLAS);
        TextureRegion droneEnemy = new TextureRegion(atlas.findRegion(Constants.ENEMY));
        setBounds(0,0,35 / GameClass.PPM,50 / GameClass.PPM);
        setRegion(droneEnemy);
    }

    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / GameClass.PPM);

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }


    public void update() {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if (b2body.getPosition().y >= 2.8f) {
            top = true;
        }

        if (b2body.getLinearVelocity().y == 0) {
            top = false;
        }

        if (!top) {
            b2body.applyForceToCenter(0f, 8.5f, true);
        }
    }

}
