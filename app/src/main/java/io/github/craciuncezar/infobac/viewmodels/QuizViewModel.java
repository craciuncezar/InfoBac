package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.data.entity.CompletedQuiz;
import io.github.craciuncezar.infobac.data.entity.QuizQuestion;
import io.github.craciuncezar.infobac.utils.FileUtility;

public class QuizViewModel extends AndroidViewModel {
    private String currentQuizSet;
    private MutableLiveData<QuizQuestion> quizQuestion = new MutableLiveData<>();
    private MutableLiveData<Integer> currentQuestionNumber = new MutableLiveData<>();
    private int quizSetQuestionsNumber = 10;
    private MutableLiveData<Integer> selectedAnswerIndex = new MutableLiveData<>();
    private LiveData<List<CompletedQuiz>> completedQuizes;
    private ProgressRepository progressRepository;

    public QuizViewModel(Application application) {
        super(application);
        progressRepository = ProgressRepository.getInstance(application);
        completedQuizes = progressRepository.getCompletedQuizzes();
        setCurrentQuizSet(0);
    }

    private void updateQuestion() {
        if (currentQuestionNumber.getValue() != null) {
            Gson gson = new GsonBuilder().create();
            String json = FileUtility.readFromAsset(getApplication(), "Quizuri/" + currentQuizSet + "/" + String.valueOf(currentQuestionNumber.getValue() + 1) + ".json");
            quizQuestion.setValue(gson.fromJson(json, QuizQuestion.class));
        }
    }

    public void nextQuestion() {
        if (selectedAnswerIndex.getValue() != null && selectedAnswerIndex.getValue() != -1 && currentQuestionNumber.getValue() != null) {
            if (!isLastQuestion())
                currentQuestionNumber.setValue(currentQuestionNumber.getValue() + 1);
            selectedAnswerIndex.setValue(-1);
            updateQuestion();
        }
    }

    public boolean answerIsCorrect() {
        if (selectedAnswerIndex.getValue() != null && quizQuestion.getValue() != null) {
            String selectedAnswer = quizQuestion.getValue().getChoices()[selectedAnswerIndex.getValue()];
            String correctAnswer = quizQuestion.getValue().getCorrectAnswer();
            return selectedAnswer.equals(correctAnswer);
        }
        return false;
    }

    public boolean isLastQuestion() {
        return currentQuestionNumber.getValue() != null && currentQuestionNumber.getValue().equals(quizSetQuestionsNumber - 1);
    }

    public void onChoiceSelected(int index) {
        if (selectedAnswerIndex.getValue() != null && selectedAnswerIndex.getValue() != index) {
            selectedAnswerIndex.setValue(index);
        } else {
            selectedAnswerIndex.setValue(-1);
        }
    }

    public MutableLiveData<QuizQuestion> getQuizQuestion() {
        return quizQuestion;
    }

    public MutableLiveData<Integer> getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public int getCurrentQuizSetQuestionsNumber() {
        return quizSetQuestionsNumber;
    }

    public void addQuizCompleted() {
        if (completedQuizes.getValue() != null) {
            for (CompletedQuiz completedQuiz : completedQuizes.getValue())
                if (completedQuiz.getQuizName().equals(currentQuizSet))
                    return;
        }
        progressRepository.addCompletedQuiz(new CompletedQuiz(currentQuizSet));
    }

    public void setCurrentQuizSet(int index) {
        currentQuizSet = getApplication().getResources().getStringArray(R.array.quiz_name)[index];
        currentQuestionNumber.setValue(0);
        selectedAnswerIndex.setValue(-1);
        updateQuestion();
    }

    public MutableLiveData<Integer> getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public LiveData<List<CompletedQuiz>> getCompletedQuizes() {
        return completedQuizes;
    }
}
