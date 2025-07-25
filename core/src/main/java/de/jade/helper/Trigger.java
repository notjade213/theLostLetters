package de.jade.helper;

import com.badlogic.gdx.math.Vector2;

public class Trigger {
    private final Vector2 target;
    private final float radius;
    private Runnable action;
    private boolean triggered;

    public Trigger(Vector2 target, float radius, Runnable action) {
        this.target = target;
        this.radius = radius;
        this.action = action;
    }

    public void update(Vector2 playerPosition) {
        if(!triggered && playerPosition.dst(target) <= radius) {
            triggered = true;
            action.run();
        }
    }

    public boolean isTriggered() {
        return triggered;
    }
}
