package io.github.craciuncezar.infobac;

import org.junit.Before;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import io.github.craciuncezar.infobac.controllers.HomeFragment;
import io.github.craciuncezar.infobac.controllers.MainActivity;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setup(){
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void initTitle_test(){
        assertThat(mainActivity.getTitle()).isEqualTo("Home");
    }

    @Test
    public void loadFragmentTest(){
        mainActivity.loadFragment(new HomeFragment());
        assertThat(mainActivity.getSupportFragmentManager().getFragments().get(0)).isInstanceOf(HomeFragment.class);
    }
}
