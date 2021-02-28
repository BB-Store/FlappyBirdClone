package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;



import de.tutorial.flappybird.clone.Mainactivity;

public class MenuScreen implements Screen {

    private Button btnPlay;
    private Stage stage;
    private Label.LabelStyle skin;
    private int highscore;
    private Mainactivity game;

    public MenuScreen(Mainactivity game, int score){
        this.game = game;
        stage = new Stage(new StretchViewport(Mainactivity.Width, Mainactivity.Height));
        skin = new Label.LabelStyle();
        skin.font = new BitmapFont(Gdx.files.internal("skin.fnt"), Gdx.files.internal("skin.png"), false);
        Gdx.input.setInputProcessor(stage);

        if(score > highscore){
            highscore = score;
        }

        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Mainactivity.Width, Mainactivity.Height);
        stack.add(createPlayButton());
        stack.add(createHigscoreLabel());
    }

    private Table createHigscoreLabel() {
        Table layer = new Table();
        layer.center();
        Label label = new Label("Highscore:", skin);
        layer.add(label).padBottom(300);
        layer.row();
        Label lblScore = new Label(Integer.toString(highscore), skin);
        layer.add(lblScore).padTop(50);
        return layer;
    }

    private Table createPlayButton() {
        Table layer = new Table();
        btnPlay = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_btn.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play_btn-dn.png")))));
        layer.add(btnPlay).width(180).height(180);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }

            private void startGame() {
                btnPlay.addAction(Actions.sequence(Actions.fadeOut(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(game));
                    }
                })));
            }
        });

        return layer;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        stage.dispose();
        skin.font.dispose();
    }
}
