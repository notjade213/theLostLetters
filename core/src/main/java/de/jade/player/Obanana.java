package de.jade.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Array;
import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.Constans;
import de.jade.player.abilities.Attack;

public class Obanana extends Sprite {
    public World world;
    public Body b2body;
    private float velocityX;
    private Sprite sprite;
    private Attack attack;
    private Boolean obananaIsDead;

    private Animation obananaRun;
    private TextureRegion obananaDash;
    private TextureRegion obananaDeath;
    private TextureRegion obananaFall;
    private TextureRegion obananaIdle;
    private TextureRegion obananaLand;
    private TextureRegion obananaPunch;
    private TextureRegion obananaJump;
    private TextureRegion obananaDuck;

    private float stateTimer;
    public enum State { FALL, JUMP, IDLE, RUN, PUNCH, DEAD, LAND, DUCK, DASH };
    public State currentState;
    public State previousState;

    public int lives = 3;
    public int damamge = 50;

    public Obanana(World world, Main main) {
        this.world = world;
        this.attack = new Attack();
        defineObanana();
        stateTimer = 0f;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 3; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("RunSprite"), i * 32, 0, 32, 32));
        obananaRun = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        obananaIdle = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("IdleSprite"));
        obananaJump = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("JumpSprite"));
        obananaDeath = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("DeathSprite"));

        setBounds(getX(), getY(), 1f , 1f);
        setRegion(obananaIdle);
    }

    public void defineObanana() {
        obananaIsDead = false;

        BodyDef bdef = new BodyDef();
        bdef.position.set(10f, 10f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        fdef.shape = shape;
        fdef.friction = 0.8f;
        b2body.createFixture(fdef);
    }

    public void action(String type, Obanana player) {
        switch(type) {
            case "JUMP":
                player.b2body.applyLinearImpulse(new Vector2(0, 8f), player.b2body.getWorldCenter(), true);
                break;
            case "DUCK":
                break;
            case "RIGHT":
                if (player.b2body.getLinearVelocity().x <= 2) {
                    player.b2body.applyLinearImpulse(new Vector2(2f, 0), player.b2body.getWorldCenter(), true);
                }
                break;
            case "LEFT":
                if (player.b2body.getLinearVelocity().x >= -2) {
                    player.b2body.applyLinearImpulse(new Vector2(-2f, 0), player.b2body.getWorldCenter(), true);
                }
                break;
            case "ATTACK":
                attack.PunchAttack(player);
                break;
            case "DASH":
                player.b2body.setLinearVelocity(new Vector2(20f, 0));
                break;
            default:
                break;
        }
    }

    public void update(float delta) {
        setRegion(getFrame(delta));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = obananaDeath;
                break;
            case JUMP:
                region = obananaJump;
                break;
            case RUN:
                region = (TextureRegion) obananaRun.getKeyFrame(stateTimer, true);
                break;
            case FALL:
            case IDLE:
            default:
                region = obananaIdle;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public Attack getAttack() {
        return this.attack;
    }

    public State getState() {
        if (obananaIsDead)
            return State.DEAD;
        else if ((b2body.getLinearVelocity().y > 0 && currentState == State.JUMP) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMP))
            return State.JUMP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALL;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUN;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            return State.DUCK;
        } else
            return State.IDLE;
    }

    public void die() {
        if (!isDead()) {
            obananaIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead() {
        return obananaIsDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }
}
