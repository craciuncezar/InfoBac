package io.github.craciuncezar.infobac.adapters;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import androidx.databinding.BindingAdapter;
import io.github.craciuncezar.infobac.R;

public class BindingAdapters {
    // Progress bar animation for progress
    @BindingAdapter("setProgressAnimate")
    public static void setProgressAnimate(ProgressBar progressBar, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progressTo * 100);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }
    // Switch selected answer card background and text
    @BindingAdapter("isSelectedAnswer")
    public static void isSelectedAnswer(CardView cardView, boolean isAnswer){
        TypedValue typedValueColor = new TypedValue();
        cardView.getContext().getTheme().resolveAttribute(R.attr.quizCardColor, typedValueColor, true);
        if(isAnswer){
            cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(R.color.colorPrimary));
            ((TextView) cardView.getChildAt(0)).setTextColor(cardView.getContext().getResources().getColor(R.color.white));
        } else {
            cardView.setCardBackgroundColor(typedValueColor.data);
            ((TextView) cardView.getChildAt(0)).setTextColor(cardView.getContext().getResources().getColor(R.color.blueGrey));
        }
    }

    @BindingAdapter("isVisible")
    public static void isVisible(View view, Boolean isVisible){
        if(isVisible){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("animateFabIcon")
    public static void setAnimateFabIcon(FloatingActionButton view, boolean isCompleted) {
        if (isCompleted) {
            view.setImageResource(R.drawable.avd_check_to_cross);
        } else {
            view.setImageResource(R.drawable.avd_cross_to_check);
        }
        // Animate icon change
        Drawable drawable = view.getDrawable();
        if (drawable instanceof Animatable) {
            ((android.graphics.drawable.Animatable) drawable).start();
        }
    }
}
