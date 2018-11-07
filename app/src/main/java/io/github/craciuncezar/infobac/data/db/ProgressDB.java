package io.github.craciuncezar.infobac.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.craciuncezar.infobac.data.dao.ProgressDao;
import io.github.craciuncezar.infobac.data.entity.CompletedQuiz;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;

@Database(entities = {CompletedSubject.class, CompletedQuiz.class, LessonProgress.class}, version = 1)
public abstract class ProgressDB extends RoomDatabase {
    private static ProgressDB instance;

    public static synchronized ProgressDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ProgressDB.class, "progress_db")
                    .build();
        }
        return instance;
    }

    public abstract ProgressDao progressDao();

}
