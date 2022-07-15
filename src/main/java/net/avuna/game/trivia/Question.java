package net.avuna.game.trivia;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Question {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answers")
    private List<String> answers = null;
}