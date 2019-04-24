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


public class Player extends GameObjects{

    public enum State {Falling, Jumping, Standing, Running}

    private State currentState;
    private State previousState;
    private float stateTimer;
    private boolean runningRight;

    private final TextureRegion standingLeft;
    private final TextureRegion standingRight;
    private final TextureRegion walkingLeft;
    private final TextureRegion walkingRight;
    private final TextureRegion jumpingLeft;
    private final TextureRegion jumpingRight;

    private final Animation walkingLeftAnimation;
    private final Animation walkingRightAnimation;


    public Player(World world, Vector2 position, Level1 level1Screen){
        super(world, position,level1Screen);

        currentState = State.Standing;
        previousState = State.Standing;
        stateTimer = 0;
        runningRight = true;

        TextureAtlas atlas = new TextureAtlas(Constants.TEXTURE_ATLAS);

        standingLeft = atlas.findRegion(Constants.STANDING_LEFT);
        standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
        walkingLeft = atlas.findRegion(Constants.WALKING_LEFT_2);
        walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2);

        jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
        jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

        Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
        walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
        walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_1));
        walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
        walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_3));
        walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, Animation.PlayMode.LOOP);

        Array<AtlasRegion> walkingRightFrames = new Array<AtlasRegion>();
        walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
        walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));
        walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
        walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
        walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);

        setBounds(0,0,26 / GameClass.PPM,26 / GameClass.PPM);
        setRegion(standingRight);
    }


	@Override
    public void define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(7 / GameClass.PPM);

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
                region = jumpingRight;
                break;
            case Running:
                region = (TextureRegion) walkingRightAnimation.getKeyFrame(stateTimer, true);
                break;
            case Falling:
            case Standing:
            default:
                region = standingRight;
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