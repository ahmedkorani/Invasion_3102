package com.oop.platformer.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import com.oop.platformer.Constants;

public class Assets implements Disposable {

    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public FeministAssets feministAssets;
    public MainMenuAssets mainMenuAssets;
    public BulletAssets bulletAssets;
    public Audio audio;


    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load(Constants.GIGAGAL_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        feministAssets = new FeministAssets(new TextureAtlas(Constants.FEMINIST_TEXTURE_ATLAS));
        mainMenuAssets = new MainMenuAssets();
        bulletAssets = new BulletAssets(new TextureAtlas(Constants.BULLET_TEXTURE_ATLAS));
        audio = new Audio(assetManager);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class FeministAssets {
        public final Animation<AtlasRegion> idleAnimation;
        public final Animation<AtlasRegion> runAnimation;
        public final Animation<AtlasRegion> shootAnimation;
        public final Animation<AtlasRegion> fallingAnimation;
        public final Animation<AtlasRegion> deathAnimation;

        public final TextureRegion jumpingAnimation;

        public FeministAssets(TextureAtlas atlas) {
            Array<AtlasRegion> idleFrames = new Array<AtlasRegion>();

            for (int i = 0; i <= 3; i++)
                idleFrames.add(atlas.findRegion("Ellie frame_idle", i));

            idleAnimation = new Animation<AtlasRegion>(0.1f, idleFrames, PlayMode.LOOP);

            Array<AtlasRegion> runFrames = new Array<AtlasRegion>();
            for (int i = 0; i <= 13; i++)
                runFrames.add(atlas.findRegion("Ellie frame_run", i));

            runAnimation = new Animation<AtlasRegion>(0.1f, runFrames, PlayMode.LOOP);

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
            for (int i=0 ; i<=7 ; i++){
                deathFrames.add(atlas.findRegion("Ellie frame_death", i));
            }

            deathAnimation = new Animation<AtlasRegion>(0.1f, deathFrames, PlayMode.NORMAL);

            jumpingAnimation = new TextureRegion(atlas.findRegion("Ellie frame_run", 12));
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

        public final Music mainThemeMusic;
        public final Music introMusic;
        public final Sound introLastSound;
        public final Sound gunShotSound;
        public final Sound enemyDestroyed;
        public final Sound enemyHit;
        public final Sound playerHit;
        public final Sound playerDied;


        public Audio(AssetManager assetManager) {
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
