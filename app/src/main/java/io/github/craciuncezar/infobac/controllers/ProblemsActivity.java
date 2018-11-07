package io.github.craciuncezar.infobac.controllers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.adapters.CustomSpinnerAdapter;
import io.github.craciuncezar.infobac.databinding.ActivityProblemsBinding;
import io.github.craciuncezar.infobac.viewmodels.ProblemsViewModel;
import io.github.craciuncezar.infobac.views.RoundedSnackBar;

@SuppressLint("SetJavaScriptEnabled")
public class ProblemsActivity extends BaseActivity {
    private ProblemsViewModel viewModel;
    private ActivityProblemsBinding binding;

    @BindingAdapter("problemText")
    public static void setProblemText(TextView view, String problemText) {
        view.setText(Html.fromHtml(problemText));
    }

    @BindingAdapter("solutionUrl")
    public static void setSolutionUrl(WebView webView, String currentProblem) {
        webView.loadUrl("file:///android_asset/CodeSnippets/Probleme/" + currentProblem + "/code.html");
    }

    public void setSolutionButtonText(Boolean solutionIsVisible) {
        if (solutionIsVisible)
            binding.btnShowSolution.setText(getString(R.string.Hide_solution));
        else
            binding.btnShowSolution.setText(getString(R.string.Show_solution));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_problems);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(ProblemsViewModel.class);
        binding.setViewModel(viewModel);
        binding.webViewSolution.getSettings().setJavaScriptEnabled(true);

        setupToolbar();
        setupSpinner();
        viewModel.getSolutionIsVisible().observe(this, this::setSolutionButtonText);
        viewModel.getCompletedProblemsList().observe(this,((CustomSpinnerAdapter)binding.subjectSpinner.getAdapter())::updateCompletedSubjects);
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

    private void setupToolbar() {
        setSupportActionBar(binding.toolbarSubjects);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Probleme");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    private void setupSpinner() {
        ArrayAdapter<String> spinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item, R.id.itmTitle, getResources().getStringArray(R.array.problems), viewModel.getCompletedProblemsList().getValue());
        binding.subjectSpinner.setAdapter(spinnerAdapter);
    }

    public void onProblemItemSelected() {
        viewModel.setProblemName(binding.subjectSpinner.getSelectedItem().toString());
    }

    public void showSolutionPressed() {
        if (viewModel.getSolutionIsVisible().getValue() != null) {
            viewModel.setSolutionIsVisible(!viewModel.getSolutionIsVisible().getValue());
        }
    }

    public void onCompletePressed() {
        viewModel.changeProblemIsCompleted();
        if (viewModel.getProblemIsCompleted().getValue() != null && viewModel.getProblemIsCompleted().getValue()) {
            RoundedSnackBar.showRoundedSnackBar(this, findViewById(R.id.coordinator_layout_subjects), "Felicitari, problema rezolvata!");
        }
    }
}
