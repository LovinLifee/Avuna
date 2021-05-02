package net.avuna.security;

import lombok.Getter;
import net.avuna.Avuna;
import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.tasks.Task;
import net.avuna.util.MathUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfirmationModule {

    @Getter
    private Map<String, Confirmation> tasks = new ConcurrentHashMap<>();

    public void registerConfirmation(IPlayer player, Confirmation confirmation) {
        String username = player.getUsername();
        tasks.put(username, confirmation);
        Avuna.getTaskManager().schedule(new Task(false) {
            @Override
            public void execute() {
                if(getTicksEllapsed() >= MathUtils.secondsToTicks(confirmation.getSecondsUntilExpire())) {
                    Confirmation confirmation = tasks.remove(username);
                    if(confirmation != null) {
                        confirmation.onExpire();
                    }
                    stop();
                }
            }
        });
    }

    @Command(aliases = {"confirm"})
    public void confirm(IPlayer player, String command, ArgumentParser args) {
        Confirmation confirmation = tasks.remove(player.getUsername());
        if(confirmation != null) {
            confirmation.onConfirm();
        }
    }

    @Command(aliases = {"deny"})
    public void deny(IPlayer player, String command, ArgumentParser args) {
        Confirmation confirmation = tasks.remove(player.getUsername());
        if(confirmation != null) {
            confirmation.onDeny();
        }
    }
}
