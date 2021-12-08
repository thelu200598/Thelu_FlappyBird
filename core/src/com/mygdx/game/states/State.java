package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract  class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected  GameStateManager gameStateManager;

    protected State (GameStateManager gameStateManager){
        this.gameStateManager=gameStateManager;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract  void handleInput();
    public abstract void update(float dt);
    public abstract void render (SpriteBatch sb);
    public abstract void dispose();
}
