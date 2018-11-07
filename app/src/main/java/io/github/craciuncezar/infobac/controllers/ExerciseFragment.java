package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.databinding.FragmentExerciseBinding;
import io.github.craciuncezar.infobac.viewmodels.ExerciseViewModel;

public class ExerciseFragment extends Fragment {
    private FragmentExerciseBinding binding;
    private ExerciseViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        binding.setFragment(this);
        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        initExerciseNumbers();
        observeUI();
        return binding.getRoot();
    }

    private void observeUI() {
        viewModel.getNumberOfCompletedProblems().observe(this, (number) -> {
            String text = number + " REZOLVATE";
            binding.tvProbComplete.setText(text);
        });

        viewModel.getNumberOfCompletedQuizzes().observe(this, (number) -> {
            String text = number + " COMPLETATE";
            binding.tvQuizComplete.setText(text);
        });

        viewModel.getNumberOfCompletedSubjectMate().observe(this, (number) -> {
            String text = number + " REZOLVATE";
            binding.tvRezolvateMate.setText(text);
        });

        viewModel.getNumberOfCompletedSubjectStiinte().observe(this, (number) -> {
            String text = number + " REZOLVATE";
            binding.tvRezolvateSn.setText(text);
        });
    }

    public void onCardMatePressed() {
        startActivity(SubjectsActivity.getIntent(getContext(), "Mate"));
    }

    public void onCardStiintePressed() {
        startActivity(SubjectsActivity.getIntent(getContext(), "Stiinte"));
    }

    public void onCardProblemePressed() {
        Intent intent = new Intent(getContext(), ProblemsActivity.class);
        startActivity(intent);
    }

    public void onCardQuizPressed() {
        Intent intent = new Intent(getContext(), QuizActivity.class);
        startActivity(intent);
    }

    private void initExerciseNumbers() {
        String bacSubjectsNumber = getResources().getStringArray(R.array.bac_subjects).length + " SUBIECTE";
        binding.tvSubiecteMate.setText(bacSubjectsNumber);
        binding.tvSubiecteSn.setText(bacSubjectsNumber);
        String problemsCountString = getResources().getStringArray(R.array.problems).length + " PROBLEME";
        binding.tvProbleme.setText(problemsCountString);
        String numberOfQuizes = getResources().getStringArray(R.array.quiz_name).length + " QUIZURI";
        binding.tvQuiz.setText(numberOfQuizes);
    }
}
