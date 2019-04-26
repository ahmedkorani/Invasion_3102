
package com.oop.platformer;

import com.badlogic.gdx.Game;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		//The Play Screen
		setScreen(new Level1(this)); // To view MainMenuScreen change Level1 to MainMenuScreen
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}