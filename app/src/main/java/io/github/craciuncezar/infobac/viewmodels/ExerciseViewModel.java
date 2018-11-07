package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.craciuncezar.infobac.data.ProgressRepository;

public class ExerciseViewModel extends AndroidViewModel {
    private LiveData<Integer> numberOfCompletedQuizzes;
    private LiveData<Integer> numberOfCompletedProblems;
    private LiveData<Integer> numberOfCompletedSubjectMate;
    private LiveData<Integer> numberOfCompletedSubjectStiinte;

    public ExerciseViewModel(Application application) {
        super(application);
        ProgressRepository repository = ProgressRepository.getInstance(application);
        numberOfCompletedQuizzes = repository.getNumberOfCompletedQuizzes();
        numberOfCompletedProblems = repository.getNumberOfCompletedSubjects("Probleme");
        numberOfCompletedSubjectMate = repository.getNumberOfCompletedSubjects("Mate");
        numberOfCompletedSubjectStiinte = repository.getNumberOfCompletedSubjects("Stiinte");
    }

    public LiveData<Integer> getNumberOfCompletedQuizzes() {
        return numberOfCompletedQuizzes;
    }

    public LiveData<Integer> getNumberOfCompletedProblems() {
        return numberOfCompletedProblems;
    }

    public LiveData<Integer> getNumberOfCompletedSubjectMate() {
        return numberOfCompletedSubjectMate;
    }

    public LiveData<Integer> getNumberOfCompletedSubjectStiinte() {
        return numberOfCompletedSubjectStiinte;
    }
}
