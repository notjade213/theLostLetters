package de.jade;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.jade.player.CurrentPlayerStatus;
import de.jade.screen.GameScreen;
import de.jade.screen.StartScreen;
import de.jade.screen.TitleScreen;


public class Main extends Game implements Telegraph {

    // Application Vars
    public static final String GAME_TITLE = "The Lost Letters | Version 1.0";

    // Batches
    public Batch batch;

    // Managers
    public AssetManager assetManager;

    // Misc
    private final CurrentPlayerStatus status;
    public static OrthographicCamera camera;

    public Main() {
        status = new CurrentPlayerStatus();
    }

    @Override
    public void create () {
        assetManager = new AssetManager();
        assetManager.load(Assets.HUD_FONT);
        assetManager.load(Assets.TITLE_BACKGROUND);
        assetManager.load(Assets.LOGO);
        assetManager.load(Assets.EXIT_BUTTON);
        assetManager.load(Assets.START_BUTTON);
        assetManager.load(Assets.OBANANA);
        assetManager.finishLoading();

        batch = new SpriteBatch();

        camera = new OrthographicCamera();

        setScreen(new GameScreen(this));
        //setScreen(new StartScreen(this));

    }

    @Override
    public void render () {
        super.render();

    }

    @Override
    public void dispose () {
        super.dispose();
        batch.dispose();
        assetManager.dispose();
    }

    // Sets the state of the game to what it was when it first began, set to "r" for testing
    public void reset() {
        status.reset();
        getScreen().dispose();
        setScreen(new GameScreen(this));
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

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Batch getBatch() {
        return batch;
    }
}
