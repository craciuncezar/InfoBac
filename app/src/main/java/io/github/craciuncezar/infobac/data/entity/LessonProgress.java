package io.github.craciuncezar.infobac.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lesson_progress")
public class LessonProgress {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "lesson_name")
    private String lessonName;
    @ColumnInfo(name = "progress_index")
    private int progressIndex;

    public LessonProgress(String lessonName, int progressIndex) {
        this.lessonName = lessonName;
        this.progressIndex = progressIndex;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProgressIndex(int progressIndex){
        this.progressIndex = progressIndex;
    }

    public long getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public int getProgressIndex() {
        return progressIndex;
    }
}
