package io.github.craciuncezar.infobac.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesManager {

    private static volatile PreferencesManager instance;
    private SharedPreferences preferences;

    private PreferencesManager() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static PreferencesManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PreferencesManager.class) {
                if (instance == null) {
                    instance = new PreferencesManager();
                    instance.preferences = context.getSharedPreferences("PREF", MODE_PRIVATE);
                }
            }
        }
        return instance;
    }

    public String getCurrentLesson() {
        return preferences.getString("currentLesson", "Introducere");
    }

    public void setCurrentLesson(String currentLesson) {
        preferences.edit().putString("currentLesson", currentLesson).apply();
    }

    public String getCurrentTheme() {
        return preferences.getString("theme", "Light Theme");
    }

    public void setCurrentTheme(String currentTheme) {
        preferences.edit().putString("theme", currentTheme).apply();
    }
}
