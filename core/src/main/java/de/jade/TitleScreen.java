package de.jade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TitleScreen extends AbstractScreen {
    private static final String TITLE = "The Lost Letters";
    private static final String OPTION_ONE = "Start";
    private static final String OPTION_TWO = "Exit Game";
    private static final String OPTION_THREE = "Settings";

    private final Stage stage;

    // I have no fucking idea who this shit doesn't fucking crash but whatever, I'll clean it up tomorrow 👍
    public TitleScreen(Main game) {
        super(game);

        final Table table = new Table();

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.BLACK;
        style.font = new BitmapFont();

        Label titleLabel = new Label(TITLE, style);
        titleLabel.setFontScale(1.2f);
        table.add(titleLabel).padBottom(60.0f).colspan(2).row();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        TextButton startButton = new TextButton(OPTION_ONE, buttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(final InputEvent event, final float x, final float y, final int pointer, final Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }
        });

        table.add(startButton).right().row();
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
