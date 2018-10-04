package io.github.craciuncezar.infobac.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import io.github.craciuncezar.infobac.DataManager;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.controllers.LessonActivity;

public class LessonCongratsDialog {
    public static void showDialog(Context context, LayoutInflater inflater){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dialog_congrats_lesson,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button nextLesson = view.findViewById(R.id.btn_next_lesson);

        nextLesson.setOnClickListener(v -> {
             alertDialog.dismiss();
             ((Activity) context).finish();
        });
        alertDialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_margin));

        alertDialog.show();
    }
}
