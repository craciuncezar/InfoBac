package io.github.craciuncezar.infobac.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.controllers.QuizActivity;

@SuppressLint("InflateParams")
public class CongratsDialog {
    public static void showLessonDialog(Context context, LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dialog_congrats, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button nextLesson = view.findViewById(R.id.btn_next);
        nextLesson.setOnClickListener(v -> {
            alertDialog.dismiss();
            ((Activity) context).finish();
        });
        TextView title = view.findViewById(R.id.textViewTitleCongrats);
        title.setText(context.getString(R.string.completed_lesson));
        LottieAnimationView animationView = view.findViewById(R.id.animation);
        animationView.setAnimation(R.raw.trophy);
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_margin));
        }
        alertDialog.show();
    }

    public static void showQuizDialog(Context context, LayoutInflater inflater, boolean changeQuizSetDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dialog_congrats, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button nextLesson = view.findViewById(R.id.btn_next);
        nextLesson.setOnClickListener(v -> {
            if (changeQuizSetDialog) {
                ((QuizActivity) context).changeQuizSetDialog();
            } else {
                ((Activity) context).finish();
            }
            alertDialog.dismiss();
        });
        TextView title = view.findViewById(R.id.textViewTitleCongrats);
        title.setText(context.getString(R.string.completed_quiz));
        LottieAnimationView animationView = view.findViewById(R.id.animation);
        animationView.setAnimation(R.raw.check_animation);
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.dialog_margin));
        }
        alertDialog.show();
    }

}
