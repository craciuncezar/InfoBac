package io.github.craciuncezar.infobac.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.entity.CompletedQuiz;

public class QuizSetAdapter extends RecyclerView.Adapter<QuizSetAdapter.ViewHolder> {
    private List<String> quizSets;
    private List<CompletedQuiz> completedQuizzes;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public QuizSetAdapter(Context context, String[] quizSets, List<CompletedQuiz> completedQuizzes) {
        this.quizSets = Arrays.asList(quizSets);
        this.completedQuizzes = completedQuizzes;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateCompletedQuizzes(List<CompletedQuiz> completedQuizzes){
        this.completedQuizzes = completedQuizzes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.spinner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String quizSet = getItem(position);
        holder.itemTitle.setText(quizSet);
        holder.itemCompleted.setVisibility(isQuizCompleted(quizSet) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return quizSets.size();
    }

    private String getItem(int position) {
        return quizSets.get(position);
    }

    private boolean isQuizCompleted(String quiz) {
        if (completedQuizzes != null) {
            for (CompletedQuiz completedQuiz : completedQuizzes) {
                if (completedQuiz.getQuizName().equals(quiz))
                    return true;
            }
        }
        return false;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemTitle;
        ImageView itemCompleted;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itmTitle);
            itemCompleted = itemView.findViewById(R.id.itmImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(v, getAdapterPosition());

        }
    }
}
