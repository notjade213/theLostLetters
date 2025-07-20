package de.jade.player;

public interface PlayerStatusObserver {
    void onPlayerStatusChange(final PlayerStatus event);
}
