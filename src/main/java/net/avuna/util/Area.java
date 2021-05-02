package net.avuna.util;

import net.avuna.game.Position;

public class Area {

	public static boolean isWithinArea(Position pos, Position bound1, Position bound2) {
		int entityX = pos.getX();
		int entityY = pos.getY();
		int x1 = Math.min(bound1.getX(), bound2.getX());
		int y1 = Math.min(bound1.getY(), bound2.getY());
		int x2 = Math.max(bound1.getX(), bound2.getX());
		int y2 = Math.max(bound1.getY(), bound2.getY());
		return entityX >= x1 && entityX <= x2 && entityY >= y1 && entityY <= y2;
	}
}
