package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;

public class HomeFragment extends Fragment {
    @BindView(R.id.progress_exercises) ProgressBar progressBarExercises;
    @BindView(R.id.current_lesson_tv) TextView textViewCurrentLesson;
    @BindView(R.id.tv_learn) TextView textViewLearn;

    String currentLesson;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        int totalExercises = getResources().getStringArray(R.array.bac_subjects).length*2;
        int resolvedSubject = DataManager.getInstance().getCompletedSubjects().size();
        progressBarExercises.setMax(totalExercises);
        progressBarExercises.setProgress(resolvedSubject);

        initLearnNowCard();

        return view;
    }

    @OnClick(R.id.cardViewLearn)
    public void onLearnCardPressed(View view){
        Intent intent = LessonActivity.getIntent(getContext(),textViewCurrentLesson.getText().toString());
        startActivity(intent);
    }

    private void initLearnNowCard(){
        currentLesson = DataManager.getInstance().getCurrentLesson();
        textViewCurrentLesson.setText(currentLesson.equals("")? "Introducere" : currentLesson);
        textViewLearn.setText(currentLesson.equals("")? "Incepe sa inveti" : "Continua lectia");
    }

    @Override
    public void onResume() {
        super.onResume();
        initLearnNowCard();
    }
}
