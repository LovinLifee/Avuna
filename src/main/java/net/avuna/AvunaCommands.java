package net.avuna;

import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.tasks.security.Confirmation;
import net.avuna.tasks.security.PlayerPermissions;

import java.util.Optional;

/*
    TODO: add a configurable command prefix when sending command syntax to the player
 */
public class AvunaCommands {

    @Command(aliases = {"giverights"}, permissions = PlayerPermissions.DEVELOPER | PlayerPermissions.CONSOLE)
    public void giveRights(IPlayer player, String command, ArgumentParser args) {
        if (!args.ensureTypes(Integer.class, String.class)) {
            player.sendMessage("Please use as giverights (int)rights (string)\"player name\"");
            return;
        }
        final int playerRights = args.getInt(0);
        final String playerName = args.getString(1);
        player.sendMessage(String.format("You are trying to set %s rights to %s", playerName, PlayerPermissions.of(playerRights)));
        player.sendMessage("this is a potentially risky command, please type ::confirm or ::deny");
        player.sendMessage("You have 10 seconds before this automatically gets denied");
        Avuna.getConfirmationModule().registerConfirmation(player, new Confirmation(10) {
            @Override
            public void onConfirm() {
                Optional<IPlayer> playerOptional = Avuna.getPlayers().lookup(playerName);
                if (playerOptional.isPresent()) {
                    playerOptional.get().getPermissions().setPermissions(playerRights);
                } else {
                    player.sendMessage(playerName + " is not online");
                }
            }
            @Override
            public void onExpire() {
                player.sendMessage("Your 10 seconds has run out.");
            }
        });
    }

    @Command(aliases = {"voted", "claimvotes", "claimvote"})
    public void claimVotes(IPlayer player, String command, ArgumentParser args) {
        Avuna.getVoteHandler().checkVotes(player.getUsername());
    }

    @Command(aliases = {"donated", "claimdonation","claimdonated"})
    public void claimDonation(IPlayer player, String command, ArgumentParser args) {
        Avuna.getDonationHandler().checkDonations(player.getUsername());
    }

    @Command(aliases = {"yell"})
    public void yell(IPlayer player, String command, ArgumentParser args) {
        if(args.toString().isEmpty()) {
            player.sendMessage("Please use as ::yell <message>");
            return;
        }
        Avuna.getPlayers().sendMessageToAll(args.toString());
    }

    @Command(aliases = {"ping"})
    public void pingPong(IPlayer player, String command, ArgumentParser args) {
        player.sendMessage("pong");
    }
}
