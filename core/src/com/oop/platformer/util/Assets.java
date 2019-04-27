package com.oop.platformer.util;

import com.badlogic.gdx.assets.AssetManager;
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
    public  FeministAssets feministAssets;
    public MainMenuAssets mainMenuAssets;
    public BulletAssets bulletAssets;


    public void init(AssetManager assetManager)
    {
        this.assetManager = assetManager;
        assetManager.load(Constants.GIGAGAL_TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        feministAssets = new FeministAssets(new TextureAtlas(Constants.FEMINIST_TEXTURE_ATLAS));
        mainMenuAssets = new MainMenuAssets();
        bulletAssets = new BulletAssets(new TextureAtlas(Constants.BULLET_TEXTURE_ATLAS));
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

        public final TextureRegion jumpingAnimation;

        public FeministAssets(TextureAtlas atlas)
        {
            Array<AtlasRegion> idleFrames = new Array<AtlasRegion>();

            for(int i  =0; i<=3; i++)
                idleFrames.add(atlas.findRegion("Ellie frame_idle", i));

            idleAnimation = new Animation<AtlasRegion>(0.1f, idleFrames, PlayMode.LOOP);

            Array<AtlasRegion> runFrames = new Array<AtlasRegion>();
            for(int i = 0; i<=13; i++)
                runFrames.add(atlas.findRegion("Ellie frame_run",i));

            runAnimation = new Animation<AtlasRegion>(0.1f, runFrames, PlayMode.LOOP);

            Array<AtlasRegion> shootFrames = new Array<AtlasRegion>();
            for(int i = 0; i <= 3; i++)
                shootFrames.add(atlas.findRegion("Ellie frame_shoot",i));

            shootAnimation = new Animation<AtlasRegion>(0.05f, shootFrames, PlayMode.LOOP);

            Array<AtlasRegion> fallingFrames = new Array<AtlasRegion>();
            for(int i = 0; i <= 7; i++) {
                fallingFrames.add(atlas.findRegion("Ellie frame_aim",i));
            }

            fallingAnimation = new Animation<AtlasRegion>(0.1f, fallingFrames, PlayMode.LOOP);

            jumpingAnimation = new TextureRegion(atlas.findRegion("Ellie frame_run", 12));
        }
    }

    public class BulletAssets{
        public final Animation<AtlasRegion> bulletAnimation;
        public final TextureRegion bulletTexture;
        public BulletAssets(TextureAtlas atlas) {
            Array<AtlasRegion> bulletFrames = new Array<AtlasRegion>();
            for(int i = 1; i<=3; i++)
                bulletFrames.add(atlas.findRegion("shot-"+i));
            bulletAnimation = new Animation<AtlasRegion>(0.05f, bulletFrames, PlayMode.LOOP);

            bulletTexture = atlas.findRegion("shot-1");
        }
    }

    public class MainMenuAssets{

        //Texture for the background image
        public final Texture background;
        public final TextureAtlas skinAtlas;
        public MainMenuAssets() {
            background = new Texture(Constants.BACKGROUND);
            skinAtlas = new TextureAtlas(Constants.SKIN_ATLAS);
        }
    }
}
