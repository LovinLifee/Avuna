package net.avuna.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.RequiredArgsConstructor;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;

@RequiredArgsConstructor
public class CommandMethod<T extends IPlayer> extends AbstractCommand<T> {
	
	private final String commandName;
	private final Method method;
	private final Command command;
	private final Object listener;

	@Override
	public void execute(T sender, String commandName, ArgumentParser parser) {
		try {
			method.invoke(listener, sender, commandName, parser);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long permissions() {
		return command.permissions();
	}

	@Override
	public String commandName() {
		return commandName;
	}

	@Override
	public String description() {
		return command.description();
	}
}
