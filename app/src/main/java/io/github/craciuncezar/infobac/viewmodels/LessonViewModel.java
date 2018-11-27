package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.PreferencesManager;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;

public class LessonViewModel extends AndroidViewModel {
    private LiveData<LessonProgress> lessonProgress;
    private PreferencesManager preferencesManager;
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>();
    private MutableLiveData<String> pageUrl = new MutableLiveData<>();
    private int lessonPages;
    private String lessonName;
    private ProgressRepository repository;

    public LessonViewModel(Application app) {
        super(app);
        repository = ProgressRepository.getInstance(app);
        preferencesManager = PreferencesManager.getInstance(app);
    }

    public void setLesson(String lessonName) {
        this.lessonName = lessonName;
        setNumberOfLessonPages(lessonName);
        lessonProgress = repository.getProressForLesson(lessonName);
        pageUrl.setValue(lessonName + "/" + currentPage.getValue() + ".html");
    }

    private void setNumberOfLessonPages(String lessonName) {
        List<String> lessons = Arrays.asList(getApplication().getResources().getStringArray(R.array.lesson_names));
        int[] chapterNumbers = getApplication().getResources().getIntArray(R.array.lesson_chapters);
        this.lessonPages = chapterNumbers[lessons.indexOf(lessonName)];
    }

    public void initLesson(LessonProgress progress) {
        if (progress == null) {
            setCurrentLesson(lessonName);
            currentPage.setValue(0);
            LessonProgress progressLesson = new LessonProgress(lessonName, 0);
            repository.addLessonProgress(progressLesson);
            this.lessonProgress = new MutableLiveData<>();
            ((MutableLiveData<LessonProgress>) this.lessonProgress).setValue(progressLesson);
        } else {
            currentPage.setValue(progress.getProgressIndex());
        }
    }

    public void nextLessonPage() {
        if (currentPage.getValue() != null && currentPage.getValue() < lessonPages - 1) {
            currentPage.setValue(currentPage.getValue() + 1);
            if (lessonProgress.getValue() != null && currentPage.getValue() > lessonProgress.getValue().getProgressIndex()) {
                lessonProgress.getValue().setProgressIndex(currentPage.getValue());
                repository.updateLessonProgress(lessonProgress.getValue());
            }
        }
    }

    public void previousLessonPage() {
        if (currentPage.getValue() != null && currentPage.getValue() > 0) {
            currentPage.setValue(currentPage.getValue() - 1);
        }
    }

    public boolean isEndOfLesson() {
        if (currentPage.getValue() != null && currentPage.getValue() == lessonPages - 1) {
            // Set the next lesson as current lesson if it exists
            List<String> lessons = Arrays.asList(getApplication().getResources().getStringArray(R.array.lesson_names));
            if (lessons.size() > (lessons.indexOf(lessonName) + 1))
                preferencesManager.setCurrentLesson(lessons.get(lessons.indexOf(lessonName) + 1));
            return true;
        }
        return false;
    }

    public LiveData<Integer> getCurrentPage() {
        return currentPage;
    }

    public int getLessonPages() {
        return lessonPages;
    }

    private void setCurrentLesson(String lessonName) {
        preferencesManager.setCurrentLesson(lessonName);
    }

    public LiveData<LessonProgress> getLessonProgress() {
        return lessonProgress;
    }

    public String getLessonName() {
        return lessonName;
    }
}
