package com.oop.platformer.util;

import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.DroneEnemy;
import com.oop.platformer.GameObjects.Player;
import com.oop.platformer.Scenes.Hud;

import java.util.ArrayList;

public class LevelManager {

    //Game Objects
    private Player player;
    private ArrayList<DroneEnemy> droneEnemyArrayList;
    private DroneEnemy droneEnemy;

    //Game hud
    private Hud hud;

    public LevelManager(Player player, ArrayList<DroneEnemy> droneEnemyArrayList, Hud hud){
        this.player = player;
        this.droneEnemyArrayList = droneEnemyArrayList;
        this.hud = hud;
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
        this.droneEnemy = droneEnemy;
        droneEnemy.setPosition(220 / GameClass.PPM, 150 / GameClass.PPM);
        droneEnemy.position.x = 220 / GameClass.PPM;
        droneEnemy.position.y = 150 / GameClass.PPM;
    }
}
