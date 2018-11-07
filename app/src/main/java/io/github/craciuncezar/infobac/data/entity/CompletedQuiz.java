package io.github.craciuncezar.infobac.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getQuizName() {
        return quizName;
    }
}
