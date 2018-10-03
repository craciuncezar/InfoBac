package io.github.craciuncezar.infobac.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.models.DiscussionComment;
import io.github.craciuncezar.infobac.models.DiscussionThread;
import io.github.craciuncezar.infobac.utils.DateUtils;
import io.github.craciuncezar.infobac.views.ReportDialog;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_comments)
    Toolbar toolbarComments;
    @BindView(R.id.recycler_comments)
    RecyclerView recyclerViewComments;
    @BindView(R.id.editTextComment)
    EditText editTextComment;
    @BindView(R.id.commentsRootView)
    ConstraintLayout rootView;

    private DiscussionThread discussionThread;
    private boolean liked = false;
    private ArrayList<DiscussionComment> commentsLiked = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        discussionThread = (DiscussionThread) getIntent().getSerializableExtra("thread");

        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComments.setAdapter(new HeaderAdapter(discussionThread));

        initToolbar();

    }

    private void initToolbar(){
        setSupportActionBar(toolbarComments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_trimite_comment)
    public void trimiteCommentClicked(View view){
        String commentText = editTextComment.getText().toString().trim();
        if(validateComment()){
            DiscussionComment discussionComment = new DiscussionComment("Cezar",commentText);
            discussionThread.addComment(discussionComment);

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            recyclerViewComments.getAdapter().notifyDataSetChanged();

            editTextComment.setText("");
            editTextComment.clearFocus();

            Snackbar snackbar = Snackbar
                    .make(rootView, "Comentariul a fost adaugat!", Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(18);
            snackbarView.setBackground(getResources().getDrawable(R.drawable.snackbar_bg));
            snackbar.getView().getLayoutParams().width = AppBarLayout.LayoutParams.MATCH_PARENT;
            snackbar.show();
        }
    }

    public boolean validateComment(){
        boolean isValid = true;
        if(editTextComment.getText().toString().trim().length()<12){
            editTextComment.setError("Comentariul tau trebuie sa contina minim 12 caractere!");
            isValid = false;
        }
        return isValid;
    }

    public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;
        DiscussionThread discussionThread;

        public HeaderAdapter(DiscussionThread discussionThread) {
            this.discussionThread = discussionThread;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = getLayoutInflater().inflate(R.layout.comments_card, parent,false);
                return new VHItem(view);
            } else if (viewType == TYPE_HEADER) {
                View view = getLayoutInflater().inflate(R.layout.comments_header, parent,false);
                return new VHHeader(view);
            }

            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VHItem) {
                DiscussionComment comment = getItem(position);
                VHItem item = (VHItem) holder;
                item.commentAuthor.setText(comment.getAuthor());
                item.commentMessage.setText(comment.getMessage());
                item.commentDate.setText(DateUtils.getDateString(comment.getCreatedDate()));
                item.commentLikes.setText(comment.getLikes()+"");
            } else if (holder instanceof VHHeader) {
                VHHeader item = (VHHeader) holder;
                item.threadQuestion.setText(discussionThread.getTitle());
                item.threadMessage.setText(discussionThread.getMessage());
                item.threadUsername.setText(discussionThread.getAuthor());
                item.threadCommentsNumber.setText(discussionThread.getCommentsNumber()+"");
                item.threadLikesNumber.setText(discussionThread.getLikes()+ "");
                item.threadDate.setText(DateUtils.getDateString(discussionThread.getDateCreated()));
            }
        }

        @Override
        public int getItemCount() {
            return discussionThread.getCommentsNumber() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private DiscussionComment getItem(int position) {
            return discussionThread.getComments().get(position - 1);
        }

        class VHItem extends RecyclerView.ViewHolder {
            @BindView(R.id.comment_user_icon) ImageView userIcon;
            @BindView(R.id.comment_date) TextView commentDate;
            @BindView(R.id.comment_message) TextView commentMessage;
            @BindView(R.id.comment_username) TextView commentAuthor;
            @BindView(R.id.comment_likes) TextView commentLikes;
            @BindView(R.id.reportContainer) View reportContainer;
            @BindView(R.id.comment_like_container) View likeContainer;
            @BindView(R.id.comment_like_icon) ImageView commentLikeIcon;

            View layout;

            public VHItem(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);

                layout = itemView;

                likeContainer.setOnClickListener(onClickLike);
                reportContainer.setOnClickListener(onClickReport);
            }

            /* Todo: DRY PROBLEM */
            View.OnClickListener onClickReport = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReportDialog.showDialog(CommentsActivity.this,getLayoutInflater());
                }
            };

            View.OnClickListener onClickLike = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerViewComments.getChildAdapterPosition(layout);
                    DiscussionComment discussionComment = discussionThread.getComments().get(itemPosition-1);

                    if(commentsLiked.contains(discussionComment)) {
                        discussionComment.removeLike();
                        commentsLiked.remove(discussionComment);
                        commentLikes.setText(discussionComment.getLikes() + "");
                        commentLikeIcon.setImageResource(R.drawable.ic_like);
                    } else {
                        discussionComment.incrementLikes();
                        commentsLiked.add(discussionComment);
                        commentLikes.setText(discussionComment.getLikes() + "");
                        commentLikeIcon.setImageResource(R.drawable.ic_like_activated);
                    }
                }
            };
        }

        class VHHeader extends RecyclerView.ViewHolder {
            @BindView(R.id.comment_header_question) TextView threadQuestion;
            @BindView(R.id.comment_header_username) TextView threadUsername;
            @BindView(R.id.comment_header_date) TextView threadDate;
            @BindView(R.id.comment_header_message) TextView threadMessage;
            @BindView(R.id.comment_header_comments) TextView threadCommentsNumber;
            @BindView(R.id.comment_header_likes) TextView threadLikesNumber;
            @BindView(R.id.reportContainer) View reportContainer;
            @BindView(R.id.thread_like_container) View likeContainer;
            @BindView(R.id.comment_header_likes_icon) ImageView threadLikeIcon;
            public VHHeader(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);

                reportContainer.setOnClickListener(onClickReport);
                likeContainer.setOnClickListener(onClickLike);
            }

            /* DRY PROBLEM*/
            View.OnClickListener onClickReport = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReportDialog.showDialog(CommentsActivity.this,getLayoutInflater());
                }
            };

            View.OnClickListener onClickLike = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!liked) {
                        liked = !liked;
                        discussionThread.incerementLikes();
                        threadLikesNumber.setText(discussionThread.getLikes() + "");
                        threadLikeIcon.setImageResource(R.drawable.ic_like_activated);
                    } else {
                        liked = !liked;
                        discussionThread.removeLike();
                        threadLikesNumber.setText(discussionThread.getLikes() + "");
                        threadLikeIcon.setImageResource(R.drawable.ic_like);
                    }
                }
            };
        }
    }
}
