package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;

public class SubjectsViewModel extends AndroidViewModel {
    private String baseFilePath = "Subiecte/";
    private String subjectType;
    private String currentSubject;
    private MutableLiveData<String> currentFilePath = new MutableLiveData<>();
    private MutableLiveData<Boolean> subjectIsCompleted = new MutableLiveData<>();
    private LiveData<List<CompletedSubject>> completedSubjectsList;
    private CompletedSubject completedSubject;
    private ProgressRepository repository;

    public SubjectsViewModel(@NonNull Application application) {
        super(application);
        repository = ProgressRepository.getInstance(application);
    }

    public void setSubjectType(String subjectType) {
        if(this.subjectType == null) {
            this.subjectType = subjectType;
            this.baseFilePath += subjectType + "/";
            completedSubjectsList = repository.getCompletedSubjects(subjectType);
        }
    }

    public void setCurrentSubject(String currentSubject) {
        this.currentSubject = currentSubject;
        currentFilePath.setValue(baseFilePath + currentSubject);
        subjectIsCompleted.setValue(checkSubjectIsCompleted());
    }

    private boolean checkSubjectIsCompleted() {
        if (completedSubjectsList.getValue() != null) {
            for (CompletedSubject completedSubject : completedSubjectsList.getValue()) {
                if (completedSubject.getSubjectName().equals(this.currentSubject)) {
                    this.completedSubject = completedSubject;
                    return true;
                }
            }
        }
        return false;
    }

    public void changeSubjectIsCompleted() {
        if (checkSubjectIsCompleted())
            repository.deleteCompletedSubject(completedSubject);
        else
            repository.addCompletedSubject(new CompletedSubject(subjectType, currentSubject));
        if (subjectIsCompleted.getValue() != null) {
            this.subjectIsCompleted.setValue(!subjectIsCompleted.getValue());
        }
    }

    public MutableLiveData<Boolean> getSubjectIsCompleted() {
        return subjectIsCompleted;
    }

    public MutableLiveData<String> getCurrentFilePath() {
        return currentFilePath;
    }

    public LiveData<List<CompletedSubject>> getCompletedSubjectsList() {
        return completedSubjectsList;
    }
}
