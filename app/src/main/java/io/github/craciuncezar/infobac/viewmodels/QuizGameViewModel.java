package io.github.craciuncezar.infobac.viewmodels;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.entity.QuizQuestion;
import io.github.craciuncezar.infobac.utils.FileUtility;

public class QuizGameViewModel extends AndroidViewModel {
    private MutableLiveData<QuizQuestion> quizQuestion = new MutableLiveData<>();
    private MutableLiveData<Integer> currentQuestionNumber = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedAnswerIndex = new MutableLiveData<>();

    private List<Integer> randomQuizIndexes = new ArrayList<>();
    private String[] quizNames;

    public QuizGameViewModel(@NonNull Application application) {
        super(application);
        currentQuestionNumber.setValue(0);
        selectedAnswerIndex.setValue(-1);
        quizNames = getApplication().getResources().getStringArray(R.array.quiz_name);
        generateRandomIndexes();
        getQuestion();
    }

    private void generateRandomIndexes() {
        for (int i = 1; i < 11; i++)
            randomQuizIndexes.add(i);
        Collections.shuffle(randomQuizIndexes);
    }

    private void getQuestion() {
        int questionIndex = randomQuizIndexes.get(currentQuestionNumber.getValue());
        int quizIndex = new Random().nextInt(quizNames.length);
        updateQuestion(quizNames[quizIndex], questionIndex);
    }

    private void updateQuestion(String quizSet, int index) {
        Gson gson = new GsonBuilder().create();
        String json = FileUtility.readFromAsset(getApplication(), "Quizuri/" + quizSet + "/" + String.valueOf(index) + ".json");
        quizQuestion.setValue(gson.fromJson(json, QuizQuestion.class));
    }

    public void nextQuestion() {
        if (selectedAnswerIndex.getValue() != -1) {
            if (!isLastQuestion())
                currentQuestionNumber.setValue(currentQuestionNumber.getValue() + 1);
            selectedAnswerIndex.setValue(-1);
            getQuestion();
        }
    }

    public boolean answerIsCorrect() {
        String selectedAnswer = quizQuestion.getValue().getChoices()[selectedAnswerIndex.getValue()];
        String correctAnswer = quizQuestion.getValue().getCorrectAnswer();
        return selectedAnswer.equals(correctAnswer);
    }

    public void onChoiceSelected(int index) {
        if (selectedAnswerIndex.getValue() != index) {
            selectedAnswerIndex.setValue(index);
        } else {
            selectedAnswerIndex.setValue(-1);
        }
    }

    public boolean isLastQuestion() {
        return currentQuestionNumber.getValue().equals(9);
    }

    public MutableLiveData<QuizQuestion> getQuizQuestion() {
        return quizQuestion;
    }

    public MutableLiveData<Integer> getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public MutableLiveData<Integer> getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }
}
