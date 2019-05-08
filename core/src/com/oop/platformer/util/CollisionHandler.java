package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.oop.platformer.GameObjects.Bullet;
import com.oop.platformer.GameObjects.Enemy;
import com.oop.platformer.GameObjects.Player;

// Every time a collision happens in any of box2d world objects all of ContactListener methods trigger
public class CollisionHandler implements ContactListener {

    public static final CollisionHandler instance = new CollisionHandler();

    private CollisionHandler() {}



    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() instanceof Player && fb.getUserData() instanceof Enemy) {
            //invokes a new thread to modify values on bodies while the world is locked in the collision event
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run () {
                    LevelManager.instance.playerIsHit();
                }
            });
        }

        if (fa.getUserData() instanceof Enemy && fb.getUserData() instanceof Bullet) {
            LevelManager.instance.bulletHitEnemy(fa,fb);
        } else if (fa.getUserData() instanceof Bullet && fb.getUserData() instanceof Enemy) {
            LevelManager.instance.bulletHitEnemy(fb, fa);
        } else if (fb.getUserData() instanceof Bullet) {
            LevelManager.instance.bulletHitWall(fb);
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() instanceof Player && fb.getUserData() instanceof Enemy) {
            //disables the default world physics calculations to apply custom bounce effect
            contact.setEnabled(false);
        }
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }


}
