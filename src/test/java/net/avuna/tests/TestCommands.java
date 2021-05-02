package net.avuna.tests;

import net.avuna.Avuna;
import net.avuna.commands.CommandNotFoundException;
import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.security.PlayerPermissions;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.security.InsufficientPermissionsException;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TestCommands {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Avuna.bootstrap();

        final IPlayer player = new MockPlayer("Hayden");
        final IPlayer[] players = {player};

        Avuna.getPlayers().bind(players);

        Avuna.getCommandHandler().registerCommandListener(new TestCommands());

        takeInputUntil(input -> {
            try {
                Avuna.getCommandHandler().execute(player, input);
            } catch (InsufficientPermissionsException e) {
                player.sendMessage("You do not have the rights to use this command");
            } catch (CommandNotFoundException e) {
                player.sendMessage("The command (" + e.getCommandName() + ") does not exist");
            }
        }, terminate -> {
            return terminate != null && terminate.equalsIgnoreCase("exit");
        });
        System.exit(0);
    }
    
    private static void takeInputUntil(Consumer<String> consumer, Predicate<String> terminateWhen) {
        String input = null;
        while(!terminateWhen.test(input)) {
            input = takeInput();
            consumer.accept(input);
        }
    }

    private static String takeInput() {
        System.out.print("cmd>");
        String input = scanner.nextLine();
        return input != null ? input : "";
    }

    @Command(aliases = {"echo"})
    public void echoCommand(MockPlayer player, String cmdName, ArgumentParser args) {
        player.sendMessage("Echo: " + Arrays.toString(args.getArguments()));
    }

    @Command(aliases = {"echo2"}, permissions = PlayerPermissions.DEVELOPER)
    public void helpCommand(MockPlayer player, String cmdName, ArgumentParser args) {
        player.sendMessage("Echo: " + Arrays.toString(args.getArguments()));
    }
}
