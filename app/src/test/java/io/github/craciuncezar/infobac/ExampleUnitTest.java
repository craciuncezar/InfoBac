package io.github.craciuncezar.infobac;

import android.app.Application;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;
import io.github.craciuncezar.infobac.viewmodels.ExerciseViewModel;

import static com.google.common.truth.Truth.assertThat;

public class ExampleUnitTest {
    private ExerciseViewModel exerciseViewModel;
    private Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp(){
        exerciseViewModel = new ExerciseViewModel((Application)context);
    }

    @Test
    public void test1(){
        assertThat(exerciseViewModel).isNotNull();
    }

}