
package com.oop.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.oop.platformer.Screens.Level1;

public class GameClass extends Game {

	//Map width and hight
	public static final int V_WIDTH = 450;
	public static final int V_HEIGHT = 21*16;

	//Pixels per meter value
	public static final float PPM = 100;

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//The Play Screen
		setScreen(new Level1());
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