package de.jade.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class ObananaInput extends InputAdapter {
    private final static int JUMP_KEY = Input.Keys.W;
    private final static int DUCK_KEY = Input.Keys.S;
    private final static int RIGHT_KEY = Input.Keys.D;
    private final static int LEFT_KEY = Input.Keys.A;
    private final static int ATTACK_KEY = Input.Keys.SPACE;
    private final static int DASH_KEY = Input.Keys.E;

    public static void inputHandler(Obanana player) {

        if (Gdx.input.isKeyJustPressed(JUMP_KEY)) {
            player.action("JUMP", player);
        } else if (Gdx.input.isKeyPressed(DUCK_KEY)) {
            player.action("DUCK", player);
        } else if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
            player.action("RIGHT", player);
        } else if (Gdx.input.isKeyPressed(LEFT_KEY)) {
            player.action("LEFT", player);
        } else if (Gdx.input.isKeyJustPressed(ATTACK_KEY)) {
            player.action("ATTACK", player);
        } else if (Gdx.input.isKeyJustPressed(DASH_KEY)) {
            player.action("DASH", player);
        }
    }

}

