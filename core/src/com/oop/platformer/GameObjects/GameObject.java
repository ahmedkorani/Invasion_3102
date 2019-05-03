package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public abstract class GameObject extends Sprite {
    public World world;
    public Body body;
    public Vector2 spritePosition;

    public GameObject(World world, Vector2 spritePosition) {
        this.world = world;
        this.spritePosition = spritePosition;
        define();
    }

    public abstract void define();

    public abstract void update(float delta);
}