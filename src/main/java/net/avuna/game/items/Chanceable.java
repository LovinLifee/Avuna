package net.avuna.game.items;

public interface Chanceable {

    public double getChance();

    public default boolean isDroppedAlways() {
        return getChance() >= 1.0;
    }
}
