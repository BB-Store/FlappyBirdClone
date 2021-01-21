package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.tutorial.flappybird.clone.Mainactivity;
import objectives.Bird;

public class GameScreen implements Screen {

    private Mainactivity game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Bird bird;

    public GameScreen(Mainactivity game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Mainactivity.Width * 0.5f, Mainactivity.Height*0.5f);
        batch = new SpriteBatch();
        bird = new Bird(50, 250);
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
        batch.end();
    }

    private void update(float delta) {
        handleInput();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + 80;
        camera.update();
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

    }
}
