package io.github.craciuncezar.infobac.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed_quizzes")
public class CompletedQuiz {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "quiz_name")
    private String quizName;

    public CompletedQuiz(String quizName) {
        this.quizName = quizName;
    }

    @Ignore
    public CompletedQuiz(long id, String quizName) {
        this.id = id;
        this.quizName = quizName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuizName() {
        return quizName;
    }
}
