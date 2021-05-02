package net.avuna.util;

import lombok.experimental.UtilityClass;
import net.avuna.game.Position;

import java.util.*;

@UtilityClass
public class MathUtils {
	
	private static final Random random;
	
	static {
		random = new Random();
	}
	
	/*
	 * Map a given value to a range
	 * Example: map the users slayer xp min: 1, max: 200m to 100% -> 110% drop rate potential
	 * map(usersSlayerExperience, 1, 200_000_000, 1D, 1.10D);
	 * 
	 */
	public static float map(float value, float minValue, float maxValue, float minExpectedValue, float maxExpectedValue) {
        return minExpectedValue + (maxExpectedValue - minExpectedValue) * ((value - minValue) / (maxValue - minValue));
    }

    /*
    	lowerBounds and higherBounds are inclusive
     */
	public static int randomRange(int lowerBounds, int higherBounds) {
		return random.nextInt((higherBounds - lowerBounds) + 1) + lowerBounds;
	}

	/*
		value should be a number between 0-1 inclusive
	 */
	public static int greenRedGradientColor(float value) {
		return java.awt.Color.HSBtoRGB(value / 3f, 1f, 1f);
	}

	public static List<Position> plotPointsOnCircle(int centerX, int centerY, int radius) {
		List<Position> points = new ArrayList<>();
		for (int angle = 0; angle < 360; angle++) {
			double xOffset = radius * Math.cos(angle * Math.PI / 180);
			double yOffset = radius * Math.sin(angle * Math.PI / 180);
			int pointX = (int) Math.round(centerX + xOffset);
			int pointY = (int) Math.round(centerY + yOffset);
			points.add(new Position(pointX, pointY));
		}
		return points;
	}

	public static int secondsToTicks(long seconds) {
		return (int) Math.ceil(seconds / 0.6);
	}

	public static boolean IsPowerOfTwo(long number, boolean excludeZero) {
		if(excludeZero) {
			return (number != 0) && ((number & (number - 1)) == 0);
		}
		return (number & (number - 1)) == 0;
	}
}
