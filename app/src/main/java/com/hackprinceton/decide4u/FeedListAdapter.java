package com.hackprinceton.decide4u;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hackprinceton.decide4u.R.id.listView;

/**
 * Created by sherr on 11/12/2016.
 */

class FeedListAdapter extends ArrayAdapter<Question> {
    private static final String TAG = "TAG-FeedListAdapter";

    private Context qContext;
    private LayoutInflater qInflater;
    private List<Question> questions;

    private Context mContext;

    private ApiEndpointInterface mApiService;

    FeedListAdapter(Context context, List<Question> questions) {
        super(context, 0, questions);
        mContext = context;
        this.questions = questions;
        qInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);
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

        final Question question = getItem(position);
        holder.questionTv.setText(question.getQuestion());
        holder.usernameTv.setText(question.getUsername());

        final String name = mContext.getSharedPreferences(LoginActivity.LOGIN_PREF, Context.MODE_PRIVATE).getString(LoginActivity.USERNAME_KEY, "");

        // Declare button, button layout variables
        Button btnOpt1 = (Button)convertView.findViewById(R.id.btnOpt1);
        Button btnOpt2 = (Button)convertView.findViewById(R.id.btnOpt2);
        final LinearLayout btnLayout = (LinearLayout)convertView.findViewById(R.id.btnLayout);
        final RelativeLayout progBarLayout = (RelativeLayout)convertView.findViewById(R.id.progBarLayout);

        // Conditions in which user cannot vote (already voted, author of question)
        if (name.equals(question.getUsername()) || question.getUsers().contains(name)) {
            btnLayout.setVisibility(View.GONE);
            progBarLayout.setVisibility(View.VISIBLE);
        }

        btnOpt1.setText(question.getOpt1());
        btnOpt2.setText(question.getOpt2());

        // Progress bar
        int opt1Votes = question.getOpt1Votes();
        int opt2Votes = question.getOpt2Votes();

        int percentage = 50;
        if (opt1Votes + opt2Votes != 0)
            percentage = (int) (100 * opt1Votes / (opt1Votes + opt2Votes));

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
                btnLayout.setVisibility(View.GONE);
                progBarLayout.setVisibility(View.VISIBLE);

                notifyDataSetChanged();

                question.vote1();

                if (!name.equals("")) question.addUser(name);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Call<Question> call = mApiService.update(question.getId(), question);
                        call.enqueue(new Callback<Question>() {
                            @Override
                            public void onResponse(Call<Question> call, Response<Question> response) {
                                Log.d(TAG, "Update question success: " + (new Gson()).toJson(response.body()));
                            }

                            @Override
                            public void onFailure(Call<Question> call, Throwable t) {
                                Log.d(TAG, "Update question fail");
                                t.printStackTrace();
                            }
                        });
                    }
                });
            }
        });
        btnOpt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);
                progBarLayout.setVisibility(view.VISIBLE);

                notifyDataSetChanged();

                question.vote2();

                if (!name.equals("")) question.addUser(name);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Call<Question> call = mApiService.update(question.getId(), question);
                        call.enqueue(new Callback<Question>() {
                            @Override
                            public void onResponse(Call<Question> call, Response<Question> response) {
                                Log.d(TAG, "Update question success: " + (new Gson()).toJson(response.body()));
                            }

                            @Override
                            public void onFailure(Call<Question> call, Throwable t) {
                                Log.d(TAG, "Update question fail");
                                t.printStackTrace();
                            }
                        });
                    }
                });
            }
        });

        return convertView;
    }

}
