package io.github.craciuncezar.infobac.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import io.github.craciuncezar.infobac.views.RoundedSnackBar;

public class SubjectsActivity extends AppCompatActivity {
    @BindView(R.id.tabLayoutSubjects) TabLayout tabLayout;
    @BindView(R.id.subject_fragment_container) ViewPager viewPager;
    @BindView(R.id.subject_spinner) AppCompatSpinner subjectsSpinner;
    @BindView(R.id.toolbar_subjects) Toolbar toolbarSubjects;
    @BindView(R.id.fabSubjects) FloatingActionButton fab;

    private String currentSubject;
    private String filePath = "Subiecte/";
    private boolean subjectIsCompleted = false;
    private static final String PROFIL_KEY = "profil_subiecte";
    private ArrayList<String> subiecteRezolvate;

    public static Intent getIntent(Context context, String profil) {
        Intent intent = new Intent(context,SubjectsActivity.class);
        intent.putExtra(PROFIL_KEY,profil);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        subiecteRezolvate = DataManager.getInstance().getCompletedSubjects();

        filePath += getIntent().getStringExtra(PROFIL_KEY) + "/";
        ButterKnife.bind(this);

        currentSubject = filePath + getResources().getStringArray(R.array.bac_subjects)[0];

        initToolBar();
        initTabLayout();
        initSpinner();
    }

    private void initToolBar(){
        setSupportActionBar(toolbarSubjects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Subiecte Bac");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void initTabLayout(){
        SectionFragmentAdapter sectionFragmentAdapter = new SectionFragmentAdapter(getSupportFragmentManager());
        sectionFragmentAdapter.addFragment(new PdfSubjectFragment(),"Subiect");
        sectionFragmentAdapter.addFragment(new PdfSubjectFragment(),"Barem");
        viewPager.setAdapter(sectionFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new MySpinnerAdapter(this,R.layout.spinner_item_subjects,R.id.spinnerTitle, getResources().getStringArray(R.array.bac_subjects));
        subjectsSpinner.setAdapter(stringArrayAdapter);
    }

    @OnItemSelected(R.id.subject_spinner)
    public void onSubjectItemSelected(Spinner spinner, int position){
        currentSubject = filePath + spinner.getSelectedItem().toString();
        if(subiecteRezolvate.contains(currentSubject)){
            fab.setImageResource(R.drawable.ic_cross);
            subjectIsCompleted = true;
        } else {
            fab.setImageResource(R.drawable.ic_check);
            subjectIsCompleted = false;
        }
        viewPager.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.fabSubjects)
    public void onFabClick(View view){
        if(subiecteRezolvate.contains(currentSubject)){
            subiecteRezolvate.remove(currentSubject);
        }else {
            subiecteRezolvate.add(currentSubject);
        }
        MySpinnerAdapter adapter = (MySpinnerAdapter)subjectsSpinner.getAdapter();
        adapter.notifyDataSetChanged();
        // Animate icon change
        if(subjectIsCompleted){
            subjectIsCompleted = false;
            fab.setImageResource(R.drawable.avd_cross_to_check);
        } else {
            subjectIsCompleted = true;
            fab.setImageResource(R.drawable.avd_check_to_cross);
        }
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((android.graphics.drawable.Animatable) drawable).start();
        }

        // Show a snackbar for confirmation
        if(subjectIsCompleted) {
            RoundedSnackBar.showRoundedSnackBar(this,findViewById(R.id.coordinator_layout_subjects),"Felicitari, subiect rezolvat!");
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

    private class SectionFragmentAdapter extends FragmentPagerAdapter {
        ArrayList<PdfSubjectFragment> fragments = new ArrayList<>();
        ArrayList<String> fragmentTitles = new ArrayList<>();

        public void addFragment(PdfSubjectFragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        public SectionFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void notifyDataSetChanged() {
            fragments.get(0).updatePdfContainer(currentSubject);
            fragments.get(1).updatePdfContainer(currentSubject+"Barem");
        }
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

            viewHolder holder ;
            View rowview = convertView;
            if (rowview==null) {

                holder = new viewHolder();
                LayoutInflater flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowview = flater.inflate(R.layout.spinner_item_subjects, null, false);

                holder.txtTitle = (TextView) rowview.findViewById(R.id.spinnerTitle);
                holder.imageView = (ImageView) rowview.findViewById(R.id.spinnerImage);
                rowview.setTag(holder);
            }else{
                holder = (viewHolder) rowview.getTag();
            }
            holder.imageView.setVisibility(subiecteRezolvate.contains(filePath + rowItem)? View.VISIBLE : View.GONE);
            holder.txtTitle.setText(rowItem);

            return rowview;
        }

        private class viewHolder{
            TextView txtTitle;
            ImageView imageView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
