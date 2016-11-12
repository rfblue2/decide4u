package com.hackprinceton.decide4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);

        Intent intent = getIntent();
        String questionText = intent.getStringExtra(FeedActivity.QUESTION_KEY);
        TextView questionTitle = (TextView) findViewById(R.id.questionTv);
        questionTitle.setText(questionText);
    }
}
