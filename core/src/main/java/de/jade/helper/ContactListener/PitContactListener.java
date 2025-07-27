package de.jade.helper.ContactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.jade.player.Obanana;

public class PitContactListener implements ContactListener {
    private Obanana player;

    public PitContactListener(Obanana player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {

        if(contact.getFixtureA() == null || contact.getFixtureB() == null) return;
        if(contact.getFixtureA().getUserData() == null || contact.getFixtureB().getUserData() == null) return;

        if (contact.getFixtureA().getUserData().equals("player") && contact.getFixtureB().getUserData().equals("pit") ||
            contact.getFixtureB().getUserData().equals("player") && contact.getFixtureA().getUserData().equals("pit")) {

            player.setObananaIsPitDead(true);
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
