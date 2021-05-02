package net.avuna.game.trivia;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Question {

    @Expose
    @SerializedName("question")
    private String question;

    @Expose
    @SerializedName("answers")
    private List<String> answers = null;
}