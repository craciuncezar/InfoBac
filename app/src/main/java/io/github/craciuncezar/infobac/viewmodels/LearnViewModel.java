package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;

public class LearnViewModel extends AndroidViewModel {
    private LiveData<List<LessonProgress>> lessonProgressList;
    private int[] subjectsChapters;
    private String[] subjectsNames;

    public LearnViewModel(Application application) {
        super(application);
        ProgressRepository repository = ProgressRepository.getInstance(application);
        lessonProgressList = repository.getLessonProgress();
        subjectsChapters = application.getResources().getIntArray(R.array.lesson_chapters);
        subjectsNames = application.getResources().getStringArray(R.array.lesson_names);
    }

    public LessonProgress progressAvailable(String subject) {
        if (lessonProgressList.getValue() != null) {
            for (LessonProgress lessonProgress : lessonProgressList.getValue()) {
                if (lessonProgress.getLessonName().equals(subject))
                    return lessonProgress;
            }
        }
        return null;
    }

    public LiveData<List<LessonProgress>> getLessonProgressList() {
        return lessonProgressList;
    }

    public int[] getSubjectsChapters() {
        return subjectsChapters;
    }

    public String getSubjectName(int index) {
        return subjectsNames[index];
    }

    public int getNumbersOfChapters(int index) {
        return subjectsChapters[index];
    }
}
