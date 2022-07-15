package net.avuna.tasks.security.punishments;

import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.event.Event;
import net.avuna.event.impl.entity.player.PlayerLoginEvent;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.tasks.security.PlayerPermissions;
import net.avuna.util.Priority;

//TODO: DO THIS YOU LITTLE BTICH
public class BanHandler {

    public boolean isBanned(String username) {
        return false;
    }

    @Command(aliases = {"ban"}, permissions = PlayerPermissions.ADMINISTRATOR_PLUS)
    public void banPlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Command(aliases = {"unban"}, permissions = PlayerPermissions.ADMINISTRATOR_PLUS)
    public void unbanPlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Command(aliases = {"warnban"}, permissions = PlayerPermissions.ADMINISTRATOR_PLUS)
    public void warnBanPlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Event(priority = Priority.HIGH)
    private void onLogin(PlayerLoginEvent<IPlayer> event) {
        if(isBanned(event.getPlayer().getUsername())) {
            event.setCancelled(true);
        }
    }
}
