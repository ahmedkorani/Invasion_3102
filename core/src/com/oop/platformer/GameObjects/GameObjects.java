package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObjects extends Sprite{
    public World world;

    public Body b2body;


    private Vector2 velocity;
    private int health;
    private String weapon;

    public GameObjects(World world){
        this.world = world;
    }

    public abstract void define();
}