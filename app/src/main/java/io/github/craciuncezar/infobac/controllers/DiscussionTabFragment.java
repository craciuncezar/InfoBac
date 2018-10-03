package io.github.craciuncezar.infobac.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.models.DiscussionComment;
import io.github.craciuncezar.infobac.models.DiscussionThread;
import io.github.craciuncezar.infobac.utils.DateUtils;
import io.github.craciuncezar.infobac.views.ReportDialog;


public class DiscussionTabFragment extends Fragment {

  private ArrayList<DiscussionThread> dummyData;
  private RecyclerView recyclerView;
  private ArrayList<DiscussionThread> likedThreads = new ArrayList<>();

  public DiscussionTabFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    recyclerView = new RecyclerView(getActivity());

    String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec egestas consequat risus, sit amet vestibulum purus auctor eget. Nulla facilisi. Duis commodo imperdiet nunc, ac mollis mi efficitur at. Integer hendrerit arcu nec purus tempor venenatis. Nullam dolor augue, tristique et libero sit amet, euismod laoreet mi. Sed sollicitudin urna in massa maximus lacinia. Aenean venenatis ";
    dummyData = new ArrayList<>();
    ArrayList<DiscussionComment> discussionComments = new ArrayList<>();
    discussionComments.add(new DiscussionComment("Hop'asa","Commentariu zilei!"));
    dummyData.add(new DiscussionThread("Da ce am facut sefu?", lorem,"Gigi Contra",new Date(),22,discussionComments));

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(new DiscussionAdapter(dummyData));
    recyclerView.setPadding(0,16,0,0);
    recyclerView.setClipToPadding(false);
    return recyclerView;
  }

  public void addNewDiscussionThread(DiscussionThread discussionThread){
    dummyData.add(0, discussionThread);
    DiscussionAdapter adapter = (DiscussionAdapter)recyclerView.getAdapter();
    adapter.setDiscussionThreads(dummyData);
    adapter.notifyDataSetChanged();

  }



  public class DiscussionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DiscussionThread> discussionThreads = new ArrayList<>();
    public class ViewHolder extends RecyclerView.ViewHolder{
      @BindView(R.id.thread_question) TextView threadQuestion;
      @BindView(R.id.thread_username) TextView threadUsername;
      @BindView(R.id.thread_date) TextView threadDate;
      @BindView(R.id.thread_message) TextView threadMessage;
      @BindView(R.id.thread_comments) TextView threadCommentsNumber;
      @BindView(R.id.thread_likes) TextView threadLikesNumber;
      @BindView(R.id.cardDiscussion) CardView cardDiscussion;
      @BindView(R.id.thread_like_container) View threadLikeContainer;
      @BindView(R.id.thread_like_icon) ImageView threadLikeIcon;
      @BindView(R.id.reportContainer) View reportContainer;

      private LinearLayout layout;
      private DiscussionThread thread;

      public ViewHolder(@NonNull LinearLayout layout) {
        super(layout);
        this.layout = layout;
        ButterKnife.bind(this, layout);

        threadLikeContainer.setOnClickListener(this.onClickLike);
        cardDiscussion.setOnClickListener(onClickCard);
        reportContainer.setOnClickListener(onClickReport);
      }


      View.OnClickListener onClickLike = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int itemPosition = recyclerView.getChildAdapterPosition(layout);
          thread = discussionThreads.get(itemPosition);
          if(!likedThreads.contains(thread)) {
            thread.incerementLikes();
            threadLikesNumber.setText(thread.getLikes() + "");
            likedThreads.add(thread);
            threadLikeIcon.setImageResource(R.drawable.ic_like_activated);
          } else {
            thread.removeLike();
            threadLikesNumber.setText(thread.getLikes() + "");
            likedThreads.remove(thread);
            threadLikeIcon.setImageResource(R.drawable.ic_like);
          }
        }
      };
      View.OnClickListener onClickCard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int itemPosition = recyclerView.getChildAdapterPosition(layout);
          thread = discussionThreads.get(itemPosition);
          Intent intent = new Intent(getContext(),CommentsActivity.class);
          intent.putExtra("thread", thread);
          startActivity(intent);
        }
      };
      View.OnClickListener onClickReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          ReportDialog.showDialog(getContext(),getLayoutInflater());
        }
      };

    }

    public DiscussionAdapter(ArrayList<DiscussionThread> discussionThreads) {
      this.discussionThreads = discussionThreads;
    }

    public void setDiscussionThreads(ArrayList<DiscussionThread> discussionThreads){
      this.discussionThreads = discussionThreads;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_card, parent,false);
      return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      ViewHolder myHolder = (ViewHolder) holder;
      DiscussionThread discussionThread = discussionThreads.get(position);
      myHolder.threadQuestion.setText(discussionThread.getTitle());
      myHolder.threadUsername.setText(discussionThread.getAuthor());
      myHolder.threadMessage.setText(discussionThread.getMessage());
      myHolder.threadCommentsNumber.setText(discussionThread.getCommentsNumber()+"");
      myHolder.threadLikesNumber.setText(discussionThread.getLikes()+"");
      myHolder.threadDate.setText(DateUtils.getDateString(discussionThread.getDateCreated()));
      if(likedThreads.contains(discussionThread)){
        myHolder.threadLikeIcon.setImageResource(R.drawable.ic_like_activated);
      } else {
        myHolder.threadLikeIcon.setImageResource(R.drawable.ic_like);
      }
    }

    @Override
    public int getItemCount() {
      return discussionThreads.size();
    }

  }
}
