package net.avuna.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Position {

	private int x, y, height;

	public Position(int x, int y) {
		this(x, y, 0);
	}

	public int distanceTo(Position other) {
		return manhattanDistance(this, other);
	}

	public boolean isWithinArea(Position bound1, Position bound2) {
		return isWithinArea(this, bound1, bound2);
	}

	public boolean isWithinRadius(Position circleCenter, int radius) {
		return isWithinRadius(this, circleCenter, radius);
	}

	public static boolean isWithinRadius(Position pos, Position circleCenter, int radius) {
		int dx = Math.abs(pos.getX() - circleCenter.getX());
		int dy = Math.abs(pos.getY() - circleCenter.getY());
		return (dx * dx + dy * dy <= radius * radius);
	}

	private static int manhattanDistance(Position pos, Position other) {
		return Math.abs(pos.getX() - other.getX()) + Math.abs(pos.getY() - other.getY());
	}

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
