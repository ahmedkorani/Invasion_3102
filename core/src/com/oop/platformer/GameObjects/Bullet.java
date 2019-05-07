package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class Bullet extends GameObject {

    private Vector2 velocity;

    private boolean destroyed;
    private boolean isSetToDestroy;
    private float destroyTimer;

    public Bullet(World world, Vector2 position, boolean playerIsRight) {
        super(world, position);

        if (playerIsRight) {
            if (Assets.instance.bulletAssets.bulletTexture.isFlipX())
                Assets.instance.bulletAssets.bulletTexture.flip(true, false);
            this.velocity = new Vector2(3f, 0.1f);
        } else {
            if (!Assets.instance.bulletAssets.bulletTexture.isFlipX())
                Assets.instance.bulletAssets.bulletTexture.flip(true, false);
            this.velocity = new Vector2(-3f, 0.1f);
        }

        setBounds(position.x, position.y, 8 / GameClass.PPM, 4 / GameClass.PPM);
        setRegion(Assets.instance.bulletAssets.bulletTexture);

        destroyed = false;
        isSetToDestroy = false;
        destroyTimer = 0;
    }

    @Override
    public void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spritePosition);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1 / GameClass.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float deltaTime) {
        destroyTimer += deltaTime;

        if ((destroyTimer >= 1.5f || isSetToDestroy) && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        } else if (!destroyed) {
            body.setLinearVelocity(velocity);

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(Assets.instance.bulletAssets.bulletTexture);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    public void setToDestroy() {
        isSetToDestroy = true;
    }

    public float getPosition() {
        return body.getPosition().x;
    }
}
