package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oop.platformer.GameClass;
import com.oop.platformer.GameObjects.*;
import com.oop.platformer.Scenes.Hud;
import com.oop.platformer.Screens.Level1;

import static com.oop.platformer.Constants.FIRE_RATE;

public class LevelManager {

    public static final LevelManager instance = new LevelManager();

    private GameClass gameClass;
    private Level1 level;
    private World world;
    private OrthogonalTiledMapRenderer renderer;

    //Game Objects
    private Player player;
    private Array<Enemy> enemies;
    private Array<Bullet> bullets;
    private Enemy bossEnemy;

    //Game hud
    private Hud hud;
    private OrthographicCamera gameCam;

    private boolean isDeathSoundPlayed;
    private float shootTimer;

    private boolean musicControlChecked;
    private boolean isMusicPaused;

    public void setLevel(Level1 level)
    {
        instance.level = level;
        instance.gameClass = level.getGameClass();
        instance.world = level.getWorld();
        instance.player = level.getPlayer();
        instance.enemies = level.getEnemies();
        instance.bullets = level.getBullets();
        instance.hud = level.getHud();
        instance.gameCam = level.getGameCam();
        instance.renderer = level.getRenderer();
        shootTimer = 0;
        //finding the boss enemy in the enemies array
        for (Enemy enemy : enemies) {
            if (enemy instanceof BossEnemy) {
                this.bossEnemy = enemy;
                break;
            }
        }
        isDeathSoundPlayed = false;
        isMusicPaused = GameClass.isMusicPaused;
        musicControlChecked = false;
    }

    private LevelManager() {

    }

    public void update(float deltaTime) {

        if (player.isDead()) {
            if(!musicControlChecked){
                isMusicPaused = GameClass.isMusicPaused;
                musicControlChecked = true;
            }
            GameClass.isMusicPaused = true;
            if (!isDeathSoundPlayed) {
                Assets.instance.audio.playerDied.play();
                isDeathSoundPlayed = true;
            }
            if (player.lostLevel()) {
                System.out.println("Level is lost");
                gameOver(false);
            }
        } else if (bossEnemy.destroyed) {
            if(!musicControlChecked){
                isMusicPaused = GameClass.isMusicPaused;
                musicControlChecked = true;
            }
            GameClass.isMusicPaused = true;
            if (!player.isWin()) {
                player.setWin();
                Assets.instance.audio.playerWon.play();
            } else if (player.wonLevel()) {
                gameOver(true);
            }
        } else
            handlePlayerInput(deltaTime);
        checkBulletsPosition();
        hud.setLives(player.getLives());
        hud.setScore(player.getScore());

        world.step(1 / 60f, 60, 2);
        player.update(deltaTime);

        //Bullet update
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        for (Enemy enemy : enemies)
            enemy.update(deltaTime);

        gameCam.position.x = player.body.getWorldCenter().x;
        gameCam.update();
        renderer.setView(gameCam); //tells our renderer to draw only what camera can see in our game world
    }

    // returns a bullet to be added to bullets ArrayList in level1 screen
    //NOTE*** +0.06f to adjust the spritePosition of the bullet exit to the barrel
    private Bullet spawnBullet() {
        Assets.instance.audio.gunShotSound.play();
        if (player.isRunningRight()) {
            return new Bullet(world, new Vector2(player.body.getPosition().x + 2 / GameClass.PPM + 0.20f, player.body.getPosition().y + 0.08f),
                    true);
        } else {
            return new Bullet(world, new Vector2(player.body.getPosition().x + 2 / GameClass.PPM - 0.20f, player.body.getPosition().y + 0.08f),
                    false);
        }

    }

    void playerIsHit() {
        player.hitPlayer();
    }

    private void handlePlayerInput(float deltaTime) {
        shootTimer += deltaTime;
        player.handleInput(deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.F) && shootTimer >= FIRE_RATE) {
            player.shooting = true;
            level.bullets.add(spawnBullet());
            shootTimer = 0;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.F))
            player.shooting = false;

        //Music Control
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            GameClass.isMusicPaused = !GameClass.isMusicPaused;
        }
    }

    private void checkBulletsPosition() {
        for (Bullet bullet : bullets) {
            if (bullet.getPosition() > gameCam.position.x + 3.4 || bullet.getPosition() < gameCam.position.x - 3.4) {
                bullet.setToDestroy();
            }
        }
    }


    void bulletHitWall(Fixture fixture) {
        for (Bullet bullet : bullets) {
            if (bullet.equals(fixture.getUserData()))
                bullet.setToDestroy();
        }
    }

    void bulletHitEnemy(Fixture enemyFixture, Fixture bulletFixture) {
        for (Bullet bullet : bullets) {
            if (bullet.equals(bulletFixture.getUserData()))
                bullet.setToDestroy();
        }
        for (Enemy enemy : enemies) {
            if (enemy.equals(enemyFixture.getUserData())) {
                if (enemy.setToDestroy())
                    player.increaseScore();
            }
        }
    }

    private void gameOver(boolean playerState) {
        GameClass.isMusicPaused = isMusicPaused;
        gameClass.beginOutro(playerState);
    }
}
