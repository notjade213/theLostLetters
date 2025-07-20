package de.jade.player;

public interface PlayerStatus {
    public static final short DEFAULT_LIVES = 3;
    public static final short DEFAULT_LEVEL = 1;
    public static final short DEFAULT_BANANAS = 0;

    // Get players current Bananas
    short getBananas();

    // Get players current Lives
    short getLives();

    // Get players current Level
    short getLevel();
}
