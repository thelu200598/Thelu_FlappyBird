package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class MyGdxGame extends ApplicationAdapter {
	public static final int WIDTH=460;
	public static final int HEIGHT=800;

	public static final String TITLE="Flappy bird";
	private GameStateManager gameStateManager;
	private SpriteBatch batch;
	private Music music;


	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		music= Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		gameStateManager.push(new MenuState(gameStateManager));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.5f, 0.6f, 0.6f, 1);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(batch);

	}
	
	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}

}
