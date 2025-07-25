package de.jade.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.jade.Assets;
import de.jade.Main;

import static com.badlogic.gdx.utils.Align.center;

public class StartScreen implements Screen {
    private static final String START_MESSAGE = "Press any Button to Continue";

    private final Stage stage;
    private Main game;

    public StartScreen(Main game) {
        this.game = game;

        final Table table = new Table();
        table.setFillParent(true);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = game.assetManager.get(Assets.HUD_FONT);

        Label titleLabel = new Label("The Wild Gooners", style);
        titleLabel.setFontScale(5f);
        table.add(titleLabel).padBottom(60.0f).colspan(2).row();

        Label messageLabel = new Label(START_MESSAGE, style);
        messageLabel.setFontScale(2f);
        table.add(messageLabel).align(center).row();


        stage = new Stage(new ScreenViewport());
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keypressed) {
                game.setScreen(new TitleScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,10f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        //game.getBatch().draw(game.getAssetManager().get(Assets.TITLE_BACKGROUND), 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
