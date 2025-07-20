package de.jade.screen;

import com.badlogic.gdx.Screen;
import de.jade.Main;

public abstract class AbstractScreen implements Screen {
    protected final Main game;

    public AbstractScreen(final Main game) {
        this.game = game;
    }

    @Override
    public void hide() {
        dispose();

    }
}
