package com.oop.platformer.util;

import com.badlogic.gdx.math.Vector2;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.Bullet;
import com.oop.platformer.GameObjects.DroneEnemy;
import com.oop.platformer.GameObjects.Player;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.Screens.Level1;


public class LevelManager {

    private Level1 level1Screen;
    //Game Objects
    private Player player;

    //Game hud
    private Hud hud;

    public LevelManager(Level1 level1Screen,Player player, Hud hud){
        this.level1Screen = level1Screen;
        this.player = player;
        this.hud = hud;
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

    public void playerIsHit(){
        //TODO kill the player when lives reaches zero
        if(player.getLives() == 0)
            System.out.println("you Lost");

        player.hitPlayer();
        hud.setPlayerLives(player.getLives());
    }

    public void handlePlayerInput(float deltaTime) {
        player.handleInput(deltaTime);
    }

    public void movePlayerUp() {
    }

    public void movePlayerRight() {
    }

    public void movePlayerLeft() {
    }
}
