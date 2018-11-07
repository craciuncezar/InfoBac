package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.PreferencesManager;
import io.github.craciuncezar.infobac.databinding.FragmentHomeBinding;
import io.github.craciuncezar.infobac.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        initLearnNowCard();
        initProgressMax();

        return binding.getRoot();
    }

    public void learnCardPressed() {
        Intent intent = LessonActivity.getIntent(getContext(), binding.textViewCurrentLesson.getText().toString());
        startActivity(intent);
    }

    public void quizGamePressed() {
        Intent intent = new Intent(getContext(), QuizGameActivity.class);
        startActivity(intent);
    }

    public void feedbackPressed() {
        final String appPackageName = Objects.requireNonNull(getContext(), "Unexpected context null").getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void initLearnNowCard() {
        String currentLesson = PreferencesManager.getInstance(getActivity()).getCurrentLesson();
        binding.textViewCurrentLesson.setText(currentLesson);
        binding.textViewLearn.setText(currentLesson.equals("Introducere") ? "Incepe sa inveti" : "Continua sa inveti");
    }

    private void initProgressMax() {
        int totalExercises = getResources().getStringArray(R.array.bac_subjects).length * 2 + getResources().getStringArray(R.array.problems).length + getResources().getStringArray(R.array.quiz_name).length;
        binding.progressBarExercises.setMax(totalExercises);

        int[] lessonsChapters = getResources().getIntArray(R.array.lesson_chapters);
        int totalLessonsChapters = 0;
        for (int i : lessonsChapters) {
            totalLessonsChapters += i-1;
        }
        binding.progressBarLessons.setMax(totalLessonsChapters);
    }

    @Override
    public void onResume() {
        super.onResume();
        initLearnNowCard();
    }
}
