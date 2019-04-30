package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private Level1 level1Screen;
    private World world;
    //Game Objects
    private Player player;
    private Enemy enemy;
    private Array<Bullet> bullets;
    //Game hud
    private Hud hud;
    private OrthographicCamera gameCam;


    private float shootTimer;

    public LevelManager(Level1 level1Screen, Player player, Enemy enemy, Hud hud, World world, Array<Bullet> bullets, OrthographicCamera gameCam) {
        this.level1Screen = level1Screen;
        this.player = player;
        this.enemy = enemy;
        this.hud = hud;
        this.world = world;
        this.bullets = bullets;
        this.gameCam = gameCam;
        shootTimer = 0;
    }

    public void update(float deltaTime) {
        if (player.isDead()) {
            gameOver();
        } else
            handlePlayerInput(deltaTime);
        checkBulletsPosition();
    }

    // returns a bullet to be added to bullets ArrayList in level1 screen
    //NOTE*** +0.06f to adjust the position of the bullet exit to the barrel
    public Bullet spawnBullet() {
        Assets.instance.audio.gunShotSound.play();
        if (player.isRunningRight()) {
            return new Bullet(world, new Vector2(player.body.getPosition().x + 2 / GameClass.PPM + 0.20f, player.body.getPosition().y + 0.08f),
                    true);
        } else {
            return new Bullet(world, new Vector2(player.body.getPosition().x + 2 / GameClass.PPM - 0.20f, player.body.getPosition().y + 0.08f),
                    false);
        }

    }

    public void playerIsHit() {
        player.hitPlayer();
        hud.setLives(player.getLives());
        if (player.isDead()) {
            if (Assets.instance.audio.mainThemeMusic.isPlaying())
                Assets.instance.audio.mainThemeMusic.stop();
            Assets.instance.audio.playerDied.play();
        }
    }

    private void handlePlayerInput(float deltaTime) {
        shootTimer += deltaTime;
        player.handleInput(deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.F) && shootTimer >= FIRE_RATE) {
            player.shooting = true;
            level1Screen.bullets.add(spawnBullet());
            shootTimer = 0;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.F))
            player.shooting = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            System.out.println("reset position");
            player.respawnPlayer();
        }
    }

    private void checkBulletsPosition() {
        for (Bullet bullet : bullets) {
            if (bullet.getPosition() > gameCam.position.x + 2.2 || bullet.getPosition() < gameCam.position.x - 2.2) {
                bullet.setToDestroy();
            }
        }
    }

    private void checkPlayerPosition() {
        if (player.body.getPosition().y < -5f)
            System.out.println("player out of bounds");
    }

    public void bulletHitWall(Fixture fixture) {
        for (Bullet bullet : bullets) {
            if (bullet.equals(fixture.getUserData()))
                bullet.setToDestroy();
        }
    }

    public void bulletHitEnemy(Fixture enemyFixture, Fixture bulletFixture) {
        for (Bullet bullet : bullets) {
            if (bullet.equals(bulletFixture.getUserData()))
                bullet.setToDestroy();
        }

        if (this.enemy.setToDestroy()) {
            player.increaseScore();
            hud.setScore(player.getScore());
        }
    }

    public void gameOver() {
    }
}
