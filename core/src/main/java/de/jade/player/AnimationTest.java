package de.jade.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.Constans;

public class AnimationTest extends Sprite {

    private Animation obananaRun;
    private Animation obananaDash;
    private Animation obananaDeath;
    private Animation obananaFall;
    private Animation obananaIdle;
    private TextureRegion obananaLand;
    private Animation obananaPunch;
    private TextureRegion obananaJump;
    private TextureRegion obananaDuck;

    private float stateTimer;

    public AnimationTest(Main main) {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("RunSprite"), i * 32, 0, 32, 32));
        obananaRun = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("FallSprite"), i * 32, 0, 32, 32));
        obananaFall = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("DashSprite"), i * 32, 0, 32, 32));
        obananaDash = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 8; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("DeathSprite"), i * 32, 0, 32, 32));
        obananaDeath = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("IdleSprite"), i * 32, 0, 32, 32));
        obananaIdle = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("PunchSprite"), i * 32, 0, 32, 32));
        obananaPunch = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();
    }

    public void update(SpriteBatch batch) {
        stateTimer += Gdx.graphics.getDeltaTime();

        TextureRegion obananaRun = (TextureRegion) this.obananaRun.getKeyFrame(stateTimer, true);
        TextureRegion obananaDash = (TextureRegion) this.obananaDash.getKeyFrame(stateTimer, true);
        TextureRegion obananaDeath = (TextureRegion) this.obananaDeath.getKeyFrame(stateTimer, true);
        TextureRegion obananaPunch = (TextureRegion) this.obananaPunch.getKeyFrame(stateTimer, true);


        batch.draw(obananaRun, 0,300f / Constans.PPM, 5, 5);
        batch.draw(obananaDash, 150f / Constans.PPM, 300f / Constans.PPM, 5, 5);
        batch.draw(obananaDeath, 300f / Constans.PPM, 300f / Constans.PPM, 5, 5);
        batch.draw(obananaPunch, 450f / Constans.PPM, 300f / Constans.PPM, 5, 5);

    }
}
