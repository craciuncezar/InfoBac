package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import io.github.craciuncezar.infobac.BaseActivity;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.PreferencesManager;
import io.github.craciuncezar.infobac.data.ProgressRepository;
import io.github.craciuncezar.infobac.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String CURRENT_NAVIGATION_SELECTED = "naviagation_selected";

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.home_menu_item:
                setTitle(getString(R.string.bottom_navigation_home));
                loadFragment(new HomeFragment());
                return true;
            case R.id.learn_menu_item:
                setTitle(getString(R.string.bottom_navigation_invata));
                loadFragment(new LearnFragment());
                return true;
            case R.id.exercise_menu_item:
                setTitle(getString(R.string.bottom_navigation_exerseaza));
                loadFragment(new ExerciseFragment());
                return true;
        }
        return true;
    };

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationListener);
        binding.themeText.setText(getCurrentTheme());
        loadFragment(new HomeFragment());
        binding.navigationView.setNavigationItemSelectedListener(this);


        initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NAVIGATION_SELECTED, binding.bottomNavigation.getSelectedItemId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int itemSelectedId = savedInstanceState.getInt(CURRENT_NAVIGATION_SELECTED);
        binding.bottomNavigation.setSelectedItemId(itemSelectedId);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        setTitle(getString(R.string.bottom_navigation_home));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    public void changeThemeClicked(View view) {
        if (getCurrentTheme().equals("Dark Theme")) {
            binding.themeIcon.setImageResource(R.drawable.ic_darkmode_active);
            setCurrentTheme("Light Theme");
        } else {
            binding.themeIcon.setImageResource(R.drawable.ic_darkmode);
            setCurrentTheme("Dark Theme");
        }
        binding.themeText.setText(getCurrentTheme());
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                            R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete_progress:
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setCancelable(true)
                        .setTitle(getString(R.string.are_you_sure))
                        .create();
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Da", (v, a) -> {
                    ProgressRepository.getInstance(getApplication()).deleteProgress();
                    PreferencesManager.getInstance(this).setCurrentLesson("Introducere");
                    dialog.dismiss();
                    recreate();
                });
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nu", (v, a) -> dialog.dismiss());
                dialog.show();
                if (getCurrentTheme().equals("Dark Theme") && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawableResource(R.color.darkThemeBackground);
                }
                return true;
            case R.id.play_store: {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                return true;
            }
            case R.id.info:
                AlertDialog infoDialog = new AlertDialog.Builder(MainActivity.this).setCancelable(true)
                        .setMessage(getString(R.string.despreApp))
                        .create();
                infoDialog.show();
                if (getCurrentTheme().equals("Dark Theme") && infoDialog.getWindow() != null) {
                    infoDialog.getWindow().setBackgroundDrawableResource(R.color.darkThemeBackground);
                }
                return true;
            case R.id.report_bug:
                Intent emailIntent;
                emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:craciuncezar1996@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug report info");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Bugul gasit: ");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }
}
