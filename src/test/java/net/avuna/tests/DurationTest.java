package net.avuna.tests;

import net.avuna.util.DurationParser;

public class DurationTest {

    public static void main(String[] args) {
        String duration = "15s";
        long durationInMilli = DurationParser.parseInMillis(duration);
        System.out.printf("%s in milliseconds is %d\n", duration, durationInMilli);
        long durationInTicks = DurationParser.parseInTicks(duration);
        System.out.printf("%s in ticks is %d\n", duration, durationInTicks);
    }
}
