package com.hackprinceton.decide4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hackprinceton.decide4u.model.Question;

public class QDetailActivity extends AppCompatActivity {
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Question question = (Question) bundle.getSerializable(QUESTION_KEY);

        TextView questionTitle = (TextView) findViewById(R.id.qTitle);
        TextView questionUser = (TextView) findViewById(R.id.qUsername);
        Button questionOpt1 = (Button) findViewById(R.id.btnOpt1);
        Button questionOpt2 = (Button) findViewById(R.id.btnOpt2);
        TextView questionDetails = (TextView) findViewById(R.id.qDetails);

        questionTitle.setText(question.toString());
        questionUser.setText(question.getUsername());
        questionOpt1.setText(question.getOpt1());
        questionOpt2.setText(question.getOpt2());
        questionDetails.setText(question.getDetails());
    }
}
