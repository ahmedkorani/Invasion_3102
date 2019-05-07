package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.oop.platformer.Constants;


public class Assets implements Disposable {

    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public PlayerAssets playerAssets;
    public MainMenuAssets mainMenuAssets;
    public BulletAssets bulletAssets;
    public DroneEnemyAssets droneEnemyAssets;
    public TurretEnemyAssets turretEnemyAssets;
    public BossEnemyAssets bossEnemyAssets;


    public Audio audio;
    public CustomFont customFont;


    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load(Constants.GIGAGAL_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        playerAssets = new PlayerAssets(new TextureAtlas(Constants.FEMINIST_TEXTURE_ATLAS));
        mainMenuAssets = new MainMenuAssets();
        bulletAssets = new BulletAssets(new TextureAtlas(Constants.BULLET_TEXTURE_ATLAS));
        droneEnemyAssets = new DroneEnemyAssets(new TextureAtlas(Constants.ENEMY_TEXTURE_ATLAS));
        turretEnemyAssets = new TurretEnemyAssets(new TextureAtlas(Constants.ENEMY_TEXTURE_ATLAS));
        bossEnemyAssets = new BossEnemyAssets(new TextureAtlas(Constants.BOSS_TEXTURE_ATLAS));

        audio = new Audio(assetManager);
        customFont = new CustomFont();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class CustomFont {

        public final BitmapFont font;

        public CustomFont() {
            //Declaring font and some of it's properties
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.RETRO_FONT));
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameter.size = 48;
            fontParameter.borderWidth = 3;
            fontParameter.borderColor = Color.PINK;
            fontParameter.color = Color.CYAN;
            //Assigning the font generator to the bitmap font
            font = fontGenerator.generateFont(fontParameter);
        }
    }

    public class PlayerAssets {
        public final Animation<AtlasRegion> idleAnimation;
        public final Animation<AtlasRegion> runAnimation;
        public final Animation<AtlasRegion> shootAnimation;
        public final Animation<AtlasRegion> fallingAnimation;
        public final Animation<AtlasRegion> deathAnimation;

        public final TextureRegion jumpingAnimation;

        public PlayerAssets(TextureAtlas atlas) {
            Array<AtlasRegion> idleFrames = new Array<AtlasRegion>();

            for (int i = 0; i <= 3; i++)
                idleFrames.add(atlas.findRegion("Ellie frame_idle", i));

            idleAnimation = new Animation<AtlasRegion>(0.1f, idleFrames, PlayMode.LOOP);

            Array<AtlasRegion> runFrames = new Array<AtlasRegion>();
            for (int i = 0; i <= 13; i++)
                runFrames.add(atlas.findRegion("Ellie frame_run", i));

            runAnimation = new Animation<AtlasRegion>(0.07f, runFrames, PlayMode.LOOP);

            Array<AtlasRegion> shootFrames = new Array<AtlasRegion>();
            for (int i = 0; i <= 3; i++)
                shootFrames.add(atlas.findRegion("Ellie frame_shoot", i));

            shootAnimation = new Animation<AtlasRegion>(0.1f, shootFrames, PlayMode.LOOP);

            Array<AtlasRegion> fallingFrames = new Array<AtlasRegion>();
            for (int i = 0; i <= 7; i++) {
                fallingFrames.add(atlas.findRegion("Ellie frame_aim", i));
            }

            fallingAnimation = new Animation<AtlasRegion>(0.1f, fallingFrames, PlayMode.LOOP);

            Array<AtlasRegion> deathFrames = new Array<AtlasRegion>();
            for (int i = 0; i <= 7; i++) {
                deathFrames.add(atlas.findRegion("Ellie frame_death", i));
            }

            deathAnimation = new Animation<AtlasRegion>(0.1f, deathFrames, PlayMode.NORMAL);

            jumpingAnimation = new TextureRegion(atlas.findRegion("Ellie frame_run", 12));
        }
    }

    public class DroneEnemyAssets {
        public final Animation<AtlasRegion> idleAnimation;

        public DroneEnemyAssets(TextureAtlas atlas) {
            Array<AtlasRegion> animation = new Array<AtlasRegion>();
            for (int i = 1; i <= 4; i++)
                animation.add(atlas.findRegion("drone-" + i));
            idleAnimation = new Animation<AtlasRegion>(0.1f, animation, PlayMode.LOOP);
        }
    }

    public class BossEnemyAssets {
        public final Animation<AtlasRegion> flyingAnimation;

        public BossEnemyAssets(TextureAtlas atlas) {
            Array<AtlasRegion> animation = new Array<AtlasRegion>();
            for (int i = 1; i <= 7; i++)
                animation.add(atlas.findRegion("Boss-" + i));
            flyingAnimation = new Animation<AtlasRegion>(0.1f, animation, PlayMode.LOOP);
        }

    }

    public class TurretEnemyAssets {
        public final Animation<AtlasRegion> idleAnimation;

        public TurretEnemyAssets(TextureAtlas atlas) {
            Array<AtlasRegion> animation = new Array<AtlasRegion>();
            for (int i = 1; i <= 6; i++)
                animation.add(atlas.findRegion("turret-" + i));
            idleAnimation = new Animation<AtlasRegion>(0.1f, animation, PlayMode.LOOP);
        }
    }

    public class BulletAssets {
        //        public final Animation<AtlasRegion> bulletAnimation;
        public final TextureRegion bulletTexture;

        public BulletAssets(TextureAtlas atlas) {
            bulletTexture = new TextureRegion(new Texture(Constants.BULLET));
//            Array<AtlasRegion> bulletFrames = new Array<AtlasRegion>();
//            for(int i = 1; i<=3; i++)
//                bulletFrames.add(atlas.findRegion("shot-"+i));
//            bulletAnimation = new Animation<AtlasRegion>(0.05f, bulletFrames, PlayMode.LOOP);
//
//            bulletTexture = atlas.findRegion("shot-1");
        }
    }

    public class MainMenuAssets {

        //Intro Assets
        public Texture FirstFrame;
        public Texture SecondFrame;
        public Texture ThirdFrame;
        public Texture mainBackground;

        public MainMenuAssets() {
            FirstFrame = new Texture(Constants.FIRST_FRAME);
            SecondFrame = new Texture(Constants.SECOND_FRAME);
            ThirdFrame = new Texture(Constants.THIRD_FRAME);
            mainBackground = new Texture(Constants.BACKGROUND);
        }
    }

    public class Audio {

        public final Music startScreenMusic;
        public final Music mainThemeMusic;
        public final Music introMusic;
        public final Sound introLastSound;
        public final Sound gunShotSound;
        public final Sound enemyDestroyed;
        public final Sound enemyHit;
        public final Sound playerHit;
        public final Sound playerDied;

        public Audio(AssetManager assetManager) {
            assetManager.load(Constants.STARTSCREENMUSIC, Music.class);
            assetManager.load(Constants.MUSIC, Music.class);
            assetManager.load(Constants.INTRO_MUSIC, Music.class);
            assetManager.load(Constants.IntroFinalSoundEffect, Sound.class);
            assetManager.load(Constants.GUN_SHOT, Sound.class);
            assetManager.load(Constants.ENEMY_DESTROYED, Sound.class);
            assetManager.load(Constants.ENEMY_HIT, Sound.class);
            assetManager.load(Constants.ENEMY_HIT, Sound.class);
            assetManager.load(Constants.PLAYER_HIT, Sound.class);
            assetManager.load(Constants.PLAYER_DIED, Sound.class);
            assetManager.finishLoading();

            //Music loop to play forever
            mainThemeMusic = assetManager.get(Constants.MUSIC);
            mainThemeMusic.setLooping(true);
            startScreenMusic = assetManager.get(Constants.STARTSCREENMUSIC);
            startScreenMusic.setLooping(true);

            introMusic = assetManager.get(Constants.INTRO_MUSIC);
            introLastSound = assetManager.get(Constants.IntroFinalSoundEffect);

            gunShotSound = assetManager.get(Constants.GUN_SHOT);
            enemyDestroyed = assetManager.get(Constants.ENEMY_DESTROYED);
            enemyHit = assetManager.get(Constants.ENEMY_HIT);
            playerHit = assetManager.get(Constants.PLAYER_HIT);
            playerDied = assetManager.get(Constants.PLAYER_DIED);
        }
    }
}
