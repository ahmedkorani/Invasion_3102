package com.oop.platformer.util;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionHandler implements ContactListener {

    // Every time a collision happens in any of box2d world objects all of ContactListener methods trigger

    //TODO complete collisionHandler to detect Collisions between objects
    // Player - Enemy - Bullet

    @Override
    public void beginContact(Contact contact) {
        //begin of contact
        System.out.println("Collision Started");

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //testing if the contact was null or not
        if(fa == null || fb == null) return;
        System.out.println("Testing collision start 1");
        if(fa.getBody().getUserData() == null || fb.getBody().getUserData() == null) return;

        System.out.println("Testing collision start 2");

    }

    @Override
    public void endContact(Contact contact) {
        //end of contact
        System.out.println("Collision Ended");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
