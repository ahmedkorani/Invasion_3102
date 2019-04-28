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

public class BulletTest extends GameObjects {

    private Vector2 velocity;

    private TextureRegion bulletRegion;

    public BulletTest(World world, Vector2 position, Vector2 velocity) {
        super(world, position);

        this.velocity = velocity;

        TextureAtlas atlas = new TextureAtlas(Constants.GIGAGAL_TEXTURE_ATLAS);
        bulletRegion = new TextureRegion(atlas.findRegion(Constants.BULLET));

        setBounds(position.x,position.y,10 / GameClass.PPM,5 / GameClass.PPM);
        setRegion(bulletRegion);
    }

    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / GameClass.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float deltaTime) {
        setPosition(this.getX() + deltaTime*velocity.x, this.getY());
        setRegion(bulletRegion);
        body.setLinearVelocity(velocity);
    }
}
