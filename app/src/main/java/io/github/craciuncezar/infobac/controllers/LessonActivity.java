package io.github.craciuncezar.infobac.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.databinding.ActivityLessonBinding;
import io.github.craciuncezar.infobac.viewmodels.LessonViewModel;
import io.github.craciuncezar.infobac.views.CongratsDialog;

public class LessonActivity extends BaseActivity {
    private static final String NAME_FOR_LESSON = "lesson";
    private LessonViewModel viewModel;
    private ActivityLessonBinding binding;
    private Handler handler = new Handler();

    public static Intent getIntent(Context context, String lesson) {
        Intent intent = new Intent(context, LessonActivity.class);
        intent.putExtra(NAME_FOR_LESSON, lesson);
        return intent;
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lesson);
        viewModel = ViewModelProviders.of(this).get(LessonViewModel.class);
        viewModel.setLesson(getIntent().getStringExtra(NAME_FOR_LESSON));
        viewModel.getLessonProgress().observe(this, (progress) -> viewModel.initLesson(progress));
        binding.setActivity(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.lessonWebView.getSettings().setJavaScriptEnabled(true);

        initToolBar();
        observeUI();

        binding.progressBarLesson.setMax((viewModel.getLessonPages() - 1) * 100);
    }

    private void observeUI() {
        viewModel.getCurrentPage().observe(this, (page) -> {
            binding.progressLesson.setText(String.format(Locale.ENGLISH, "%d/%d", page + 1, viewModel.getLessonPages()));
            binding.lessonWebView.setVisibility(View.GONE);
            binding.lessonWebView.scrollTo(0, 0);
            binding.lessonWebView.loadUrl("file:///android_asset/Teorie/" + viewModel.getLessonName() + "/" + page + ".html");
            handler.removeCallbacksAndMessages(null);
            binding.lessonWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    binding.lessonWebView.evaluateJavascript("setCurrentStyle('" + getCurrentTheme() + "')", (result) -> handler.postDelayed(() -> binding.lessonWebView.setVisibility(View.VISIBLE), 300));
                }
            });
        });
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

    public void nextPage() {
        if (viewModel.isEndOfLesson())
            CongratsDialog.showLessonDialog(this, getLayoutInflater());
        else {
            viewModel.nextLessonPage();
        }
    }

    public void previousPage() {
        viewModel.previousLessonPage();
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbarLesson);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
    }
}
