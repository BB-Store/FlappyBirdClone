package de.tutorial.flappybird.clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GameScreen;
import screens.MenuScreen;

public class Mainactivity extends Game {

	public static final int Width = 480;
	public static final int Height = 800;

	@Override
	public void create () {
		setScreen(new MenuScreen(this, 0));
	}

}
