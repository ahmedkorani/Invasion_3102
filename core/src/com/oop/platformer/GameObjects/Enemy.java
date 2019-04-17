package com.oop.platformer.GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.platformer.GameObjects.GameObjects;
import com.oop.platformer.Screens.Level1;

public abstract class Enemy extends GameObjects {
    public Enemy(World world, Vector2 position, Level1 level1Screen){
        super(world, position,level1Screen);
    }
}
