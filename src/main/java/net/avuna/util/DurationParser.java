package net.avuna.util;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationParser {

    private static final Pattern PATTERN = Pattern.compile("\\d+[a-zA-Z]");

    public static long parseInMillis(String data) {
        Matcher matcher = PATTERN.matcher(data);
        long time = 0;
        while(matcher.find()) {
            String[] split = matcher.group().split("(?<=\\d)(?=\\D)");
            int duration = Integer.parseInt(split[0]);
            String modifier = split[1];
            switch(modifier.toLowerCase()) {
                case "s":
                    time += TimeUnit.SECONDS.toMillis(duration);
                    break;
                case "m":
                    time += TimeUnit.MINUTES.toMillis(duration);
                    break;
                case "h":
                    time += TimeUnit.HOURS.toMillis(duration);
                    break;
                case "d":
                    time += TimeUnit.DAYS.toMillis(duration);
                    break;
            }
        }
        return time;
    }

    public static long parseInTicks(String data) {
        return MathUtils.secondsToTicks(TimeUnit.MILLISECONDS.toSeconds(parseInMillis(data)));
    }
}
