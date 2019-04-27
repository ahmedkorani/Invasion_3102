package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.util.Assets;

public class Bullet extends Sprite {

    public Level1 level;
    public Vector2 position;
    public Vector2 velocity;
    private float stateTimer;
    public Bullet(Level1 level, Vector2 position, Vector2 velocity)
    {
        this.level = level;
        this.position = position;
        this.velocity = velocity;
        stateTimer = 0;

        setBounds(position.x,position.y,10 / GameClass.PPM,5 / GameClass.PPM);
        setRegion(Assets.instance.bulletAssets.bulletAnimation.getKeyFrame(stateTimer,true));
    }

    public void update (float delta) {
        stateTimer+=delta;

        setPosition(this.getX() + delta*velocity.x, this.getY());
        setRegion(Assets.instance.bulletAssets.bulletAnimation.getKeyFrame(stateTimer,true));

    }




}
