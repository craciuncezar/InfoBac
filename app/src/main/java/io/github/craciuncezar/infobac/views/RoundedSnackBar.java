package io.github.craciuncezar.infobac.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.ContentViewCallback;
import com.google.android.material.snackbar.Snackbar;

import io.github.craciuncezar.infobac.R;

public class RoundedSnackBar {
  public static void showRoundedSnackBar(Context context, View view, String text) {
    Snackbar snackbar = Snackbar
            .make(view, text, Snackbar.LENGTH_LONG);
    View snackbarView = snackbar.getView();
    TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
    textView.setTextColor(context.getResources().getColor(R.color.white));
    textView.setTextSize(18);
    snackbarView.setBackground(context.getResources().getDrawable(R.drawable.snackbar_bg));
    snackbar.show();
  }
}
