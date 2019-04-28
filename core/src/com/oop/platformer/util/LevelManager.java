package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.*;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.Screens.Level1;

import static com.oop.platformer.Constants.FIRE_RATE;

public class LevelManager {

    private Level1 level1Screen;
    private World world;
    //Game Objects
    private Player player;
    private Enemy enemy;
    //Game hud
    private Hud hud;

    private float shootTimer;

    public LevelManager(Level1 level1Screen, Player player, Enemy enemy, Hud hud, World world){
        this.level1Screen = level1Screen;
        this.player = player;
        this.enemy = enemy;
        this.hud = hud;
        this.world = world;
        shootTimer = 0;
    }

    // returns a bullet to be added to bullets arraylist in level1 screen
    //NOTE*** +0.06f to adjust the position of the bullet exit to the barrel
    public Bullet spawnBullet(){
        if (player.isRunningRight()){
            return new Bullet(new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y + 0.06f), new Vector2(3f,0));
        }
        else{
            return new Bullet(new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y + 0.06f), new Vector2(-3f,0));
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
        shootTimer += deltaTime;
        player.handleInput(deltaTime);

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            enemy.setToDestroyed = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F) && shootTimer >= FIRE_RATE) {
            level1Screen.bullets.add(spawnBullet());
            shootTimer = 0;
        }
    }
}
