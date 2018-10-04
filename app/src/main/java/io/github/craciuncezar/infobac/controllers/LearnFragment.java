package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;

public class LearnFragment extends Fragment {

    @BindView(R.id.progressBarIntroducere) ProgressBar progressBarIntroducere;
    @BindView(R.id.tv_progress_introducere) TextView progressTextViewIntroducere;
    private HashMap<String,Integer> lessonsProgress;
    private int[] subjectsChapters;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

        ButterKnife.bind(this,rootView);
        subjectsChapters = getResources().getIntArray(R.array.lesson_chapters);
        lessonsProgress = DataManager.getInstance().getLessonsProgress();

        if(lessonsProgress.containsKey("Introducere")){
            int progress = lessonsProgress.get("Introducere");
            progressBarIntroducere.setMax(subjectsChapters[0]);
            progressBarIntroducere.setProgress(progress+1);
            progressTextViewIntroducere.setText((int)((float)progressBarIntroducere.getProgress()/progressBarIntroducere.getMax()*100)+"%");
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lessonsProgress.containsKey("Introducere")){
            int progress = lessonsProgress.get("Introducere");
            progressBarIntroducere.setMax(subjectsChapters[0]);
            progressBarIntroducere.setProgress(progress+1);
            progressTextViewIntroducere.setText((int)((float)progressBarIntroducere.getProgress()/progressBarIntroducere.getMax()*100)+"%");
        }
    }

    @OnClick(R.id.lesson_introduction)
    public void onClickLesson(View view){
        TextView textView = view.findViewById(R.id.lesson_name);
        Intent intent = LessonActivity.getIntent(getContext(), textView.getText().toString());
        startActivity(intent);
    }


}
