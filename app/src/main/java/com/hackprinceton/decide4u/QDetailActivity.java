package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class QDetailActivity extends AppCompatActivity{
    public final static String QUESTION_KEY = "question";

    private final static String TAG = "TAG-QDetailActivity";

    private Context mContext;

    private ApiEndpointInterface mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);

        mContext = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final Question question = (Question) bundle.getSerializable(QUESTION_KEY);

        final String name = mContext.getSharedPreferences(LoginActivity.LOGIN_PREF, Context.MODE_PRIVATE).getString(LoginActivity.USERNAME_KEY, "");


        TextView questionTitle = (TextView) findViewById(R.id.qTitle);
        TextView questionUser = (TextView) findViewById(R.id.qUsername);
        Button questionOpt1 = (Button) findViewById(R.id.btnOpt1);
        Button questionOpt2 = (Button) findViewById(R.id.btnOpt2);
        TextView questionDetails = (TextView) findViewById(R.id.qDetails);

        questionTitle.setText(question.getQuestion());
        questionUser.setText(question.getUsername());
        questionOpt1.setText(question.getOpt1());
        questionOpt2.setText(question.getOpt2());
        questionDetails.setText(question.getDetails());


        // Declare button, button layout variables
        final LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btnLayout);
        final RelativeLayout progBarLayout = (RelativeLayout) findViewById(R.id.progBarLayout);

        // Conditions in which user cannot vote (already voted, author of question)
        if (name.equals(question.getUsername()) || question.getUsers().contains(name)) {
            btnLayout.setVisibility(View.GONE);
            progBarLayout.setVisibility(View.VISIBLE);
        }

        // Progress bar
        int opt1Votes = question.getOpt1Votes();
        int opt2Votes = question.getOpt2Votes();

        int percentage = 50;
        if (opt1Votes + opt2Votes != 0)
            percentage = (int) (100 * opt1Votes / (opt1Votes + opt2Votes));

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(percentage);

        // Change vote bar labels
        TextView prog1Name = (TextView) findViewById(R.id.prog1Name);
        TextView prog2Name = (TextView) findViewById(R.id.prog2Name);
        prog1Name.setText(question.getOpt1());
        prog2Name.setText(question.getOpt2());

        TextView prog1Votes = (TextView) findViewById(R.id.prog1Votes);
        TextView prog2Votes = (TextView) findViewById(R.id.prog2Votes);
        prog1Votes.setText(opt1Votes + " Votes");
        prog2Votes.setText(opt2Votes + " Votes");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);

        // Listeners for two option buttons
        questionOpt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);

                TextView prog1Name = (TextView) findViewById(R.id.prog1Name);
                TextView prog2Name = (TextView) findViewById(R.id.prog2Name);
                prog1Name.setText(question.getOpt1());
                prog2Name.setText(question.getOpt2());

                progBarLayout.setVisibility(view.VISIBLE);

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

        questionOpt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                btnLayout.setVisibility(View.GONE);

                TextView prog1Name = (TextView) findViewById(R.id.prog1Name);
                TextView prog2Name = (TextView) findViewById(R.id.prog2Name);
                prog1Name.setText(question.getOpt1());
                prog2Name.setText(question.getOpt2());

                progBarLayout.setVisibility(View.VISIBLE);
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
    }
}
