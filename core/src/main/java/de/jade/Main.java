package de.jade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import de.jade.player.CurrentPlayerStatus;


public class Main extends Game implements Telegraph {

    // Application Vars
    public static final String GAME_TITLE = "The Lost Letters | Version 1.0";

    // Batches
    public Batch batch;

    // Managers
    private AssetManager assetManager;

    // Misc
    private final CurrentPlayerStatus status;

    public Main() {
        status = new CurrentPlayerStatus();
    }

    @Override
    public void create () {

        setScreen(new TitleScreen(this));
    }

    @Override
    public void render () {

    }

    @Override
    public void dispose () {
    }

    // Sets the state of the game to what it was when it first began, set to "r" for testing
    public void reset() {
        status.reset();
        getScreen().dispose();
        setScreen(new TitleScreen(this));
    }

    @Override
    public void resume () {
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
    }


    @Override
    public boolean handleMessage(Telegram msg) {

        return false;
    }
}
