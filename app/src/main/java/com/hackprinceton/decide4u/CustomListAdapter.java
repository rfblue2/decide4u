package com.hackprinceton.decide4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private ArrayList<Question> questions;

    CustomListAdapter(Context context, ArrayList<Question> questions) {
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

        // Declare button, button layout variables
        Button btnOpt1 = (Button)convertView.findViewById(R.id.btnOpt1);
        Button btnOpt2 = (Button)convertView.findViewById(R.id.btnOpt2);
        final LinearLayout btnLayout = (LinearLayout)convertView.findViewById(R.id.btnLayout);
        final RelativeLayout progBarLayout = (RelativeLayout)convertView.findViewById(R.id.progBarLayout);

        // Progress bar
        int opt1Votes = question.getOpt1Votes();
        int opt2Votes = question.getOpt2Votes();

        int percentage = (int) (100 * opt1Votes / (opt1Votes + opt2Votes));

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        progressBar.setProgress(percentage);

        // Change vote bar labels
        TextView prog1Name = (TextView) convertView.findViewById(R.id.prog1Name);
        TextView prog2Name = (TextView) convertView.findViewById(R.id.prog2Name);
        prog1Name.setText(question.getOpt1());
        prog2Name.setText(question.getOpt2());

        TextView prog1Votes = (TextView) convertView.findViewById(R.id.prog1Votes);
        TextView prog2Votes = (TextView) convertView.findViewById(R.id.prog2Votes);
        prog1Votes.setText(opt1Votes + " Votes");
        prog2Votes.setText(opt2Votes + " Votes");

        // Button click listeners

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
