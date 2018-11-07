package io.github.craciuncezar.infobac.data.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.github.craciuncezar.infobac.data.entity.CompletedQuiz;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;

@Dao
public interface ProgressDao {

    @Query("SELECT * FROM lesson_progress")
    LiveData<List<LessonProgress>> getAllLessonProgress();

    @Query("SELECT * FROM lesson_progress WHERE lesson_name = :lessonName")
    LiveData<LessonProgress> getProgressForLesson(String lessonName);

    @Insert
    long addLessonProgress(LessonProgress lessonProgress);

    @Update
    void updateLessonProgress(LessonProgress lessonProgress);

    @Query("SELECT SUM(progress_index) FROM lesson_progress")
    LiveData<Integer> getTotalLessonProgress();

    // SUBJECTS
    @Insert
    long addCompletedSubject(CompletedSubject completedSubject);

    @Delete
    void deleteCompletedSubject(CompletedSubject subject);

    @Query("SELECT * FROM completed_subjects WHERE type = :type")
    LiveData<List<CompletedSubject>> getCompletedSubject(String type);

    @Query("SELECT COUNT(*) FROM completed_subjects WHERE type = :type")
    LiveData<Integer> getNumberOfCompletedSubjects(String type);

    // QUIZZES
    @Insert
    long addCompletedQuiz(CompletedQuiz completedQuiz);

    @Query("SELECT * FROM completed_quizzes")
    LiveData<List<CompletedQuiz>> getAllCompletedQuizzes();

    @Query("SELECT COUNT(*) FROM completed_quizzes")
    LiveData<Integer> getNumberOfCompletedSubjects();

    @Query("SELECT (SELECT COUNT(*) FROM completed_quizzes) + (SELECT COUNT(*) FROM completed_subjects)")
    LiveData<Integer> getTotalNumberOfCompletedExercises();

    @Query("DELETE FROM lesson_progress")
    void deleteLessonProgress();
    @Query("DELETE FROM completed_quizzes")
    void deleteQuizProgress();
    @Query("DELETE FROM completed_subjects")
    void deleteSubjectsProgress();
}
