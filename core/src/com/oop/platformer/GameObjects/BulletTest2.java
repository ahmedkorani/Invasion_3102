package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletTest2 {

    private final float SPEED = 100;
    private float x,y;

    private Texture bulletTexture;

    public boolean setToDestroy = false;

    public BulletTest2(float x, float y){
        this.x = x;
        this.y = y;
        bulletTexture = new Texture("Bullets/bullet1.png");

    }

    public void update(float deltaTime){
        y += SPEED * deltaTime;
    }
    public void draw(SpriteBatch batch){
        batch.draw(bulletTexture, x, y);

    }
}
