package net.avuna.security.punishments;

import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.event.Event;
import net.avuna.event.impl.entity.player.PlayerChatEvent;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.security.PlayerPermissions;
import net.avuna.util.Priority;

//TODO: DO THIS YA LIL BITCH PT. 2
public class MuteHandler {

    public boolean isMuted(String username) {
        return false;
    }

    @Command(aliases = {"mute"}, permissions = PlayerPermissions.DEVELOPER)
    public void mutePlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Command(aliases = {"warnmute"}, permissions = PlayerPermissions.DEVELOPER)
    public void warnMutePlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Command(aliases = {"unmute"}, permissions = PlayerPermissions.DEVELOPER)
    public void unmutePlayer(IPlayer player, String command, ArgumentParser args) {

    }

    @Event(priority = Priority.HIGH)
    private void onPlayerChat(PlayerChatEvent<IPlayer> event) {
        if(isMuted(event.getPlayer().getUsername())) {
            event.setCancelled(true);
        }
    }
}
