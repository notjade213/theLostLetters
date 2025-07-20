package de.jade;

import com.badlogic.gdx.Screen;

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
