package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.*;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.Screens.Level1;


public class LevelManager {

    private Level1 level1Screen;
    private World world;
    //Game Objects
    private Player player;
    private Enemy enemy;
    //Game hud
    private Hud hud;

    private static final float FIRE_RATE = 0.5f;
    private float lastShot = 0;

    public LevelManager(Level1 level1Screen, Player player, Enemy enemy, Hud hud, World world){
        this.level1Screen = level1Screen;
        this.player = player;
        this.enemy = enemy;
        this.hud = hud;
        this.world = world;
    }

    // returns a bullet to be added to bullets arraylist in level1 screen
    public Bullet spawnBullet(){
        if (player.isRunningRight()){
            return new Bullet(level1Screen, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(3f,0));
        }
        else{
            return new Bullet(level1Screen, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(-3f,0));
        }
    }

    public BulletTest spawnBulletTesting(){
        if (player.isRunningRight()){
            return new BulletTest(world, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(3f,0));
        }
        else{
            return new BulletTest(world, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(-3f,0));
        }
    }

    public void playerIsHit(){
        //TODO kill the player when lives reaches zero
        if(player.getLives() == 0)
            System.out.println("you Lost");

        player.hitPlayer();
        hud.setPlayerLives(player.getLives());
    }

    public void handlePlayerInput(float deltaTime) {
        player.handleInput(deltaTime);

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            enemy.setToDestroyed = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            level1Screen.bullets.add(spawnBullet());
//            System.out.println(System.currentTimeMillis() - lastShot);
//            if (System.currentTimeMillis() - lastShot >= FIRE_RATE) {
//                level1Screen.bullets.add(spawnBullet());
//                lastShot = System.currentTimeMillis();
//            }
        }
    }

    public void movePlayerUp() {
    }

    public void movePlayerRight() {
    }

    public void movePlayerLeft() {
    }
}
