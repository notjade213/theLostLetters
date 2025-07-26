package de.jade.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import de.jade.Assets;
import de.jade.Main;

public class Door {
        private Animation openDoor;
        private float stateTimer;

        private boolean doorOpen = false;
        private Main main;
        private Vector2 doorPos;

    public Door(Main main) {
        this.main = main;

        stateTimer = 0f;
        doorPos = new Vector2(3072f / Constans.PPM, 14f);

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 7; i++) {
            frames.add(new TextureRegion(main.assetManager.get(Assets.DOOR).findRegion("Door"), i * 64, 0, 64, 64));
            openDoor = new Animation<TextureRegion>(0.3f, frames);
        }
    }

    public boolean isDoorOpen() {
        return openDoor.isAnimationFinished(stateTimer);
    }

    public void update(SpriteBatch batch, Screen nextLevel, Body player) {
        stateTimer += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) openDoor.getKeyFrame(stateTimer, false);

        batch.draw(currentFrame, 3074f / Constans.PPM,447f / Constans.PPM, 2, 2);

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && isDoorOpen()
            && player.getPosition().dst2(doorPos) <= 2f) {
            main.setScreen(nextLevel);
        }
    }

}
