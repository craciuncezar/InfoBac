package io.github.craciuncezar.infobac.controllers;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;

public class HomeFragment extends Fragment {
    @BindView(R.id.progress_lectii) ProgressBar progressBarLessons;
    @BindView(R.id.progress_exercises) ProgressBar progressBarExercises;
    @BindView(R.id.current_lesson_tv) TextView textViewCurrentLesson;
    @BindView(R.id.tv_learn) TextView textViewLearn;

    private String currentLesson;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        int totalExercises = getResources().getStringArray(R.array.bac_subjects).length*2;
        int resolvedSubject = DataManager.getInstance().getCompletedSubjects().size();
        progressBarExercises.setMax(totalExercises);
        progressBarExercises.setProgress(resolvedSubject);

        initProgressLessons();
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
        textViewLearn.setText(currentLesson.equals("")? "Incepe sa inveti" : "Continua sa inveti");
    }

    private void initProgressLessons(){
        int[] lessonsChapters = getResources().getIntArray(R.array.lesson_chapters);
        int totalLessonsChapters = 0;
        for(int i: lessonsChapters){
            totalLessonsChapters+=i;
        }

        int completedLessons = 0;
        HashMap<String,Integer> lessonProgress = DataManager.getInstance().getLessonsProgress();
        for (Object o : lessonProgress.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            completedLessons = completedLessons + (int) pair.getValue() + 1;
        }
        progressBarLessons.setMax(totalLessonsChapters);
        progressBarLessons.setProgress(completedLessons);
    }

    @Override
    public void onResume() {
        super.onResume();
        initLearnNowCard();
        initProgressLessons();
    }
}
