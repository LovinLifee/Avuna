package net.avuna.tests;

import net.avuna.Avuna;

import net.avuna.event.AbstractEvent;
import net.avuna.event.impl.entity.player.PlayerLogoutEvent;

public class TestOnlinePlayers {

    public static void main(String[] args) {
        Avuna.bootstrap();
        MockPlayer player = new MockPlayer("Hayden");
        AbstractEvent event = new PlayerLogoutEvent<>(player);
        Avuna.getEventManager().submit(event);
    }
}
