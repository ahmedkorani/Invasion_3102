package com.oop.platformer.util;

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
//            levelManager.playerIsHit();
            LevelManager.instance.playerIsHit();
            System.out.println("Player was hit by an Enemy");
        }

        if (fa.getUserData() instanceof Enemy && fb.getUserData() instanceof Bullet) {
//            levelManager.bulletHitEnemy(fa, fb);
            LevelManager.instance.bulletHitEnemy(fa,fb);
            System.out.println("Enemy was hit by a bullet");
        } else if (fa.getUserData() instanceof Bullet && fb.getUserData() instanceof Enemy) {
//            levelManager.bulletHitEnemy(fb, fa);
            LevelManager.instance.bulletHitEnemy(fb, fa);
            System.out.println("Enemy was hit by a bullet");
        } else if (fb.getUserData() instanceof Bullet) {
//            levelManager.bulletHitWall(fb);
            LevelManager.instance.bulletHitWall(fb);
            System.out.println("Bullet was destroyed by a wall");
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }


}
