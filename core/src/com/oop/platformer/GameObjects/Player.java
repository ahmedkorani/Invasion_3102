package com.oop.platformer.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;

import javax.xml.soap.Text;

public class Player extends GameObjects{

    private enum State {Falling, Jumping, Standing, Running};
    private State currentState;
    private State previousState;

    private TextureRegion playerStand;
    private TextureRegion playerJump;
    private Animation playerRun;
    private float stateTimer;
    private boolean runningRight;


    public Player(World world, Vector2 position, Level1 level1Screen){
        super(world, position,level1Screen, "little_mario");
        this.define();

        currentState = State.Standing;
        previousState = State.Standing;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i<=4; i++)
            frames.add(new TextureRegion(getTexture(), 1+i*16, 11,16,16));

        playerRun = new Animation(0.1f, frames);

        frames.clear();

        playerJump = new TextureRegion(getTexture(), 1+5*16, 11, 16,16);    //hard coded, needs to get values of offset and index from atlas

        frames.clear();

        playerStand = new TextureRegion(getTexture(), 1, 11, 16, 16);
        setBounds(0,0,16 / GameClass.PPM,16 / GameClass.PPM);
        setRegion(playerStand);
    }


	@Override
    public void define() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(10 / GameClass.PPM);

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
                region = playerJump;
                break;
            case Running:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case Falling:
            case Standing:
            default:
                region = playerStand;
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

    private State getState() {
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