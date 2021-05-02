package net.avuna.game.trivia;

import lombok.Getter;
import net.avuna.config.Config;

import java.nio.file.Path;
import java.util.List;

@Getter
public class TriviaConfig extends Config {
    private long delayInTicks = 1000;
    private String eventStartMessage;
    private String eventNotStartedMessage;
    private String wrongAnswerMessage;
    private List<Question> questions = null;

    public TriviaConfig(Path jsonFile, boolean hasDefaultResource) {
        super(jsonFile, hasDefaultResource);
    }
}
