package com.oop.platformer.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.oop.platformer.GameClass;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.util.Assets;

public class Player extends GameObjects {


    public enum State {Falling, Jumping, Standing, Running, Shooting, Jumping_Shooting}

    private State currentState;
    private State previousState;
    private float stateTimer;
    private boolean runningRight;
    //player Lives
    private int lives;
    //player Score
    private int score;

    public Player(World world, Vector2 position, Level1 level1Screen) {
        super(world, position, level1Screen);

        lives = 3;
        score = 0;

        currentState = State.Standing;
        previousState = State.Standing;
        stateTimer = 0;
        runningRight = true;

        setBounds(0, 0, 32 / GameClass.PPM, 32 / GameClass.PPM);
        setRegion(Assets.instance.feministAssets.idleAnimation.getKeyFrame(stateTimer, true));
    }


    @Override
    public void define() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(13 / GameClass.PPM);

        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float deltaTime) {

        this.position = body.getPosition();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }

    private TextureRegion getFrame(float deltaTime) {
        currentState = getState();


        TextureRegion region;

        switch (currentState) {
            case Jumping:
                region = Assets.instance.feministAssets.jumpingAnimation;
                break;
            case Running:
                region = Assets.instance.feministAssets.runAnimation.getKeyFrame(stateTimer, true);
                break;
            case Shooting:
            case Jumping_Shooting:
                region = Assets.instance.feministAssets.shootAnimation.getKeyFrame(stateTimer, true);
                break;
            case Falling:
                region = Assets.instance.feministAssets.fallingAnimation.getKeyFrame(stateTimer, true);
                break;
            case Standing:
            default:
                region = Assets.instance.feministAssets.idleAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
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
        if (body.getLinearVelocity().y < 0 || (body.getLinearVelocity().y < 0 && previousState == State.Jumping))
            return State.Falling;
        else if (body.getLinearVelocity().y > 0 && Gdx.input.isKeyPressed(Input.Keys.F))
            return State.Jumping_Shooting;
        else if (body.getLinearVelocity().x != 0)
            return State.Running;
        else if (body.getLinearVelocity().y > 0)
            return State.Jumping;
        else if (Gdx.input.isKeyPressed(Input.Keys.F))
            return State.Shooting;
        else
            return State.Standing;
    }

    public void hitPlayer() {
        System.out.println("player is hit");
        lives--;

    }


    private void pushPlayerAway() {

    }

    //Returns lives remaining for the player
    public int getLives() {
        return lives;
    }

    //returns player Current score
    public int getScore() {
        return score;
    }

    private void respawnPlayer() {
    }

    public boolean isRunningRight() {
        return runningRight;
    }
}