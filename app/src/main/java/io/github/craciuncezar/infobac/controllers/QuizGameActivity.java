package io.github.craciuncezar.infobac.controllers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.databinding.ActivityQuizGameBinding;
import io.github.craciuncezar.infobac.viewmodels.QuizGameViewModel;
import io.github.craciuncezar.infobac.views.CongratsDialog;
import io.github.craciuncezar.infobac.views.QuizBottomSheet;

public class QuizGameActivity extends BaseActivity implements QuizBottomSheet.QuizBottomSheetListener {
    private ActivityQuizGameBinding binding;
    private QuizGameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_game);
        viewModel = ViewModelProviders.of(this).get(QuizGameViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setHandler(this);
        setupToolBar();
    }

    public void setupToolBar() {
        setSupportActionBar(binding.toolbarQuiz);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAnswerPressed() {
        if (viewModel.answerIsCorrect()) {
            advanceQuiz();
        } else {
            if (viewModel.getQuizQuestion().getValue() != null) {
                QuizBottomSheet bottomSheet = QuizBottomSheet.getQuizBottomSheet(viewModel.getQuizQuestion().getValue().getCorrectAnswer());
                bottomSheet.show(getSupportFragmentManager(), "wrongAnswerBottomSheet");
            }
        }
    }

    private void advanceQuiz() {
        if (viewModel.isLastQuestion()) {
            CongratsDialog.showQuizDialog(QuizGameActivity.this, getLayoutInflater(), false);
        } else {
            viewModel.nextQuestion();
        }
    }

    @Override
    public void onBottomSheetButtonPressed() {
        advanceQuiz();
    }
}
