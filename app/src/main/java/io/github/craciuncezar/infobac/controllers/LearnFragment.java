package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.entity.LessonProgress;
import io.github.craciuncezar.infobac.databinding.FragmentLearnBinding;
import io.github.craciuncezar.infobac.viewmodels.LearnViewModel;

public class LearnFragment extends Fragment {
    private FragmentLearnBinding binding;
    private LearnViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learn, container, false);
        viewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        binding.setFragment(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        observeUi();
        return binding.getRoot();
    }

    private void observeUi() {
        viewModel.getLessonProgressList().observe(this, (lessonProgressList) -> {
            updateProgress("Introducere", binding.progressBarIntroducere, binding.progressTextViewIntroducere);
            updateProgress("Pseudocod", binding.progressBarPseudocod, binding.progressTextViewPseudocod);
            updateProgress("Bazele C++", binding.progressBarCpp, binding.progressTextViewCpp);
            updateProgress("Date complexe", binding.progressBarDate, binding.progressTextViewDate);
        });
    }

    private void updateProgress(String subject, ProgressBar progressBar, TextView textView) {
        LessonProgress progress = viewModel.progressAvailable(subject);
        progressBar.setProgress(progress != null ? progress.getProgressIndex() + 1 : 0);
        String text = (int) (((float) progressBar.getProgress() / progressBar.getMax()) * 100) + "%";
        textView.setText(text);
    }

    public void onClickLesson(String lesson) {
        Intent intent = LessonActivity.getIntent(getContext(), lesson);
        startActivity(intent);
    }
}
