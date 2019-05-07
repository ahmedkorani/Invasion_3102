
package com.oop.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.oop.platformer.Screens.*;
import com.oop.platformer.util.Assets;

public class GameClass extends Game {

    //Map width and height
    public static final int V_WIDTH = 700;
    public static final int V_HEIGHT = 336;

    public static float screenWidth;
    public static float screenHeight;

    //Pixels per meter value to fix the ration in the world
    public static final float PPM = 100;

    public SpriteBatch batch;

    public static boolean isMusicPaused;

    public GameClass() {
    }

    public GameClass(float width, float height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        Assets.instance.init(new AssetManager());
        isMusicPaused = false; //Change this to true if you want the music to be paused by default
        //The Play Screen
        setScreen(new OutroScreen(this, false)); // To view MainMenuScreen change Level1 to MainMenuScreen

    }

    @Override
    public void render() {
        super.render();
        checkMusicControl();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void checkMusicControl() {
        if (!isMusicPaused) {
            if (this.getScreen().toString().contains("StartScreen"))
                Assets.instance.audio.startScreenMusic.play();

            else if (this.getScreen().toString().contains("Level1"))
                Assets.instance.audio.mainThemeMusic.play();

            else if (this.getScreen().toString().contains("OutroScreen")) {
                if (OutroScreen.playerWon)
                    Assets.instance.audio.winMusic.play();
                else if (OutroScreen.playerLost)
                    Assets.instance.audio.loseMusic.play();
            }

        } else {
            if (this.getScreen().toString().contains("StartScreen"))
                Assets.instance.audio.startScreenMusic.pause();

            else if (this.getScreen().toString().contains("Level1"))
                Assets.instance.audio.mainThemeMusic.pause();

            else if (this.getScreen().toString().contains("OutroScreen")) {
                if (OutroScreen.playerWon)
                    Assets.instance.audio.winMusic.pause();
                else if (OutroScreen.playerLost)
                    Assets.instance.audio.loseMusic.pause();
            }

        }

    }

    public void beginIntro() {
        setScreen(new IntroScreen(this));
        Assets.instance.audio.startScreenMusic.stop();
    }

    public void beginLevel() {
        Assets.instance.audio.introMusic.stop();
        setScreen(new Level1(this));
    }

    public void beginOutro(boolean playerState) {
        setScreen(new OutroScreen(this, playerState));
    }


    public void endOutro() {
        setScreen(new StartScreen(this));
    }
}