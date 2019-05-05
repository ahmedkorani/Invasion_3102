package com.oop.platformer.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.oop.platformer.Constants;
import com.oop.platformer.GameClass;
import com.oop.platformer.util.Assets;

public class Player extends GameObject {


    public enum State {Falling, Jumping, Standing, Running, Shooting, Jumping_Shooting, Dead}

    private int jumpCounter = 0;
    private State currentState;
    private State previousState;
    private float stateTimer;
    private boolean runningRight;
    //player Lives
    private int lives;
    //player Score
    private int score;
    //player dead or not
    public boolean win;
    public boolean dead;
    public boolean shooting;

    private Vector2 respawnPosition;
    private float currentTime;
    private float previousTime;
    private float xRespawn, yRespawn;

    private float deathTime;
    private float winTime;

    public Player(World world, Vector2 position) {
        super(world, position);

        respawnPosition = this.spritePosition;
        xRespawn = this.spritePosition.x;
        yRespawn = this.spritePosition.y;
        currentTime = 0;
        previousTime = 0;
        deathTime = 0;

        lives = Constants.LIVES;
        score = Constants.SCORE;
        dead = false;
        shooting = false;

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
        bodyDef.position.set(spritePosition);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);


        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(13 / GameClass.PPM);
        fixtureDef.shape = shape;

//        fixtureDef.friction = 0f;



        body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float deltaTime) {
        currentTime += deltaTime;

        if (win)
            winTime = currentTime;

        checkPlayerPosition();
        this.spritePosition = body.getPosition();
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));

        //To Save a check point every 5 seconds of game play
        if (currentTime - previousTime >= 5 && spritePosition.y >= 0 && currentState != State.Jumping && currentState != State.Falling) {
            respawnPosition = this.spritePosition;
            previousTime = currentTime;
            xRespawn = respawnPosition.x;
            yRespawn = respawnPosition.y;
        }
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
            case Dead:
                region = Assets.instance.feministAssets.deathAnimation.getKeyFrame(stateTimer, false);
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

    private State getState() {
        if (body.getLinearVelocity().y < 0 || (body.getLinearVelocity().y < 0 && previousState == State.Jumping))
            return State.Falling;
        else if (body.getLinearVelocity().y > 0 && Gdx.input.isKeyPressed(Input.Keys.F))
            return State.Jumping_Shooting;
        else if (body.getLinearVelocity().x != 0)
            return State.Running;
        else if (body.getLinearVelocity().y > 0)
            return State.Jumping;
        else if (shooting)
            return State.Shooting;
        else if (dead)
            return State.Dead;
        else
            return State.Standing;
    }

    public void handleInput(float deltaTime) {

        float verticalSpeed = body.getLinearVelocity().y;

        if (verticalSpeed == 0)
            jumpCounter = 0;

        if (jumpCounter == 0 && verticalSpeed < 0)
            jumpCounter = 2;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCounter != 2) {
            body.setLinearVelocity(body.getLinearVelocity().x, 2.5f);
            jumpCounter++;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.setLinearVelocity(-1.8f, body.getLinearVelocity().y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(1.8f, body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    public void hitPlayer() {
        if (lives == 0) {
            //TODO show Death Screen
            System.out.println("you ran out of lives DEAD :( ");
            dead = true;
            deathTime = currentTime;
        } else {
            System.out.println("player is hit");
            Assets.instance.audio.playerHit.play();
            lives--;
        }
    }

    private void checkPlayerPosition() {
        if (!dead && this.spritePosition.y < -5f) {
            hitPlayer();
            if (!dead)
                respawnPlayer();
        }
    }

    //Returns lives remaining for the player
    public int getLives() {
        return lives;
    }

    //returns player Current score
    public int getScore() {
        return score;
    }

    public boolean isDead() {
        return dead;
    }

    public void increaseScore() {
        score += 100;
    }

    public void respawnPlayer() {
        body.setTransform(new Vector2(xRespawn, yRespawn), 0);
        if (!isRunningRight())
            runningRight = true;
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public boolean wonLevel(){
        return win && currentTime - winTime >= 5;
    }

    public void setWin(){
        win = true;
        winTime = currentTime;
    }

    public boolean lostLevel() {
        return dead && currentTime - deathTime >= 5;
    }

}