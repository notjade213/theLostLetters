package de.jade.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.jade.Assets;
import de.jade.Main;

public class StartScreen extends AbstractScreen implements InputProcessor {
    private static final String START_MESSAGE = "Press any Button to Continue";

    private final Stage stage;

    public StartScreen(Main game) {
        super(game);

        final Table table = new Table();
        table.setFillParent(true);

        Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = game.getAssetManager().get(Assets.HUD_FONT);

        Label titleLabel = new Label("The Lost Letters", style);
        titleLabel.setFontScale(10f);
        table.add(titleLabel).padBottom(60.0f).colspan(2).row();

        Label messageLabel = new Label(START_MESSAGE, style);
        messageLabel.setFontScale(3f);
        table.add(messageLabel).align(5).row();


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

    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
