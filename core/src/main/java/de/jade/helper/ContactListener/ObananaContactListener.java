package de.jade.helper.ContactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.jade.Main;

public class ObananaContactListener implements ContactListener {
    private int groundContacts;



    // FIX THIS

    // add coyote

    public ObananaContactListener(Main main) {

    }

    @Override
    public void beginContact(Contact contact) {
        if("feet".equals(contact.getFixtureA().getUserData()) && "ground".equals(contact.getFixtureB().getUserData())
            || "feet".equals(contact.getFixtureB().getUserData()) && "ground".equals(contact.getFixtureA().getUserData())) {
            groundContacts++;
        }
    }

    @Override
    public void endContact(Contact contact) {
        if ("feet".equals(contact.getFixtureA().getUserData()) && "ground".equals(contact.getFixtureB().getUserData())
            || "feet".equals(contact.getFixtureB().getUserData()) && "ground".equals(contact.getFixtureA().getUserData())) {
            groundContacts = Math.max(0, groundContacts - 1);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isGrounded() {
        return groundContacts > 0;
    }
}
