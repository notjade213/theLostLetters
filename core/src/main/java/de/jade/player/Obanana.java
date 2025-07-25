package de.jade.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.Array;
import de.jade.Assets;
import de.jade.Main;
import de.jade.player.abilities.Attack;

public class Obanana extends Sprite {
    public World world;
    public Body b2body;
    private final Attack attack;

    private boolean obananaIsDead;
    private boolean obananaIsPitDead;
    private boolean runningRight;
    private Animation obananaRun;
    private TextureRegion obananaDash;
    private TextureRegion obananaDeath;
    private Animation obananaFall;
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
    public int damage = 50;

    public Obanana(World world, Main main) {
        this.world = world;
        this.attack = new Attack();
        defineObanana(main);
    }

    public void defineObanana(Main main) {
        obananaIsDead = false;
        obananaIsPitDead = false;

        // Body
        BodyDef bdef = new BodyDef();
        bdef.position.set(10f, 10f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(0.5f, 0.5f);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = bodyShape;
        fdef.friction = 0f;

        PolygonShape feetShape = new PolygonShape();
        feetShape.setAsBox(0.49f, 0.01f, new Vector2(0f, -0.5f), 0f);

        FixtureDef feetDef = new FixtureDef();
        feetDef.shape = feetShape;
        feetDef.friction = 1f;

        b2body.createFixture(fdef);
        b2body.createFixture(feetDef).setUserData("player");

        // Animations
        stateTimer = 0f;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("RunSprite"), i * 32, 0, 32, 32));
        obananaRun = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("FallSprite"), i * 32, 0, 32, 32));
        obananaFall = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        obananaIdle = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("IdleSprite"));
        obananaJump = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("JumpSprite"));
        obananaDeath = new TextureRegion(main.assetManager.get(Assets.OBANANA).findRegion("DeathSprite"));

        setBounds(getX(), getY(), 1f, 1f);
        setRegion(obananaIdle);
    }

    public void action(String type, Obanana player) {
        switch(type) {
            case "JUMP":
                jump();
                break;
            case "DUCK":
                break;
            case "RIGHT":
                if (player.b2body.getLinearVelocity().x <= 2.5f) {
                    player.b2body.setLinearVelocity(0f, b2body.getLinearVelocity().y);
                    player.b2body.applyLinearImpulse(new Vector2(2.5f, 0), player.b2body.getWorldCenter(), true);
                }
                break;
            case "LEFT":
                if (player.b2body.getLinearVelocity().x >= -2.5) {
                    player.b2body.setLinearVelocity(0f, b2body.getLinearVelocity().y);
                    player.b2body.applyLinearImpulse(new Vector2(-2.5f, 0), player.b2body.getWorldCenter(), true);
                }
                break;
            case "ATTACK":
                attack.PunchAttack(player);
                break;
            case "DASH":
                player.b2body.applyLinearImpulse(new Vector2(5f, 0), b2body.getWorldCenter(), true);
                break;
            default:
                break;
        }
    }

    public void update(float delta) {
        setRegion(getFrame(delta));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if(obananaIsPitDead) {
            pitDie();
        }
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
                region = (TextureRegion) obananaFall.getKeyFrame(stateTimer, false);
                break;
            case IDLE:
            default:
                region = obananaIdle;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
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
        else if (b2body.getLinearVelocity().y < 0 || b2body.getLinearVelocity().y < -10f )
            return State.FALL;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUN;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            return State.DUCK;
        } else
            return State.IDLE;
    }

    public void pitDie() {
        obananaIsPitDead = true;
        b2body.setLinearVelocity(0, 0);
        b2body.setTransform(10f, 11.5f,0);
        obananaIsPitDead = false;
    }

    public void die() {

    }

    public boolean isDead() {
        return obananaIsDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void jump(){
        if ( currentState != State.JUMP && b2body.getLinearVelocity().y >= -1.2 ) {
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 0);
            b2body.applyLinearImpulse(new Vector2(0, 7f), b2body.getWorldCenter(), true);
            currentState = State.JUMP;
        }
    }

    public boolean isObananaIsPitDead() {
        return isObananaIsPitDead();
    }

    public void setObananaIsPitDead(boolean value) {
            obananaIsPitDead = value;
    }
}
