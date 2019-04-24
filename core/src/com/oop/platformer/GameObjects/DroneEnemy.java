package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;

public class DroneEnemy extends GameObjects {

    private boolean top = false;
    private boolean bottom = true;

    public DroneEnemy(World world, Vector2 position, Level1 level1Screen) {
        super(world, position, level1Screen);
        TextureAtlas atlas = new TextureAtlas(Constants.TEXTURE_ATLAS);
        TextureRegion droneEnemy = new TextureRegion(atlas.findRegion(Constants.enemy));
        setBounds(0,0,35 / GameClass.PPM,50 / GameClass.PPM);
        setRegion(droneEnemy);
    }

    @Override
    public void define() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / GameClass.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }


    public void update(){
        setPosition(b2body.getPosition().x - getWidth()/2 , b2body.getPosition().y - getHeight()/2);

        if(b2body.getPosition().y >= 2.8f){
            top = true;
            bottom = false;
        }

        if(b2body.getLinearVelocity().y == 0){
            bottom = true;
            top = false;
        }

        if (!top){
            b2body.applyForceToCenter(0f, 8.5f, true);
        }

        if (!bottom){
//            b2body.applyForceToCenter(0f, 2f, true);
//            b2body.applyLinearImpulse(new Vector2(0, 0f), b2body.getWorldCenter(), true);
        }
        System.out.println("speed : " + b2body.getLinearVelocity().y);
//        if (b2body.getPosition().y >= 3){
//            System.out.println(b2body.getPosition().y);
//            top = true;
//        }
//
//        if (b2body.getLinearVelocity().y == 0){
//            top = false;
//            bottom = true;
//        }
//
//        if (b2body.getLinearVelocity().y <= 0 && !top){
//            b2body.applyLinearImpulse(new Vector2(0f,1f), b2body.getWorldCenter(),true);
//            b2body.applyForceToCenter(0f, 15f, true);
//        }
//
//        if (b2body.getLinearVelocity().y < 0){
//            for (int i = 0; i<3; i++){
//                b2body.applyLinearImpulse(new Vector2(0f,0.1f), b2body.getWorldCenter(),true);
//                System.out.println("speed : " + b2body.getPosition().y);
//            }
//        }
//
//        if (b2body.getLinearVelocity().y <= 0 && !bottom){
//            b2body.applyLinearImpulse(new Vector2(0f,1f), b2body.getWorldCenter(),true);
//            System.out.println(b2body.getPosition().y);
//        }
    }
}
