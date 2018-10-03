package io.github.craciuncezar.infobac.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.models.LessonPage;
import io.github.craciuncezar.infobac.views.LessonViews;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


public class LessonActivity extends AppCompatActivity {

    private static final String NAME_FOR_LESSON = "lesson";

    @BindView(R.id.toolbar_lesson) Toolbar toolbarLesson;
    @BindView(R.id.progress_lesson) TextView progressLesson;
    @BindView(R.id.lesson_content) LinearLayout lessonContent;
    @BindView(R.id.progress_bar_lesson) ProgressBar progressBar;

    private String lessonName;
    private ArrayList<LessonPage> lessonPages;

    private int currentPage = 0;
    private int lessonProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        ButterKnife.bind(this);
        initToolBar();

        lessonName = getIntent().getStringExtra(NAME_FOR_LESSON);
        lessonPages = getLessonData();

        if(DataManager.getInstance().getLessonsProgress().get(lessonName)!= null){
            lessonProgress = DataManager.getInstance().getLessonsProgress().get(lessonName);
            currentPage = lessonProgress == lessonPages.size() ? 0 : lessonProgress;
        } else {
            DataManager.getInstance().getLessonsProgress().put(lessonName,0);
            DataManager.getInstance().setCurrentLesson(lessonName);
        }
        progressLesson.setText(String.format(Locale.ENGLISH,"%d/%d", currentPage+1, lessonPages.size()));
        setProgressMax(progressBar,lessonPages.size()-1);
        setProgressAnimate(progressBar, currentPage);
        updateCurrentLayout(currentPage);
    }

    public static Intent getIntent(Context context, String lesson){
        Intent intent = new Intent(context, LessonActivity.class);
        intent.putExtra(NAME_FOR_LESSON, lesson);
        return intent;
    }

    @OnClick(R.id.forward_lesson)
    public void forwardLessonClicked(View view){
        if(currentPage<lessonPages.size()-1) {
            currentPage++;
            if(currentPage>lessonProgress) {
                DataManager.getInstance().setLessonsProgress(lessonName,currentPage);
                DataManager.getInstance().setCurrentLesson(lessonName);
            }
            progressLesson.setText(String.format(Locale.ENGLISH, "%d/%d", currentPage+1, lessonPages.size()));
            setProgressAnimate(progressBar,currentPage);
            lessonContent.removeAllViews();
            updateCurrentLayout(currentPage);
        }
    }

    @OnClick(R.id.back_lesson)
    public void backLessonClicked(View view){
        if(currentPage>0) {
            currentPage--;
            progressLesson.setText(String.format(Locale.ENGLISH, "%d/%d", currentPage+1, lessonPages.size()));
            setProgressAnimate(progressBar,currentPage);
            lessonContent.removeAllViews();
            updateCurrentLayout(currentPage);
        }
    }

    private void setProgressMax(ProgressBar pb, int max) {
        pb.setMax(max * 100);
    }

    private void setProgressAnimate(ProgressBar pb, int progressTo)
    {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
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

    private void initToolBar(){
        setSupportActionBar(toolbarLesson);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.lesson_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = this.getAssets().open("Teorie/"+lessonName+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<LessonPage> getLessonData() {
        ArrayList<LessonPage> pages = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray pages_jArry = obj.getJSONArray("pages");

            for (int i = 0; i < pages_jArry.length(); i++) {
                LessonPage lessonPage = new LessonPage();
                HashMap<String, String> widget;

                JSONArray widgets_jArry = pages_jArry.getJSONObject(i).getJSONArray("widgets");
                for(int j = 0; j<widgets_jArry.length(); j++) {
                    JSONObject widget_jObj = widgets_jArry.getJSONObject(j);
                    widget = new HashMap<>();

                    Iterator<String> iter = widget_jObj.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            String value = (String)widget_jObj.get(key);
                            widget.put(key, value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    lessonPage.addWidget(widget);
                }
                pages.add(lessonPage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pages;
    }

    public void updateCurrentLayout(int pageNumber){
        LessonPage currentPage = lessonPages.get(pageNumber);
        ArrayList<HashMap<String,String>> widgets = currentPage.getWidgets();
        for(HashMap<String,String> widget : widgets){
            switch (widget.get("type")){
                case "title":
                    lessonContent.addView(LessonViews.getTitleTextView(LessonActivity.this,widget.get("text")));
                    break;
                case "text":
                    lessonContent.addView(LessonViews.getTextView(LessonActivity.this,widget.get("text")));
                    break;
                case "image":
                    lessonContent.addView(LessonViews.getImageView(LessonActivity.this,widget.get("scale"),widget.get("height"),widget.get("drawable")));
                    break;
                default:
                    break;
            }
        }

    }

}
