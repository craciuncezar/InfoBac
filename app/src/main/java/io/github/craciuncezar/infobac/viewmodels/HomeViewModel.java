package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.craciuncezar.infobac.data.ProgressRepository;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<Integer> numberOfCompletedExercises;
    private LiveData<Integer> totalLessonProgress;

    public HomeViewModel(Application app) {
        super(app);
        ProgressRepository repository = ProgressRepository.getInstance(app);
        numberOfCompletedExercises = repository.getTotalNumberOfCompletedExercises();
        totalLessonProgress = repository.getTotalLessonProgress();
    }

    public LiveData<Integer> getNumberOfCompletedExercises() {
        return numberOfCompletedExercises;
    }

    public LiveData<Integer> getTotalLessonProgress() {
        return totalLessonProgress;
    }
}
