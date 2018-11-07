package io.github.craciuncezar.infobac.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.craciuncezar.infobac.R;

public class QuizBottomSheet extends BottomSheetDialogFragment {
    private QuizBottomSheetListener listener;

    public static QuizBottomSheet getQuizBottomSheet(String correctAnswer) {
        QuizBottomSheet quizBottomSheet = new QuizBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString("correctAnswer", correctAnswer);
        quizBottomSheet.setArguments(bundle);
        return quizBottomSheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_bottom_sheet, container, false);
        String correctAnswer = "";
        if (getArguments() != null) {
            correctAnswer = getArguments().getString("correctAnswer");
        }
        TextView textView = view.findViewById(R.id.textViewCorrectAnswer);
        textView.setText(String.format(getString(R.string.Correct_answer_was), correctAnswer));
        Button button = view.findViewById(R.id.btnContinue);
        button.setOnClickListener((v) -> {
            listener.onBottomSheetButtonPressed();
            dismiss();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (QuizBottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement QuizBottomSheetListener");
        }
    }

    public interface QuizBottomSheetListener {
        void onBottomSheetButtonPressed();
    }
}
