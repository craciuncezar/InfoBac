package io.github.craciuncezar.infobac.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.cardview.widget.CardView;
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

    public static CardView getImageView(Context context, String scale, String height, String drawable){
        CardView cardView = new CardView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,Float.valueOf(height),context.getResources().getDisplayMetrics()));
        int leftRightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,context.getResources().getDisplayMetrics());
        int topBotMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics());
        layoutParams.setMargins(leftRightMargin,topBotMargin,leftRightMargin,topBotMargin);
        ImageView imageView = new ImageView(context);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,context.getResources().getDisplayMetrics()));
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.BlueGrey50));
        cardView.setElevation(0);
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

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);

        int id = context.getResources().getIdentifier(drawable, "drawable", context.getPackageName());
        imageView.setImageResource(id);

        cardView.addView(imageView);
        return cardView;
    }

    public static CardView getCodeSnippet(Context context, String name){
        CardView cardView = new CardView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int leftRightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,context.getResources().getDisplayMetrics());
        int topBotMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics());
        layoutParams.setMargins(leftRightMargin,topBotMargin,leftRightMargin,topBotMargin);
        cardView.setLayoutParams(layoutParams);
        cardView.setElevation(0);
        cardView.setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,context.getResources().getDisplayMetrics()));

        WebView webView = new WebView(context);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(layoutParams);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/CodeSnippets/"+name+".html");
        cardView.addView(webView);

        return cardView;
    }
}
