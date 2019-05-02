
package com.oop.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.oop.platformer.Screens.GameOverScreen;
import com.oop.platformer.Screens.IntroScreen;
import com.oop.platformer.Screens.Level1;
import com.oop.platformer.Screens.StartScreen;
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

	public static boolean pauseMusic;
	private boolean gameOver;

	public GameClass(){}

	public GameClass(float width, float height){
		screenWidth = width;
		screenHeight = height;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		pauseMusic = true;
		gameOver = false;
		//The Play Screen
		setScreen(new Level1(this)); // To view MainMenuScreen change Level1 to MainMenuScreen
	}

	@Override
	public void render () {
		super.render();
		checkMusicControl();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkMusicControl(){
		if(!pauseMusic)
			Assets.instance.audio.mainThemeMusic.play();
		else
			Assets.instance.audio.mainThemeMusic.pause();
	}

	public void beginIntro() {
		setScreen(new IntroScreen(this));
	}

	public void endIntro() {
		Assets.instance.audio.introMusic.stop();
		setScreen(new Level1(this));
	}

	public void gameOver(boolean playerState){
		setScreen(new GameOverScreen(playerState));
	}


}