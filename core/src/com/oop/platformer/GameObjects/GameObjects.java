package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObjects extends Sprite{
    private Vector2 velocity;
    private int health;
    private String weapon;
    public Body b2body;

    public GameObjects(){}

}