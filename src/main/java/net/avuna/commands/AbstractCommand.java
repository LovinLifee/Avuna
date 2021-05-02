package net.avuna.commands;

import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.game.entity.player.IPlayer;

import java.util.Objects;

public abstract class AbstractCommand<T extends IPlayer> {
	
	public abstract void execute(T sender, String commandName, ArgumentParser parser);
	public abstract String commandName();
	public abstract String description();

	public long permissions() {
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandName(), description(), permissions());
	}
}
