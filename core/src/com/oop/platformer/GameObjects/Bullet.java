package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.util.Assets;

public class Bullet extends Sprite {

    private Vector2 position;
    private Vector2 velocity;
    private float stateTimer;

    public Bullet(Vector2 position, Vector2 velocity)
    {
        this.position = position;
        this.velocity = velocity;
        stateTimer = 0;

        setBounds(position.x,position.y,8 / GameClass.PPM,4 / GameClass.PPM);
        setRegion(Assets.instance.bulletAssets.bulletTexture);
    }

    public void update (float delta) {
        stateTimer+=delta;

        setPosition(this.getX() + delta*velocity.x, this.getY());
        setRegion(Assets.instance.bulletAssets.bulletTexture);

    }




}
