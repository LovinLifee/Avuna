package net.avuna.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {

    /*
        String message = "Hello [USERNAME], how are you?" (this is part of a config)
        Map<String, String> tokens = Map.of("[USERNAME]", player.getName());
        player.sendMessage(replaceTokens(message, tokens));
     */
    private static final Pattern defaultPattern = Pattern.compile("\\[(.+?)\\]");

    /**
     * Default token pattern is: [Token]
     * @param text the text in which you want to replace the tokens
     * @param tokens A map where the key is the token you want replaced and the value is what to replace it with
     * @return a modified string where all the specified tokens in the string have been replaced
     */
    public static String replaceTokens(String text, Map<String, String> tokens) {
        return replaceTokens(defaultPattern, text, tokens);
    }

    public static String replaceTokens(Pattern tokenMatcher, String text, Map<String, String> tokens) {
        Matcher matcher = tokenMatcher.matcher(text);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String replacement = tokens.get(matcher.group(1));
            builder.append(text, i, matcher.start());
            if (replacement == null) {
                builder.append(matcher.group(0));
            } else {
                builder.append(replacement);
            }
            i = matcher.end();
        }
        builder.append(text.substring(i));
        return builder.toString();
    }
}
