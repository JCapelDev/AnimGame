package com.capel.animgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimGameLauncher extends Game {

	SpriteBatch spriteBatch;
	public BitmapFont font;


	
	@Override
	public void create () {


		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new AnimGameMain(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		font.dispose();
	}
}
