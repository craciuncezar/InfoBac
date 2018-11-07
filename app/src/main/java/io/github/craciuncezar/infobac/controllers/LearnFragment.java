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
            LessonProgress introducereProgress = viewModel.progressAvailable("Introducere");
            binding.progressBarIntroducere.setProgress(introducereProgress != null ? introducereProgress.getProgressIndex() + 1 : 0);
            String text = (int) (((float) binding.progressBarIntroducere.getProgress() / binding.progressBarIntroducere.getMax()) * 100)+"%";
            binding.progressTextViewIntroducere.setText(text);

            LessonProgress pseudocodProgress = viewModel.progressAvailable("Pseudocod");
            binding.progressBarPseudocod.setProgress(pseudocodProgress != null ? pseudocodProgress.getProgressIndex() + 1 : 0);
            text = (int) (((float) binding.progressBarPseudocod.getProgress() / binding.progressBarPseudocod.getMax()) * 100)+"%";
            binding.progressTextViewPseudocod.setText(text);
        });
    }

    public void onClickLesson(String lesson) {
        Intent intent = LessonActivity.getIntent(getContext(), lesson);
        startActivity(intent);
    }
}
