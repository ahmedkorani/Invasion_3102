package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import com.oop.platformer.Screens.Level1;

public abstract class GameObjects extends Sprite{
    public World world;
    public Body body;
    public Vector2 position;

    public GameObjects(World world, Vector2 position){
        this.world = world;
        this.position = position;
        define();
    }

    public abstract void define();
}