package net.avuna.tests;

import net.avuna.security.PlayerPermissions;

public class TestPermissions {

    public static void main(String[] args) {
        MockPlayer player = new MockPlayer("Hayden");
        player.getPermissions().setPermissions(PlayerPermissions.DEVELOPER);
        player.sendMessage(player.getPermissions().toString());
    }
}
