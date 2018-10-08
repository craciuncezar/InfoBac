package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;

public class ExerciseFragment extends Fragment {
    
    @BindView(R.id.tv_subiecte_mate) TextView subjectsMateNumber;
    @BindView(R.id.tv_subiecte_sn) TextView subjectsSnNumber;
    @BindView(R.id.tv_rezolvate_mate) TextView subjectMateCompleted;
    @BindView(R.id.tv_rezolvate_sn) TextView subjectsSnCompleted;
    @BindView(R.id.tv_prob_complete) TextView problemsCompleted;
    @BindView(R.id.tv_probleme) TextView problemsNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        ButterKnife.bind(this, view);

        computeSubjectsNumbers();
        computeProblemsNumbers();
        return  view;
    }

    @OnClick({R.id.cardViewMate})
    public void onSubjectsCardPressed(View view){
        startActivity(SubjectsActivity.getIntent(getContext(),"Mate"));
    }
    @OnClick({R.id.cardViewStiinte})
    public void onCardViewStiintePressed(View view){
        startActivity(SubjectsActivity.getIntent(getContext(),"Stiinte"));
    }

    @OnClick(R.id.cardViewProbleme)
    public void onCardViewProblemePressed(View view){
        Intent intent = new Intent(getContext(),ProblemsActivity.class);
        startActivity(intent);
    }

    public void computeSubjectsNumbers(){
        ArrayList<String> completedSubjects = DataManager.getInstance().getCompletedSubjects();
        int completedMate = 0;
        int completedSn = 0;
        for(String s: completedSubjects){
            if(s.startsWith("Subiecte/Mate"))
                completedMate++;
            else if(s.startsWith("Subiecte/Stiinte"))
                completedSn++;
        }
        subjectMateCompleted.setText(completedMate + " REZOLVATE");
        subjectsSnCompleted.setText(completedSn + " REZOLVATE");
        String bacSubjectsNumber = getResources().getStringArray(R.array.bac_subjects).length+" SUBIECTE";
        subjectsMateNumber.setText(bacSubjectsNumber);
        subjectsSnNumber.setText(bacSubjectsNumber);
    }

    public void computeProblemsNumbers(){
        int completedProblems = DataManager.getInstance().getCompletedProblems().size();
        problemsCompleted.setText(completedProblems + " REZOLVATE");
        String problemsCountString = getResources().getStringArray(R.array.problems).length+" PROBLEME";
        problemsNumber.setText(problemsCountString);
    }

    @Override
    public void onResume() {
        super.onResume();
        computeSubjectsNumbers();
        computeProblemsNumbers();
    }
}
