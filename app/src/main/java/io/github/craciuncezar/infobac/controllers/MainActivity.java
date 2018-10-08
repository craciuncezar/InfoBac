package io.github.craciuncezar.infobac.controllers;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.theme_icon) ImageView themeIcon;
    @BindView(R.id.theme_text) TextView themeText;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    private ActionBar mActionBar;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = DataManager.getInstance();
        dataManager.readData(MainActivity.this);

        ButterKnife.bind(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        BottomNavigationView mBottomNavigation = findViewById(R.id.bottom_navigation);

        mBottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationListener);
        loadFragment(new HomeFragment());

        // Setup action bar
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(getString(R.string.bottom_navigation_home));
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.theme_changer)
    public void changeThemeClicked(View view){
        if(themeText.getText().equals("Dark mode")){
            themeIcon.setImageResource(R.drawable.ic_darkmode_active);
            themeText.setText("Light mode");
        } else {
            themeIcon.setImageResource(R.drawable.ic_darkmode);
            themeText.setText("Dark mode");
        }
    }

    private void loadFragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                            R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.home_menu_item:
                    mActionBar.setTitle(getString(R.string.bottom_navigation_home));
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.learn_menu_item:
                    mActionBar.setTitle(getString(R.string.bottom_navigation_invata));
                    loadFragment(new LearnFragment());
                    return true;
                case R.id.exercise_menu_item:
                    mActionBar.setTitle(getString(R.string.bottom_navigation_exerseaza));
                    loadFragment(new ExerciseFragment());
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.getInstance().saveData(MainActivity.this);
    }
}
