package com.hackprinceton.decide4u;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hackprinceton.decide4u.model.Question;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "TAG-QuestionActivity";

    public static final String QUESTION_KEY = "com.questionactivity.question";
    public EditText question;
    public EditText opt1;
    public EditText opt2;
    public EditText details;
    public Button submitBtn;

    private ApiEndpointInterface mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        // Init the api service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);

        question = (EditText) findViewById(R.id.question);
        opt1 = (EditText) findViewById(R.id.option1);
        opt2 = (EditText) findViewById(R.id.option2);
        details = (EditText) findViewById(R.id.details);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(submitBtn)) {
            String qText = question.getText().toString();
            String opt1Text = opt1.getText().toString();
            String opt2Text = opt2.getText().toString();
            String detailText = details.getText().toString();
            if (qText.length() == 0 || opt1Text.length() == 0 || opt2Text.length() == 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Invalid Entry")
                        .setMessage("Missing some required fields, please check.")
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else {
                final Question q = new Question(qText, opt1Text, opt2Text, detailText, "user-submit");

                Log.d(TAG, "Sending " + q);

                // Send it to server
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Call<Void> call = mApiService.add(q);
                        try {
                            Response<Void> res = call.execute();
                            Log.d(TAG, "Add question success, " + res.body());
                        } catch (IOException e) {
                            Log.d(TAG, "Add question API call failed");
                            e.printStackTrace();
                        }
                    }
                });

                finish();
            }
        }

    }
}
