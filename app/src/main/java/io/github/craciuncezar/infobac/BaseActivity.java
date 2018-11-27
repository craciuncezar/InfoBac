package io.github.craciuncezar.infobac;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import io.github.craciuncezar.infobac.data.PreferencesManager;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private PreferencesManager preferencesManager;
    private String currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = PreferencesManager.getInstance(this);
        currentTheme = preferencesManager.getCurrentTheme();
        setAppTheme(currentTheme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentTheme != null && !currentTheme.equals(preferencesManager.getCurrentTheme()))
            recreate();
    }

    private void setAppTheme(String currentTheme) {
        switch (currentTheme) {
            case "Light Theme":
                setTheme(R.style.LightTheme);
                break;
            case "Dark Theme":
                setTheme(R.style.DarkTheme);
                break;
            default:
                setTheme(R.style.LightTheme);
                break;
        }
    }

    public String getCurrentTheme() {
        return currentTheme;
    }


    public void setCurrentTheme(String currentTheme) {
        if (!this.currentTheme.equals(currentTheme)) {
            this.currentTheme = currentTheme;
            resetFlags();
            this.preferencesManager.setCurrentTheme(currentTheme);
            recreate();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void resetFlags() {
        if (currentTheme.equals("Dark Theme")) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }
}
