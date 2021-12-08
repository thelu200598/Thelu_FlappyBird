package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class MenuState extends State{
    @Override
    public void dispose() {
        background.dispose();
        play_btn.dispose();
        System.out.println("Menu State Dispose");
    }

    private Texture background;
    private Texture play_btn;
    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        cam.setToOrtho(false, MyGdxGame.WIDTH/2,MyGdxGame.HEIGHT/2);
        background = new Texture("bg.png");
        play_btn = new Texture("playBtn.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gameStateManager.set(new PlayState(gameStateManager));

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0);
        sb.draw(play_btn,cam.position.x-play_btn.getWidth()/2,cam.position.y);
//        sb.draw(play_btn, (MyGdxGame.WIDTH / 2) - (play_btn.getWidth() / 2), MyGdxGame.HEIGHT / 2);
        sb.end();
    }
}
