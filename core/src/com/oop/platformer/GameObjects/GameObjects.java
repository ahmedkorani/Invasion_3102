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
    public Level1 level1Screen;

    public GameObjects(World world, Vector2 position, Level1 level1Screen){
        this.world = world;
        this.position = position;
        this.level1Screen = level1Screen;
        define();
    }

    public abstract void define();
}