package com.hackprinceton.decide4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

/**
 * Displays your questions
 */
public class DashActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button newDec;
    private ListView myQuestions;
    private ArrayList<Question> qList;
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        newDec = (Button) findViewById(R.id.new_dec);
        newDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        myQuestions = (ListView) findViewById(R.id.my_questions);
        myQuestions.setOnItemClickListener(this);

        qList = new ArrayList<Question>();

        qList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "Description"));
        qList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description"));
        qList.add(new Question("Which class?", "COS 226", "COS 217", "Description"));

        CustomListAdapter adapter = new CustomListAdapter(this, qList);
        myQuestions.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String questionStr = (String) parent.getItemAtPosition(i).toString();
        Intent intent = new Intent(DashActivity.this, QDetailActivity.class);
        intent.putExtra(QUESTION_KEY, questionStr);
        startActivity(intent);
    }
}
