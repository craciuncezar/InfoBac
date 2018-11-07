package io.github.craciuncezar.infobac.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;
import io.github.craciuncezar.infobac.utils.FileUtility;

public class ProblemsViewModel extends AndroidViewModel {
    private MutableLiveData<String> problemName = new MutableLiveData<>();
    private MutableLiveData<String> problemText = new MutableLiveData<>();
    private MutableLiveData<Boolean> problemIsCompleted = new MutableLiveData<>();
    private MutableLiveData<Boolean> solutionIsVisible = new MutableLiveData<>();
    private LiveData<List<CompletedSubject>> completedProblemsList;
    private ProgressRepository progressRepository;
    private CompletedSubject completedProblem;

    public ProblemsViewModel(Application application) {
        super(application);
        progressRepository = ProgressRepository.getInstance(application);
        completedProblemsList = progressRepository.getCompletedSubjects("Probleme");
        problemName.setValue(application.getResources().getStringArray(R.array.problems)[0]);
        setProblemName(problemName.getValue());
    }

    private void getProblemText(String problem) {
        problemText.setValue(FileUtility.readFromAsset(getApplication(), "CodeSnippets/Probleme/" + problem + "/enunt.txt"));
    }

    public void changeProblemIsCompleted() {
        if (checkProblemCompleted())
            progressRepository.deleteCompletedSubject(completedProblem);
        else
            progressRepository.addCompletedSubject(new CompletedSubject("Probleme", problemName.getValue()));
        if (problemIsCompleted.getValue() != null) {
            this.problemIsCompleted.setValue(!problemIsCompleted.getValue());
        }
    }

    private boolean checkProblemCompleted() {
        if (completedProblemsList.getValue() != null && problemName.getValue() != null) {
            for (CompletedSubject completedProblem : this.completedProblemsList.getValue()) {
                if (completedProblem.getSubjectName().equals(problemName.getValue())) {
                    this.completedProblem = completedProblem;
                    return true;
                }
            }
        }
        return false;
    }

    public MutableLiveData<String> getProblemText() {
        return problemText;
    }

    public MutableLiveData<Boolean> getProblemIsCompleted() {
        return problemIsCompleted;
    }

    public MutableLiveData<Boolean> getSolutionIsVisible() {
        return solutionIsVisible;
    }

    public void setSolutionIsVisible(boolean solutionIsVisible) {
        this.solutionIsVisible.setValue(solutionIsVisible);
    }

    public MutableLiveData<String> getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName.setValue(problemName);
        getProblemText(problemName);
        solutionIsVisible.setValue(false);
        problemIsCompleted.setValue(checkProblemCompleted());
    }

    public LiveData<List<CompletedSubject>> getCompletedProblemsList() {
        return completedProblemsList;
    }
}
