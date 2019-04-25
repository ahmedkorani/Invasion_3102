package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.oop.platformer.util.Assets;


public class Player extends GameObjects{

    public enum State {Falling, Jumping, Standing, Running}
    private State currentState;
    private State previousState;
    private float stateTimer;
    private boolean runningRight;


    public Player(World world, Vector2 position, Level1 level1Screen){
        super(world, position,level1Screen);

        currentState = State.Standing;
        previousState = State.Standing;
        stateTimer = 0;
        runningRight = true;

        //load assets

        setBounds(0,0,32 / GameClass.PPM,32 / GameClass.PPM);
        setRegion((TextureRegion) Assets.instance.feministAssets.idleAnimation.getKeyFrame(stateTimer,true));
    }


	@Override
    public void define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(16 / GameClass.PPM);


        fdef.shape = shape;


        b2body.createFixture(fdef);

    }

    public void update(float deltaTime){

        setPosition(b2body.getPosition().x - getWidth()/2 , b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(deltaTime));
    }

    private TextureRegion getFrame(float deltaTime) {
        currentState = getState();


        TextureRegion region;
        switch (currentState){
            case Jumping:
                region = (TextureRegion) Assets.instance.feministAssets.idleAnimation.getKeyFrame(stateTimer,true);
                break;
            case Running:
                region = (TextureRegion) Assets.instance.feministAssets.runAnimation.getKeyFrame(stateTimer, true);
                break;
            case Falling:
            case Standing:
            default:
                region = (TextureRegion) Assets.instance.feministAssets.idleAnimation.getKeyFrame(stateTimer,true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        if (currentState == previousState)
            stateTimer += deltaTime;
        else
            stateTimer = 0;

        previousState = currentState;

        return region;
    }

    public State getState() {
        if(b2body.getLinearVelocity().y > 0)
            return State.Jumping;
        else if (b2body.getLinearVelocity().y < 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.Jumping))
            return State.Falling;
        else if (b2body.getLinearVelocity().x != 0)
            return State.Running;
        else
            return State.Standing;
    }
}