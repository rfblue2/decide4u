package com.hackprinceton.decide4u;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
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
import com.hackprinceton.decide4u.model.Question;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String QUESTION_KEY = "com.questionactivity.question";
    public EditText question;
    public EditText opt1;
    public EditText opt2;
    public EditText details;
    public Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();

        question = (EditText) findViewById(R.id.question);
        opt1 = (EditText) findViewById(R.id.option1);
        opt2 = (EditText) findViewById(R.id.option2);
        details = (EditText) findViewById(R.id.details);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
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
            finish();
        }
    }
}
