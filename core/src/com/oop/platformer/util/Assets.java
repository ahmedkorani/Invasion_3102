package com.oop.platformer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


import com.oop.platformer.GameObjects.Player;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.oop.platformer.Constants;

public class Assets implements Disposable {

    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public PlayerAssets playerAssets;
    public  FeministAssets feministAssets;


    public void init(AssetManager assetManager)
    {
        this.assetManager = assetManager;
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        playerAssets = new PlayerAssets(atlas);
        feministAssets = new FeministAssets(new TextureAtlas("Feminist/feminist.atlas"));
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
    public class PlayerAssets {

        public final AtlasRegion standingLeft;
        public final AtlasRegion standingRight;
        public final AtlasRegion walkingLeft;
        public final AtlasRegion walkingRight;
        public final AtlasRegion jumpingLeft;
        public final AtlasRegion jumpingRight;

        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;

        public PlayerAssets(TextureAtlas atlas) {

            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            walkingLeft = atlas.findRegion(Constants.WALKING_LEFT_2);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2);

            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_1));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_3));
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, PlayMode.LOOP);

            Array<AtlasRegion> walkingRightFrames = new Array<AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, PlayMode.LOOP);
        }
    }
}
