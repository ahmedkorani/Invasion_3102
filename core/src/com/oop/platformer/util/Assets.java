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
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

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
        public final Animation idleAnimation;
        public final Animation runAnimation;
        public FeministAssets(TextureAtlas atlas)
        {
            Array<AtlasRegion> idleFrames = new Array<AtlasRegion>();

            for(int i  =0; i<=3; i++)
                idleFrames.add(atlas.findRegion("Ellie frame_idle", i));

            idleAnimation = new Animation(0.2f, idleFrames, PlayMode.LOOP);

            Array<AtlasRegion> runFrames = new Array<AtlasRegion>();
            for(int i = 0; i<=13; i++)
                runFrames.add(atlas.findRegion("Ellie frame_run",i));

            runAnimation = new Animation(0.1f, runFrames, PlayMode.LOOP);


        }
    }
    public class BulletAssets{
        public final Animation bulletAnimation;
        public final TextureRegion bulletTexture;
        public BulletAssets(TextureAtlas atlas) {
            Array<AtlasRegion> bulletFrames = new Array<AtlasRegion>();
            for(int i = 1; i<=3; i++)
                bulletFrames.add(atlas.findRegion("shot-"+i));
            bulletAnimation = new Animation(0.05f, bulletFrames, PlayMode.LOOP);

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
