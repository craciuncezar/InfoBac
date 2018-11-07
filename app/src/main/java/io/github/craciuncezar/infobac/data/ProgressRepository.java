package io.github.craciuncezar.infobac.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.github.craciuncezar.infobac.data.dao.ProgressDao;
import io.github.craciuncezar.infobac.data.db.ProgressDB;
import io.github.craciuncezar.infobac.data.entity.CompletedQuiz;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;
import io.github.craciuncezar.infobac.utils.AppExecutors;

public class ProgressRepository {
    private static ProgressRepository instance;
    private ProgressDao progressDao;

    public static synchronized ProgressRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ProgressRepository();
            instance.progressDao = ProgressDB.getInstance(application).progressDao();
        }
        return instance;
    }

    // Lessons Interface
    public LiveData<Integer> getTotalLessonProgress() {
        return progressDao.getTotalLessonProgress();
    }

    public LiveData<LessonProgress> getProressForLesson(String lessonName){
        return progressDao.getProgressForLesson(lessonName);
    }

    public LiveData<List<LessonProgress>> getLessonProgress(){
        return progressDao.getAllLessonProgress();
    }

    public void updateLessonProgress(LessonProgress lessonProgress) {
        AppExecutors.runOnIoThread(() -> progressDao.updateLessonProgress(lessonProgress));
    }

    public void addLessonProgress(LessonProgress lessonProgress){
        AppExecutors.runOnIoThread(()-> lessonProgress.setId(progressDao.addLessonProgress(lessonProgress)));
    }



    // Subjects Interface
    public LiveData<List<CompletedSubject>> getCompletedSubjects(String type) {
        return progressDao.getCompletedSubject(type);
    }

    public void deleteCompletedSubject(CompletedSubject subject) {
        AppExecutors.runOnIoThread(() -> progressDao.deleteCompletedSubject(subject));
    }

    public void addCompletedSubject(CompletedSubject completedSubject) {
        AppExecutors.runOnIoThread(() -> completedSubject.setId(progressDao.addCompletedSubject(completedSubject)));
    }

    public LiveData<Integer> getNumberOfCompletedSubjects(String type) {
        return progressDao.getNumberOfCompletedSubjects(type);
    }

    // Quizzes Interface
    public LiveData<List<CompletedQuiz>> getCompletedQuizzes() {
        return progressDao.getAllCompletedQuizzes();
    }

    public void addCompletedQuiz(CompletedQuiz completedQuiz) {
        AppExecutors.runOnIoThread(() -> completedQuiz.setId(progressDao.addCompletedQuiz(completedQuiz)));
    }

    public LiveData<Integer> getNumberOfCompletedQuizzes() {
        return progressDao.getNumberOfCompletedSubjects();
    }

    public LiveData<Integer> getTotalNumberOfCompletedExercises() {
        return progressDao.getTotalNumberOfCompletedExercises();
    }

    public void deleteProgress(){
        AppExecutors.runOnIoThread(()->{
            progressDao.deleteLessonProgress();
            progressDao.deleteQuizProgress();
            progressDao.deleteSubjectsProgress();
        });
    }
}
