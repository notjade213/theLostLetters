package de.jade.player;

import com.badlogic.gdx.utils.Pool.Poolable;

public final class CurrentPlayerStatus implements PlayerStatus, Poolable {

     private short lives;
     private short bananas;
     private short level;


    public CurrentPlayerStatus() {
        lives = DEFAULT_LIVES;
        bananas = DEFAULT_BANANAS;
        level = DEFAULT_LEVEL;
    }


    @Override
    public void reset() {
        lives = DEFAULT_LIVES;
        bananas = DEFAULT_BANANAS;
        level = DEFAULT_LEVEL;
    }

    // Methods to get and set Bananas
    @Override
    public short getBananas() {
        return bananas;
    }

    protected void setBananas(final short bananas) {
        this.bananas = bananas  ;
    }

    // Methods to get and set Lives
    @Override
    public short getLives() {
        return lives;
    }

    protected void setLives(final short lives) {
        this.lives = lives ;
    }

    // Methods to get and set a Level
    @Override
    public short getLevel() {
        return level;
    }

    protected void setLevel(final short level) {
        this.level = level  ;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[bananas=").append(bananas).append(", lives=").append(lives).append(", level=")
            .append(level).append("]");
        return builder.toString();
    }
}

