
package com.oop.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.oop.platformer.Screens.Level1;
import com.oop.platformer.Screens.MainMenuScreen;
import com.oop.platformer.util.Assets;

public class GameClass extends Game {

	//Map width and height
	public static final int V_WIDTH = 450;
	public static final int V_HEIGHT = 21*16;

	//Pixels per meter value to fix the ration in the world
	public static final float PPM = 100;

	public SpriteBatch batch;

	private boolean musicPause;
	private boolean gameOver;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		Assets.instance.audio.mainThemeMusic.play();
		musicPause = false;
		gameOver = false;
		//The Play Screen
		setScreen(new Level1(this)); // To view MainMenuScreen change Level1 to MainMenuScreen
	}

	@Override
	public void render () {
		super.render();
		if(!gameOver)
			checkMusicControl();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkMusicControl(){
		if(!Assets.instance.audio.mainThemeMusic.isPlaying()){
			musicPause = true;
		}
		else
			musicPause = false;

		if(Gdx.input.isKeyJustPressed(Input.Keys.M) && !musicPause)
			musicPause = true;
		else if(Gdx.input.isKeyJustPressed(Input.Keys.M) && musicPause)
			musicPause = false;
		if(!musicPause)
			Assets.instance.audio.mainThemeMusic.play();
		else
			Assets.instance.audio.mainThemeMusic.pause();
	}
}