package io.github.craciuncezar.infobac.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.craciuncezar.infobac.R;

public class LessonViews {
    public static TextView getTextView(Context context, String text){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        int leftRightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18,context.getResources().getDisplayMetrics());
        int topBotMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics());
        layoutParams.setMargins(leftRightMargin,topBotMargin,leftRightMargin,topBotMargin);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4.5f,  context.getResources().getDisplayMetrics()), 1.0f);
        textView.setTextColor(context.getResources().getColor(R.color.textColor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
        }

        return textView;
    }

    public static TextView getTitleTextView(Context context, String text){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        int leftRightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18,context.getResources().getDisplayMetrics());
        int topBotMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics());
        layoutParams.setMargins(leftRightMargin,topBotMargin,leftRightMargin,topBotMargin);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 4.5f,  context.getResources().getDisplayMetrics()), 1.0f);
        textView.setTextColor(context.getResources().getColor(R.color.textColor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
        }

        return textView;
    }

    public static ImageView getImageView(Context context, String scale, String height, String drawable){
        ImageView imageView = new ImageView(context);

        switch (scale.toLowerCase()) {
            case "center-inside":
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case "center-crop":
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            default:
                break;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Float.valueOf(height),context.getResources().getDisplayMetrics()));
        int leftRightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18,context.getResources().getDisplayMetrics());
        int topBotMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics());
        layoutParams.setMargins(leftRightMargin,topBotMargin,leftRightMargin,topBotMargin);
        imageView.setLayoutParams(layoutParams);

        int id = context.getResources().getIdentifier(drawable, "drawable", context.getPackageName());
        imageView.setImageResource(id);

        return imageView;
    }
}
