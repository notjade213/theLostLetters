package de.jade.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class InputProcessor extends InputAdapter implements Telegraph {
    private final static int JUMP_KEY = Input.Keys.W;
    private final static int LEFT_KEY = Input.Keys.A;
    private final static int RIGHT_KEY = Input.Keys.D;
    private final static int DUCK_KEY = Input.Keys.S;
    private final static int RESET_KEY = Input.Keys.R;

    private final Entity character;

    public InputProcessor(Entity character) {
        this.character = character;
    }


    @Override
    public boolean handleMessage(Telegram telegram) {
        return false;
    }
}
