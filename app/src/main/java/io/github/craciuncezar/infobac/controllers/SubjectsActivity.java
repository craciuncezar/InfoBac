package io.github.craciuncezar.infobac.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.adapters.CustomSpinnerAdapter;
import io.github.craciuncezar.infobac.adapters.SubjectsFragmentAdapter;
import io.github.craciuncezar.infobac.databinding.ActivitySubjectsBinding;
import io.github.craciuncezar.infobac.viewmodels.SubjectsViewModel;
import io.github.craciuncezar.infobac.views.RoundedSnackBar;

public class SubjectsActivity extends BaseActivity {
    private static final String PROFIL_KEY = "profil_subiecte";
    private ActivitySubjectsBinding binding;
    private SubjectsViewModel viewModel;

    public static Intent getIntent(Context context, String profil) {
        Intent intent = new Intent(context, SubjectsActivity.class);
        intent.putExtra(PROFIL_KEY, profil);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subjects);
        binding.setActivity(this);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(SubjectsViewModel.class);
        viewModel.setSubjectType(getIntent().getStringExtra(PROFIL_KEY));
        binding.setViewModel(viewModel);


        initToolBar();
        initTabLayout();
        initSpinner();
        viewModel.getCompletedSubjectsList().observe(this, ((CustomSpinnerAdapter) binding.subjectSpinner.getAdapter())::updateCompletedSubjects);
    }

    private void initToolBar() {
        setSupportActionBar(binding.toolbarSubjects);
        setTitle("Subiecte Bac");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    private void initTabLayout() {
        SubjectsFragmentAdapter subjectsFragmentAdapter = new SubjectsFragmentAdapter(getSupportFragmentManager());
        PdfSubjectFragment subjectFragment = new PdfSubjectFragment();
        subjectFragment.setIsBarem(false);
        PdfSubjectFragment baremFragment = new PdfSubjectFragment();
        baremFragment.setIsBarem(true);
        subjectsFragmentAdapter.addFragment(subjectFragment, "Subiect");
        subjectsFragmentAdapter.addFragment(baremFragment, "Barem");
        binding.subjectFragmentContainer.setAdapter(subjectsFragmentAdapter);
        binding.tabLayoutSubjects.setupWithViewPager(binding.subjectFragmentContainer);
    }

    private void initSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item, R.id.itmTitle, getResources().getStringArray(R.array.bac_subjects), viewModel.getCompletedSubjectsList().getValue());
        binding.subjectSpinner.setAdapter(stringArrayAdapter);
    }

    public void onSubjectItemSelected() {
        viewModel.setCurrentSubject(binding.subjectSpinner.getSelectedItem().toString());
    }

    public void onFabClick() {
        viewModel.changeSubjectIsCompleted();
        if (viewModel.getSubjectIsCompleted().getValue() != null && viewModel.getSubjectIsCompleted().getValue()) {
            RoundedSnackBar.showRoundedSnackBar(this, findViewById(R.id.coordinator_layout_subjects), getString(R.string.congrats_subject_complete));
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
}
