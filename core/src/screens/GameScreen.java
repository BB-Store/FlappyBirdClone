package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import de.tutorial.flappybird.clone.Mainactivity;
import objectives.Bird;
import objectives.Tube;

public class GameScreen implements Screen {

    private static final int Tube_Count = 4;
    private static final int Tube_Space = 145;
    private Mainactivity game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Bird bird;
    private Array<Tube> tubes;

    public GameScreen(Mainactivity game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Mainactivity.Width * 0.5f, Mainactivity.Height*0.5f);
        batch = new SpriteBatch();
        bird = new Bird(50, 250);
        tubes = new Array<Tube>();

        for(int i = 1; i <= Tube_Count; i++){
            tubes.add(new Tube(100 + i *(Tube_Space + Tube.Tube_Width)));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y, bird.getSize(), bird.getSize());
        for (Tube tube : tubes){
            batch.draw(tube.getTubeTop(), tube.getPosTubeTop().x, tube.getPosTubeTop().y, Tube.Tube_Width, Tube.Tube_Height);
            batch.draw(tube.getTubeBottom(), tube.getPosTubeBottom().x, tube.getPosTubeBottom().y, Tube.Tube_Width, Tube.Tube_Height);
        }
        batch.end();
    }

    private void update(float delta) {
        handleInput();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);

            if(camera.position.x - camera.viewportWidth * 0.5f > tube.getPosTubeTop().x + Tube.Tube_Width){

                //1 Punkt + Soundeffekt

                tube.reposition(tube.getPosTubeTop().x + (Tube.Tube_Width + Tube_Space) * Tube_Count);
            }
            if(tube.collides(bird.getBounds())){
                gameOver();
            }
        }

        //Check oberhalb des Bodens
        if(bird.getPosition().y <= 0) {
            gameOver();
        }

        camera.update();
    }

    private void gameOver() {
        game.setScreen(new GameScreen(game));
    }

    private void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bird.dispose();
        for (Tube tube : tubes){
            tube.dispose();
        }
    }
}
