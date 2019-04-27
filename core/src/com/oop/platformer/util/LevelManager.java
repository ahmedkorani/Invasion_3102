package com.oop.platformer.util;

import com.badlogic.gdx.math.Vector2;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.Bullet;
import com.oop.platformer.GameObjects.DroneEnemy;
import com.oop.platformer.GameObjects.Player;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.Screens.Level1;

import java.util.ArrayList;

public class LevelManager {

    private Level1 level1Screen;
    //Game Objects
    private Player player;
    private ArrayList<DroneEnemy> droneEnemyArrayList;
    private DroneEnemy droneEnemy;

    //Game hud
    private Hud hud;

    public LevelManager(Level1 level1Screen,Player player, ArrayList<DroneEnemy> droneEnemyArrayList, Hud hud){
        this.level1Screen = level1Screen;
        this.player = player;
        this.droneEnemyArrayList = droneEnemyArrayList;
        this.hud = hud;
    }

    public Bullet spawnBullet(){
        if (player.isRunningRight()){
            return new Bullet(level1Screen, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(3f,0));
        }
        else{
            return new Bullet(level1Screen, new Vector2(player.body.getPosition().x + 2/GameClass.PPM, player.body.getPosition().y), new Vector2(-3f,0));
        }
    }

    void playerIsHit(){
        //TODO kill the player when lives reaches zero
        if(player.getLives() == 0)
            System.out.println("you Lost");

        player.hitPlayer();
        hud.setPlayerLives(player.getLives());
    }

    void enemyIsHit(DroneEnemy droneEnemy){
        System.out.println("enemy");
    }


}
