package de.jade.player;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import de.jade.Assets;
import de.jade.Main;
import de.jade.helper.Constans;
import de.jade.player.abilities.Attack;


public class Obanana extends Sprite {
    public World world;
    public Body b2body;
    private float velocityX;
    private TextureAtlas obananaTex;
    private TextureRegion obananaIdle;
    private Sprite sprite;
    private Attack attack;

    // Obanana Main Stats
    public int lives = 3;
    /* public keys unlockedKeys; */
    public int damamge = 50;

    public Obanana(World world, Main main) {
        super(main.assetManager.get(Assets.OBANANA).findRegion("obananaStand"));
        this.world = world;
        this.attack = new Attack();
        defineObanana();
        obananaIdle = new TextureRegion(getTexture(), 0, 0 , 32, 32);
        setBounds(0, 0, 32 / Constans.PPM, 32 / Constans.PPM);
        setRegion(obananaIdle);
    }

    public void defineObanana() {
        //sprite = new Sprite(obananatex, 32, 32);

        BodyDef bdef = new BodyDef();
        bdef.position.set(1300f, 1700f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f / Constans.PPM, 1f / Constans.PPM);

        fdef.shape = shape;
       // b2body.setLinearDamping(0.5f);
        fdef.friction = 2.5f; // 2.5f
        b2body.createFixture(fdef);
    }

    public void action(String type, Obanana player) {
        switch(type) {
            case "JUMP":
                player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
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
                player.b2body.setLinearVelocity(new Vector2(20f,0));
                break;
            default:
                break;
        }
    }

    public void update(float delta) {
    }

    public void setPosition(float x, float y) {

    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


    public Attack getAttack(){
        return this.attack;
    }
    // Specified stuff follows here
}
