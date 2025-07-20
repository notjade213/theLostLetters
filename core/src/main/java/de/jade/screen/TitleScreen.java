package de.jade.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    public TitleScreen(Main game) {
        super(game);

        final Table table = new Table();

        // Declaring Variables, Label only used for Title
        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = game.getAssetManager().get(Assets.HUD_FONT);

        Sprite spriteTitle = new Sprite(game.getAssetManager().get(Assets.LOGO));
        Drawable drawableTitle = new SpriteDrawable(spriteTitle);
        Image imageTitle = new Image(drawableTitle);

        // Creating buttonStyle + buttonFont
        Sprite spriteSB = new Sprite(game.getAssetManager().get(Assets.START_BUTTON));
        Drawable drawableSB = new SpriteDrawable(spriteSB);

        TextButton.TextButtonStyle startbuttonStyle = new TextButton.TextButtonStyle();
        startbuttonStyle.font = new BitmapFont();
        startbuttonStyle.up = drawableSB;


        // Creating Start Button using the Text Button
        TextButton startButton = new TextButton("", startbuttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                //game.setScreen(new GameScreen(Main.camera));
                game.setScreen(new StartScreen(game));
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });


        // Creating Exit Button using the Text Button
        Sprite spriteEB = new Sprite(game.getAssetManager().get(Assets.EXIT_BUTTON));
        Drawable drawableEB = new SpriteDrawable(spriteEB);

        TextButton.TextButtonStyle exitbuttonStyle = new TextButton.TextButtonStyle();
        exitbuttonStyle.font = new BitmapFont();
        exitbuttonStyle.up = drawableEB;

        TextButton exitButton = new TextButton("", exitbuttonStyle);
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
        table.add(startButton).size(500).row();
        table.add(exitButton).size(500).row();
        table.align(topLeft).pad(50f);
        table.setFillParent(true);

        stage = new Stage(new ScreenViewport());
        stage.addActor(table);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(game.getAssetManager().get(Assets.TITLE_BACKGROUND), 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
    }
}
