package net.avuna.commands;

import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.tasks.security.InsufficientPermissionsException;
import net.avuna.tasks.security.PlayerPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {

	private static final Pattern PARSE_QUOTES = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");
	
	private final HashMap<String, AbstractCommand<? extends IPlayer>> commands = new HashMap<>();

	/**
	 * see {@link CommandHandler#execute(T, String, String[])}
	 */
	public <T extends IPlayer> void execute(T player, String rawCommand) throws InsufficientPermissionsException, CommandNotFoundException {
		Matcher matcher = PARSE_QUOTES.matcher(rawCommand);
		String commandName = "";
		List<String> arguments = new ArrayList<>();
		if (matcher.find()) {
			commandName = matcher.group(1);
		}
		while(matcher.find()) {
			arguments.add(matcher.group(1).replace("\"", ""));
		}
		execute(player, commandName, arguments.toArray(new String[0]));
	}

	public <T extends IPlayer> void execute(T sender, String commandName, String[] args) throws InsufficientPermissionsException, CommandNotFoundException {
		AbstractCommand<T> command = (AbstractCommand<T>) commands.get(commandName);
		if(command == null) {
			sender.sendMessage("The command %s does not exist", commandName);
			throw new CommandNotFoundException(commandName);
		}
		if(!sender.getPermissions().hasPermission(command.permissions())) {
			sender.sendMessage("You do not have permission to use this command.");
			throw new InsufficientPermissionsException(sender.getPermissions(), PlayerPermissions.of(command.permissions()));
		}
		ArgumentParser parser = new ArgumentParser(args);
		command.execute(sender, commandName, parser);
	}
	
	public <T extends IPlayer> void registerCommand(AbstractCommand<T> command) {
		commands.putIfAbsent(command.commandName(), command);
	}

	public <T extends IPlayer> void unregisterCommand(String commandName) {
		if(commands.containsKey(commandName)) {
			commands.remove(commandName);
		}
	}

	public <T extends IPlayer> void unregisterCommand(AbstractCommand<T> command) {
		unregisterCommand(command.commandName());
	}

	public void unregisterCommandListener(Object listener) {
		for(Method m : listener.getClass().getMethods()) {
			m.setAccessible(true);
			if(m.isAnnotationPresent(Command.class)) {
				Command command = m.getAnnotation(Command.class);
				for(String cmd : command.aliases()) {
					unregisterCommand(cmd);
				}
			}
		}
	}
	
	public void registerCommandListener(Object listener) {
		for(Method m : listener.getClass().getMethods()) {
			if(m.isAnnotationPresent(Command.class)) {
				Class<?>[] params = m.getParameterTypes();
				if(params.length != 3 || !IPlayer.class.isAssignableFrom(params[0]) || !params[1].equals(String.class) || !params[2].equals(ArgumentParser.class)) {
					throw new IllegalArgumentException(m.getName() + " must have 3 parameters (IPlayer, String, Arguments)");
				}
				Command command = m.getAnnotation(Command.class);
				for(String alias : command.aliases()) {
					CommandMethod<? extends IPlayer> method = new CommandMethod<>(alias, m, command, listener);
					registerCommand(method);
				}
			}
		}
	}
}