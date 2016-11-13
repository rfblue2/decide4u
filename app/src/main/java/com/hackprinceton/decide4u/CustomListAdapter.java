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

class CustomListAdapter extends ArrayAdapter<Question> {
    private Context qContext;
    private LayoutInflater qInflater;

    CustomListAdapter(Context context, ArrayList<Question> questions) {
        super(context, 0, questions);
        qInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        TextView questionTv;
        TextView usernameTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // if an existing view is not being reused, inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = qInflater.inflate(R.layout.list_element_layout, parent, false);
            holder.questionTv = (TextView) convertView.findViewById(R.id.qTitle);
            holder.usernameTv = (TextView) convertView.findViewById(R.id.qUsername);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = getItem(position);
        holder.questionTv.setText(question.toString());
        holder.usernameTv.setText(question.getUsername());

        return convertView;
    }

}
