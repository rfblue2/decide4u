package com.hackprinceton.decide4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class QuestionActivity extends AppCompatActivity {

    EditText question;
    EditText opt1;
    EditText opt2;
    EditText details;
    boolean hasDetails;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        hasDetails = false;
        question = (EditText) findViewById(R.id.question);
        opt1 = (EditText) findViewById(R.id.option1);
        opt2 = (EditText) findViewById(R.id.option2);
        details = (EditText) findViewById(R.id.details);
        submit = (Button) findViewById(R.id.submit_btn);
    }


}
