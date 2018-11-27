package io.github.craciuncezar.infobac.controllers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.adapters.QuizSetAdapter;
import io.github.craciuncezar.infobac.databinding.ActivityQuizBinding;
import io.github.craciuncezar.infobac.viewmodels.QuizViewModel;
import io.github.craciuncezar.infobac.views.CongratsDialog;
import io.github.craciuncezar.infobac.views.QuizBottomSheet;

public class QuizActivity extends BaseActivity implements QuizBottomSheet.QuizBottomSheetListener, QuizSetAdapter.ItemClickListener {
    private ActivityQuizBinding binding;
    private QuizViewModel quizViewModel;
    private AlertDialog dialogQuizSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);
        binding.setHandler(this);
        changeQuizSetDialog();
        setupToolBar();
    }

    public void setupToolBar() {
        setSupportActionBar(binding.toolbarQuiz);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
    }

    public void onAnswerPressed() {
        if (quizViewModel.answerIsCorrect()) {
            advanceQuiz();
        } else {
            displayBottomSheet();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.quiz_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuShowDialogSpinner:
                changeQuizSetDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("InflateParams")
    public void changeQuizSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_quiz_set, null);
        builder.setView(view);
        dialogQuizSet = builder.create();
        if (dialogQuizSet.getWindow() != null)
            dialogQuizSet.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_margin));
        RecyclerView recyclerView = view.findViewById(R.id.quiz_set_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuizSetAdapter adapter = new QuizSetAdapter(QuizActivity.this, getResources().getStringArray(R.array.quiz_name), quizViewModel.getCompletedQuizes().getValue());
        quizViewModel.getCompletedQuizes().observe(this, adapter::updateCompletedQuizzes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        dialogQuizSet.show();
    }

    private void displayBottomSheet() {
        QuizBottomSheet quizBottomSheet = new QuizBottomSheet();
        Bundle bundle = new Bundle();
        if (quizViewModel.getQuizQuestion().getValue() != null)
            bundle.putString("correctAnswer", quizViewModel.getQuizQuestion().getValue().getCorrectAnswer());
        quizBottomSheet.setArguments(bundle);
        quizBottomSheet.show(getSupportFragmentManager(), "wrongAnswerBottomSheet");
    }

    @Override
    public void onBottomSheetButtonPressed() {
        advanceQuiz();
    }

    @Override
    public void onItemClick(View view, int position) {
        quizViewModel.setCurrentQuizSet(position);
        dialogQuizSet.dismiss();
    }

    private void advanceQuiz() {
        if (quizViewModel.isLastQuestion()) {
            quizViewModel.addQuizCompleted();
            CongratsDialog.showQuizDialog(QuizActivity.this, getLayoutInflater(), true);
        } else {
            quizViewModel.nextQuestion();
        }
    }
}
