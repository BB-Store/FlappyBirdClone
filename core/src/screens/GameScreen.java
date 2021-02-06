package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.tutorial.flappybird.clone.Mainactivity;
import objectives.Bird;
import objectives.Tube;

public class GameScreen implements Screen {

    private static final int Tube_Count = 4;
    private static final int Tube_Space = 145;
    private static final int Ground_Offset = -80;
    private static final int Ground_Width = 350;
    private static final int Ground_Height = 122;
    private Mainactivity game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Bird bird;
    private Array<Tube> tubes;
    private Texture test;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Stage stage;
    private int score;
    private Label lblScore;
    private Label.LabelStyle skin;


    public GameScreen(Mainactivity game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Mainactivity.Width * 0.5f, Mainactivity.Height*0.5f);
        batch = new SpriteBatch();
        bird = new Bird(50, 250);
        tubes = new Array<Tube>();
        test = new Texture(Gdx.files.internal("test.png"));

        ground = new Texture(Gdx.files.internal("ground.png"));
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth/2, Ground_Offset);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth/2) + Ground_Width, Ground_Offset);

        stage = new Stage(new StretchViewport(Mainactivity.Width, Mainactivity.Height));
        skin = new Label.LabelStyle();
        skin.font = new BitmapFont(Gdx.files.internal("skin.fnt"), Gdx.files.internal("skin.png"), false);

        for(int i = 1; i <= Tube_Count; i++){
            tubes.add(new Tube(100 + i *(Tube_Space + Tube.Tube_Width)));
        }

        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
        Stack stack = new Stack();
        stack.setSize(Mainactivity.Width, Mainactivity.Height);
        stage.addActor(stack);
        stack.add(addScoreLabel());
    }


    private Table addScoreLabel() {
        Table layer = new Table();
        layer.top();
        lblScore = new Label("" + Integer.toString(score), skin);
        layer.add(lblScore);
        return layer;
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
        //batch.draw(test, bird.getBounds().x, bird.getBounds().y, bird.getBounds().getWidth(), bird.getBounds().getHeight());
        batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y, bird.getSize(), bird.getSize());
        for (Tube tube : tubes){
            batch.draw(tube.getTubeTop(), tube.getPosTubeTop().x, tube.getPosTubeTop().y, Tube.Tube_Width, Tube.Tube_Height);
            batch.draw(tube.getTubeBottom(), tube.getPosTubeBottom().x, tube.getPosTubeBottom().y, Tube.Tube_Width, Tube.Tube_Height);
        }

        batch.draw(ground, groundPos1.x, groundPos1.y, Ground_Width, Ground_Height);
        batch.draw(ground, groundPos2.x, groundPos2.y, Ground_Width, Ground_Height);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    private void update(float delta) {
        handleInput();
        updateGround();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);

            if(camera.position.x - camera.viewportWidth * 0.5f > tube.getPosTubeTop().x + Tube.Tube_Width){
                score++;
                // + Soundeffekt

                tube.reposition(tube.getPosTubeTop().x + (Tube.Tube_Width + Tube_Space) * Tube_Count);
            }
            if(tube.collides(bird.getBounds())){
                gameOver();
            }
        }

        //Check oberhalb des Bodens
        if(bird.getPosition().y +15 <= (Ground_Height + Ground_Offset)) {
            gameOver();
        }

        rebuildStage();
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

    private void updateGround(){
        if(camera.position.x - (camera.viewportWidth/2) > groundPos1.x + Ground_Width){
            groundPos1.add(Ground_Width*2, 0);
        }
        if(camera.position.x - (camera.viewportWidth/2) > groundPos2.x + Ground_Width){
            groundPos2.add(Ground_Width*2, 0);
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
        test.dispose();
        ground.dispose();
        skin.font.dispose();
        stage.dispose();
    }
}
