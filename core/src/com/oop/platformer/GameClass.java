
package com.oop.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.platformer.Screens.Level1;

public class GameClass extends Game {

	public static final int V_WIDTH = 450;
	public static final int V_HEIGHT = 21*16;
	public static final float PPM = 100;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Level1(this));
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