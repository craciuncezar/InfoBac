package io.github.craciuncezar.infobac.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed_subjects")
public class CompletedSubject {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    @ColumnInfo(name = "subject_name")
    private String subjectName;

    public CompletedSubject(String type, String subjectName) {
        this.type = type;
        this.subjectName = subjectName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
