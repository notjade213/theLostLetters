package de.jade.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.jade.Assets;
import de.jade.Main;

import static com.badlogic.gdx.utils.Align.*;

public class TitleScreen extends AbstractScreen {
    private static final String TITLE = "The Lost Letters";
    private static final String OPTION_ONE = "Start";
    private static final String OPTION_TWO = "Exit Game";
    private static final String OPTION_THREE = "Settings";

    private final Stage stage;
    private Music menuTheme;
    public static Table table;

    public TitleScreen(Main game) {
        super(game);

        table = new Table();
        menuTheme = game.assetManager.get(Assets.MENU_THEME);
        menuTheme.setLooping(true);

        // Declaring Variables, Label only used for Title
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = game.assetManager.get(Assets.HUD_FONT);

        Sprite spriteTitle = new Sprite(game.assetManager.get(Assets.LOGO));
        Drawable drawableTitle = new SpriteDrawable(spriteTitle);
        Image imageTitle = new Image(drawableTitle);

        // Creating start Button using Image Button
        Sprite spriteSB = new Sprite(game.assetManager.get(Assets.START_BUTTON));
        spriteSB.setSize(400,400);
        Drawable drawableSB = new SpriteDrawable(spriteSB);

        ImageButton startButton = new ImageButton(drawableSB);

        // Click Listener for start Button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });


        // Creating Exit Button as Image Button
        Sprite spriteEB = new Sprite(game.assetManager.get(Assets.EXIT_BUTTON));
        spriteEB.setSize(350,350);
        Drawable drawableEB = new SpriteDrawable(spriteEB);

        ImageButton exitButton = new ImageButton(drawableEB);

        // Click Listener for exit Button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });

        table.add(imageTitle).size(1000).row();
        table.add(startButton).align(left).row();
        table.add(exitButton).align(left).row();
        table.align(topLeft).pad(50f);
        table.setFillParent(true);

        stage = new Stage(new ScreenViewport());
        stage.addActor(table);

    }

    @Override
    public void show() {
        menuTheme.play();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(game.assetManager.get(Assets.TITLE_BACKGROUND), 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //game.getBatch().draw(game.getAssetsManager().get(Assets.LOGO), 0 , 0, 100,100);
        game.getBatch().end();

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        menuTheme.dispose();
    }

    // Used for Debuging Game Screen
    public static Table getTable() {
        return table;
    }
}
