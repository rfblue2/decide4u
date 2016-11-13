package com.hackprinceton.decide4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;
import java.util.List;

import static com.hackprinceton.decide4u.R.id.listView;

/**
 * Created by sherr on 11/12/2016.
 */

class CustomListAdapter extends ArrayAdapter<Question> {
    private Context qContext;
    private LayoutInflater qInflater;
    private List<Question> questions;

    CustomListAdapter(Context context, List<Question> questions) {
        super(context, 0, questions);
        this.questions = questions;
        qInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Question getItem(int position) {
        return questions.get(position);
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
            convertView = qInflater.inflate(R.layout.question_detail_layout, parent, false);
            holder.questionTv = (TextView) convertView.findViewById(R.id.qTitle);
            holder.usernameTv = (TextView) convertView.findViewById(R.id.qUsername);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = getItem(position);
        holder.questionTv.setText(question.getQuestion());
        holder.usernameTv.setText(question.getUsername());

        Button btnOpt1 = (Button)convertView.findViewById(R.id.btnOpt1);
        Button btnOpt2 = (Button)convertView.findViewById(R.id.btnOpt2);
        final LinearLayout btnLayout = (LinearLayout)convertView.findViewById(R.id.btnLayout);
        final RelativeLayout progBarLayout = (RelativeLayout)convertView.findViewById(R.id.progBarLayout);

        TextView prog1Name = (TextView) convertView.findViewById(R.id.prog1Name);
        TextView prog2Name = (TextView) convertView.findViewById(R.id.prog2Name);
        prog1Name.setText(question.getOpt1());
        prog2Name.setText(question.getOpt2());

        btnOpt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);
                progBarLayout.setVisibility(view.VISIBLE);

                notifyDataSetChanged();
            }
        });
        btnOpt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);
                progBarLayout.setVisibility(view.VISIBLE);

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
