package io.github.craciuncezar.infobac.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.data.entity.CompletedSubject;
import io.github.craciuncezar.infobac.databinding.SpinnerItemBinding;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private List<CompletedSubject> completedSubjectList;

    public CustomSpinnerAdapter(Activity context, int resouceId, int textviewId, String[] list, List<CompletedSubject> completedList) {
        super(context, resouceId, textviewId, list);
        completedSubjectList = completedList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(convertView, position);
    }

    public void updateCompletedSubjects(List<CompletedSubject> completedSubjects) {
        this.completedSubjectList = completedSubjects;
        notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(convertView, position);
    }

    private View createItemView(View convertView, int position) {
        String rowItem = getItem(position);
        SpinnerItemBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.spinner_item, null, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (SpinnerItemBinding) convertView.getTag();
        }
        binding.setTitle(rowItem);
        binding.setIsCompleted(checkSubjectIsCompleted(rowItem));
        return convertView;
    }

    private boolean checkSubjectIsCompleted(String subject) {
        if (this.completedSubjectList != null) {
            for (CompletedSubject completedSubject : this.completedSubjectList) {
                if (completedSubject.getSubjectName().equals(subject)) {
                    return true;
                }
            }
        }
        return false;
    }
}
