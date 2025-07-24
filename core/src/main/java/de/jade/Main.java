package de.jade;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.jade.screen.GameScreen;


public class Main extends Game implements Telegraph {

    // Application Vars

    // Batches
    public Batch batch;

    // Managers
    public  AssetManager assetManager;

    // Misc
    public static OrthographicCamera camera;

    public Main() {
    }

    @Override
    public void create () {
        this.assetManager = new AssetManager();
        assetManager.load(Assets.HUD_FONT);
        assetManager.load(Assets.TITLE_BACKGROUND);
        assetManager.load(Assets.LOGO);
        assetManager.load(Assets.EXIT_BUTTON);
        assetManager.load(Assets.START_BUTTON);
        assetManager.load(Assets.OBANANA);
        assetManager.load(Assets.TUTORIAL_THEME);
        assetManager.load(Assets.MENU_THEME);
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
    }

    public void reset() {
        getScreen().dispose();
    }

    @Override
    public void resume () {
        super.resume();
        if (getScreen() != null) {
            getScreen().resume();
        }
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
        super.pause();
        if (getScreen() != null) {
            getScreen().pause();
        }
    }


    @Override
    public boolean handleMessage(Telegram msg) {

        return false;
    }

    public Batch getBatch() {
        return batch;
    }
}
