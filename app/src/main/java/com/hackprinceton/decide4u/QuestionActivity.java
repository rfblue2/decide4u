package com.hackprinceton.decide4u;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String QUESTION_KEY = "com.questionactivity.question";
    public static final String OPT1_KEY = "com.questionactivity.opt1";
    public static final String OPT2_KEY = "com.questionactivity.opt2";
    public static final String DETAILS = "com.questionactivity.details";
    public EditText question;
    public EditText opt1;
    public EditText opt2;
    public EditText details;
    public Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = (EditText) findViewById(R.id.question);
        opt1 = (EditText) findViewById(R.id.option1);
        opt2 = (EditText) findViewById(R.id.option2);
        details = (EditText) findViewById(R.id.details);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, DashActivity.class);
        i.putExtra(QUESTION_KEY, question.getText().toString());
        i.putExtra(OPT1_KEY, opt1.getText().toString());
        i.putExtra(OPT2_KEY, opt2.getText().toString());
        i.putExtra(DETAILS, details.getText().toString());
        startActivity(i);
    }
}
