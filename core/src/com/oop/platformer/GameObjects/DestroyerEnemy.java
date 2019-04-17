package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;

public class DestroyerEnemy extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    public DestroyerEnemy(World world, Vector2 position, Level1 level1Screen) {
        super(world, position,level1Screen);
        this.define();
//        frames = new Array<TextureRegion>();
//        for(int i = 0; i < 2; i++){
//            frames.add(new TextureRegion(level1Screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
//        }
//
//        frames.add(new TextureRegion(level1Screen.getAtlas().findRegion("drone-1"), 546, 55, 34, 48));
    }

    @Override
    public void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ GameClass.PPM, 32/GameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(5 / GameClass.PPM);
//        fixtureDef.filter.categoryBits = GameClass.GAME_BIT;
//        fixtureDef.filter.maskBits =
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }
}
