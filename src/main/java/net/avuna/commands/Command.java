package net.avuna.commands;

import net.avuna.tasks.security.PlayerPermissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	/**
	 * Returns the aliases this certain {@code Command} has relating to it.
	 * 
	 * @return the aliases
	 */
	public String[] aliases();

	/**
	 * Returns the description of what this {@code Command} executes.
	 * 
	 * @return the description
	 */
	public String description() default "";

	/**
	 * Returns the syntax of how this {@code Command} should be used.
	 * 
	 * @return the syntax
	 */
	public String syntax() default "";

	/**
	 * @return the player rights needed to execute this command
	 */
	public long permissions() default PlayerPermissions.GLOBAL;
}
