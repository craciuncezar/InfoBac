package io.github.craciuncezar.infobac.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.utils.FileUtility;
import io.github.craciuncezar.infobac.views.RoundedSnackBar;

public class ProblemsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_subjects) Toolbar toolbar;
    @BindView(R.id.subject_spinner) AppCompatSpinner spinner;
    @BindView(R.id.fabSubjects) FloatingActionButton fab;
    @BindView(R.id.problemText) TextView problemText;
    @BindView(R.id.webView_solution) WebView webView;

    ArrayList<String> problemeRezolvate;
    String currentProblem;
    Boolean problemIsCompleted;
    Boolean solutionIsVisible = false;

    public final static String PATH_TO_PROBLEMS = "Probleme/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
        ButterKnife.bind(this);

        problemeRezolvate = DataManager.getInstance().getCompletedProblems();
        currentProblem = getResources().getStringArray(R.array.problems)[0];
        webView.getSettings().setJavaScriptEnabled(true);

        setupToolbar();
        setupSpinner();
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Probleme");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void setupSpinner(){
        ArrayAdapter<String> spinnerAdapter = new MySpinnerAdapter(ProblemsActivity.this,R.layout.spinner_item_subjects,R.id.spinnerTitle,getResources().getStringArray(R.array.problems));
        spinner.setAdapter(spinnerAdapter);
    }

    @OnItemSelected(R.id.subject_spinner)
    public void onProblemItemSelected(Spinner spinner, int position){
        currentProblem = spinner.getSelectedItem().toString();
        if(problemeRezolvate.contains(currentProblem)){
            fab.setImageResource(R.drawable.ic_cross);
            problemIsCompleted = true;
        } else {
            fab.setImageResource(R.drawable.ic_check);
            problemIsCompleted = false;
        }
        updateLayout();
    }

    @OnClick(R.id.btn_show_solution)
    public void showSolutionPressed(Button view){
        if(!solutionIsVisible){
            webView.setVisibility(View.VISIBLE);
            view.setText("ASCUNDE SOLUTIA");
        } else {
            view.setText("AFISEAZA SOLUTIA");
            webView.setVisibility(View.GONE);
        }
        solutionIsVisible = !solutionIsVisible;
    }

    public void updateLayout(){
        problemText.setText(Html.fromHtml(FileUtility.readFromAsset(ProblemsActivity.this,"CodeSnippets/"+PATH_TO_PROBLEMS+currentProblem+"/enunt.txt")));
        webView.loadUrl("file:///android_asset/CodeSnippets/"+PATH_TO_PROBLEMS+currentProblem+"/code.html");
    }

    @OnClick(R.id.fabSubjects)
    public void onFabClick(View view){
        if(problemeRezolvate.contains(currentProblem)){
            problemeRezolvate.remove(currentProblem);
        }else {
            problemeRezolvate.add(currentProblem);
        }
        MySpinnerAdapter adapter = (MySpinnerAdapter)spinner.getAdapter();
        adapter.notifyDataSetChanged();
        // Animate icon change
        if(problemIsCompleted){
            problemIsCompleted = false;
            fab.setImageResource(R.drawable.avd_cross_to_check);
        } else {
            problemIsCompleted = true;
            fab.setImageResource(R.drawable.avd_check_to_cross);
        }
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((android.graphics.drawable.Animatable) drawable).start();
        }

        // Show a snackbar for confirmation
        if(problemIsCompleted) {
            RoundedSnackBar.showRoundedSnackBar(this,findViewById(R.id.coordinator_layout_subjects),"Felicitari, problema rezolvata!");
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

    public class MySpinnerAdapter extends ArrayAdapter<String> {
        public MySpinnerAdapter(Activity context, int resouceId, int textviewId, String[] list){
            super(context,resouceId,textviewId, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return rowview(convertView,position);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return rowview(convertView,position);
        }

        private View rowview(View convertView , int position){

            String rowItem = getItem(position);

            MySpinnerAdapter.viewHolder holder ;
            View rowview = convertView;
            if (rowview==null) {

                holder = new MySpinnerAdapter.viewHolder();
                LayoutInflater flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = flater.inflate(R.layout.spinner_item_subjects, null, false);

                holder.txtTitle = (TextView) rowview.findViewById(R.id.spinnerTitle);
                holder.imageView = (ImageView) rowview.findViewById(R.id.spinnerImage);
                rowview.setTag(holder);
            }else{
                holder = (MySpinnerAdapter.viewHolder) rowview.getTag();
            }
            holder.imageView.setVisibility(problemeRezolvate.contains(rowItem)? View.VISIBLE : View.GONE);
            holder.txtTitle.setText(rowItem);

            return rowview;
        }

        private class viewHolder{
            TextView txtTitle;
            ImageView imageView;
        }
    }
}
