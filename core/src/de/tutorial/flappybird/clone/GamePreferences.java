package de.tutorial.flappybird.clone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class GamePreferences {
    public static final GamePreferences instance = new GamePreferences();
    private Preferences prefs;
    public static final String PREFERENCES = "windfinger.prefs";

    public int highscore;

    private GamePreferences(){
        prefs = Gdx.app.getPreferences(PREFERENCES);
    }

    public void load() {
        highscore = prefs.getInteger("highscore", 0);
    }

    public void save(){
        prefs.putInteger("highscore", highscore);
        prefs.flush();
    }
}
