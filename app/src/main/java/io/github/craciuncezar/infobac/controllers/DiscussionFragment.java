package io.github.craciuncezar.infobac.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.models.DiscussionThread;
import io.github.craciuncezar.infobac.views.RoundedSnackBar;

public class DiscussionFragment extends Fragment {
  @BindView(R.id.tab_layout_chat) TabLayout tabLayout;
  @BindView(R.id.container_chat_fragment) ViewPager containerChatFragment;
  @BindView(R.id.fab_add_discussion) FloatingActionButton floatingActionButtonDiscussion;

  private static final int CODE_FOR_ADD_DISCUSSION_RESULT = 4321;
  private DiscussionThread newDiscussionThread;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_chat, container, false);
    ButterKnife.bind(this, view);

    initTabLayout();

    Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
    floatingActionButtonDiscussion.startAnimation(myAnim);
    return view;
  }

  @OnClick(R.id.fab_add_discussion)
  public void addDiscussionClicked(View view) {
    Intent intent = new Intent(getContext(), AddDiscussionActivity.class);
    startActivityForResult(intent, CODE_FOR_ADD_DISCUSSION_RESULT);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == CODE_FOR_ADD_DISCUSSION_RESULT && resultCode == Activity.RESULT_OK){
      newDiscussionThread = (DiscussionThread) data.getSerializableExtra("newThread");
      containerChatFragment.getAdapter().notifyDataSetChanged();
      RoundedSnackBar.showRoundedSnackBar(getContext(),getView().findViewById(R.id.chatCoordinatorView),"Postarea a fost adaugata!");
    }
  }

  private void initTabLayout(){
    ChatSectionAdapter adapter = new ChatSectionAdapter(getChildFragmentManager());
    adapter.addFragment(new DiscussionTabFragment(),getString(R.string.new_tab));
    adapter.addFragment(new DiscussionTabFragment(),getString(R.string.popular_tab));
    containerChatFragment.setAdapter(adapter);
    tabLayout.setupWithViewPager(containerChatFragment);
  }

  private class ChatSectionAdapter extends FragmentPagerAdapter{
    private ArrayList<DiscussionTabFragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    public void addFragment(DiscussionTabFragment discussionFragment, String title){
      fragments.add(discussionFragment);
      titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }

    public ChatSectionAdapter(FragmentManager fm) {
      super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      return titles.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
      super.notifyDataSetChanged();
      for(DiscussionTabFragment fragment : fragments) {
        fragment.addNewDiscussionThread(newDiscussionThread);
      }
    }
  }
}
