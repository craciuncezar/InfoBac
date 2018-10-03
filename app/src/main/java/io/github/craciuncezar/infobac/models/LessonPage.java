package io.github.craciuncezar.infobac.models;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonPage {
    ArrayList<HashMap<String ,String>> widgets;

    public LessonPage() {
        this.widgets = new ArrayList<>();
    }

    public void addWidget(HashMap<String, String> widget){
        this.widgets.add(widget);
    }

    public ArrayList<HashMap<String, String>> getWidgets() {
        return widgets;
    }
}
