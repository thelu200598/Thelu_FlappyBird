package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

public class PlayState extends State {

    private static final int TUBE_SPACING=125;
    private static final int TUBE_COUNT=4;
    private static final int GROUND_Y_OFFSET=-50;
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private boolean gameover;
    private Texture gameoverImg;
    private Texture ReplyImg;

    private Array<Tube> tubes;
    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        gameoverImg = new Texture("gameover.png");
        ReplyImg = new Texture("playBtn.png");
        bird = new Bird(50,100);
        cam.setToOrtho(false, MyGdxGame.WIDTH/2,MyGdxGame.HEIGHT/2);
        bg=  new Texture("bg.png");
        ground = new Texture("ground.png");
//        tube = new Tube(100);
        groundPos1 = new Vector2(cam.position.x-cam.viewportWidth/2,GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x-cam.viewportWidth/2)+ground.getWidth(),GROUND_Y_OFFSET);
        tubes = new Array<Tube>();

        for (int i=1;i<TUBE_COUNT;i++){
            tubes.add(new Tube(i*(TUBE_SPACING+Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            if(gameover)
                gameStateManager.set(new PlayState(gameStateManager));
            bird.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x=bird.getPosition().x+80;
        for (Tube tube : tubes){
            if (cam.position.x-(cam.viewportWidth/2)>tube.getPosTopTube().x+tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x+((Tube.TUBE_WIDTH+TUBE_SPACING)*TUBE_COUNT));
            }
            if (tube.collides(bird.getBounds())){
                bird.colliding = true;
                gameover = true;

            }
        }
        if(bird.getPosition().y<= ground.getHeight()+GROUND_Y_OFFSET){
            gameover = true;
            bird.colliding = true;

        }
//            gameStateManager.set(new MenuState(gameStateManager));
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg,cam.position.x-(cam.viewportWidth/2),0);
        sb.draw(bird.getBird(), bird.getX(),bird.getY());
        for (Tube tube : tubes){
            sb.draw(tube.getTopTube(),tube.getPosTopTube().x,tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(),tube.getPosBotTube().x,tube.getPosBotTube().y);
        }
        sb.draw(ground,groundPos1.x,groundPos1.y);
        sb.draw(ground,groundPos2.x,groundPos2.y);
        if(gameover){
            sb.draw(gameoverImg, cam.position.x - gameoverImg.getWidth() / 2, cam.position.y);
            sb.draw(ReplyImg,cam.position.x-ReplyImg.getWidth()/2,cam.position.y/2);

        }
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for (Tube tube:tubes)
            tube.dispose();
        Gdx.app.log("","Play State Dispose");
    }

    private void updateGround(){
        if (cam.position.x-(cam.viewportWidth/2)>groundPos1.x+ ground.getWidth())
            groundPos1.add(ground.getWidth()*2,0);
        if (cam.position.x-(cam.viewportWidth/2)>groundPos2.x+ ground.getWidth())
            groundPos2.add(ground.getWidth()*2,0);
    }
}
