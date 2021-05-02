package net.avuna.game.trivia;

import lombok.Getter;
import net.avuna.Avuna;
import net.avuna.commands.Command;
import net.avuna.commands.arguments.ArgumentParser;
import net.avuna.config.Configurable;
import net.avuna.event.impl.game.TriviaCorrectEvent;
import net.avuna.game.entity.player.IPlayer;
import net.avuna.tasks.Task;
import net.avuna.util.TemplateEngine;

import java.util.Map;

@Getter
public class TriviaHandler extends Task implements Configurable<TriviaConfig> {

    private final TriviaConfig config;
    private Question currentQuestion;

    public TriviaHandler(TriviaConfig config) {
        super(false);
        this.config = config;
    }

    @Command(aliases={"answer"}, description = "Answer trivia questions")
    public void answerTriviaCommand(IPlayer player, String command, ArgumentParser args) {
        if(currentQuestion == null) {
            player.sendMessage(config.getEventNotStartedMessage());
            return;
        }
        if(!args.ensureTypes(String.class)) {
            player.sendMessage("Please use as ::answer <answer>");
            return;
        }
        boolean correctAnswer = currentQuestion.getAnswers().contains(args.getString(0).toLowerCase());
        if(correctAnswer) {
            currentQuestion = null;
            Avuna.getEventManager().submit(new TriviaCorrectEvent(player));
        } else {
            player.sendMessage(config.getWrongAnswerMessage());
        }
    }

    @Command(aliases={"trivia"}, description = "Get status of trivia")
    public void triviaStatusCommand(IPlayer player, String command, ArgumentParser args) {
        if(currentQuestion == null) {
            player.sendMessage(config.getEventNotStartedMessage());
        } else {
            player.sendMessage(currentQuestion.getQuestion());
        }
    }

    private Question getRandomQuestion() {
        return config.getQuestions().get((int) (Math.random() * config.getQuestions().size()));
    }

    @Override
    public void execute() {
        if(getTicksEllapsed() % config.getDelayInTicks() == 0) {
            currentQuestion = getRandomQuestion();
            final Map<String, String> tokens = Map.of("QUESTION", currentQuestion.getQuestion());
            Avuna.getPlayers().sendMessageToAll(TemplateEngine.replaceTokens(config.getEventStartMessage(), tokens));
        }
    }
}
