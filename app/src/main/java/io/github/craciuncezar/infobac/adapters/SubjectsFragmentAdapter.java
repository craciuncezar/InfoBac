package io.github.craciuncezar.infobac.adapters;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import io.github.craciuncezar.infobac.controllers.PdfSubjectFragment;

public class SubjectsFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<PdfSubjectFragment> fragments = new ArrayList<>();
    private ArrayList<String> fragmentTitles = new ArrayList<>();

    public SubjectsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(PdfSubjectFragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
