package com.hackprinceton.decide4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

import static com.hackprinceton.decide4u.R.id.listView;

/**
 * Created by sherr on 11/12/2016.
 */

public class CustomListAdapter extends ArrayAdapter<Question> {
    private Context qContext;
    private LayoutInflater qInflater;
    private ArrayList<Question> qDataSource;

    public CustomListAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
        qDataSource = questions;
        qInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView questionTextView;
    }

//    @Override
//    public int getCount() {
//        return qDataSource.size();
//    }
//
//    @Override
//    public Question getItem(int position) {
//        return qDataSource.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // if an existing view is not being reused, inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = qInflater.inflate(R.layout.list_element_layout, parent, false);
            holder.questionTextView = (TextView) convertView.findViewById(R.id.questionTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question questionTitle = getItem(position);
        holder.questionTextView.setText(questionTitle.toString());

        return convertView;
    }

}
