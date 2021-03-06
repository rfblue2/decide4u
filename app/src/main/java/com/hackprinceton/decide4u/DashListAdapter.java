package com.hackprinceton.decide4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RotomPlasma on 11/12/16.
 */

public class DashListAdapter extends ArrayAdapter<Question> {
    private Context qContext;
    private LayoutInflater qInflater;
    private List<Question> qDataSource;

    public DashListAdapter(Context context, List<Question> questions) {
        super(context, 0, questions);
        qDataSource = questions;
        qInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        TextView questionTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DashListAdapter.ViewHolder holder;

        // if an existing view is not being reused, inflate the view
        if (convertView == null) {
            holder = new DashListAdapter.ViewHolder();
            convertView = qInflater.inflate(R.layout.dashlist_elem_layout, parent, false);
            holder.questionTextView = (TextView) convertView.findViewById(R.id.questionTitle);
            convertView.setTag(holder);
        } else {
            holder = (DashListAdapter.ViewHolder) convertView.getTag();
        }

        Question questionTitle = getItem(position);
        holder.questionTextView.setText(questionTitle.getQuestion());

        return convertView;
    }
}
