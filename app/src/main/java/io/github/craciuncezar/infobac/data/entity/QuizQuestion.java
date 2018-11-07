package io.github.craciuncezar.infobac.data.entity;

import androidx.databinding.BaseObservable;

public class QuizQuestion extends BaseObservable {
    private String question;
    private String[] choices;
    private String correctAnswer;

    public QuizQuestion(String question, String[] choices, String correctAnswer) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
