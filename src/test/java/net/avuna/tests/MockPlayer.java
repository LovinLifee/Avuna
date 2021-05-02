package net.avuna.tests;

import lombok.RequiredArgsConstructor;
import net.avuna.game.entity.player.AbstractPlayer;
import net.avuna.util.Console;

@RequiredArgsConstructor
public class MockPlayer extends AbstractPlayer {

	private final String name;
	
	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("+" + Console.PURPLE + message + Console.RESET);
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}
}