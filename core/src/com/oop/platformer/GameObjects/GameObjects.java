package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObjects extends Sprite{
    protected World world;

    protected Body b2body;

    protected Vector2 position;


    public GameObjects(World world, Vector2 position){
        this.world = world;
        this.position = position;
    }

    public abstract void define();
}