package com.hackprinceton.decide4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

/**
 * Displays other people's questions
 */
public class FeedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button button;
    private ListView listView;
    private ArrayList<Question> arrayList;
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";

    public FeedActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FeedActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });



        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        arrayList = new ArrayList<Question>();

        arrayList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "I want to get my wife a birthday present!", "user0"));
        arrayList.add(new Question("Which pet?", "Cat", "Dog", "Description", "user1"));
        arrayList.add(new Question("Who should I vote for?", "Trump", "Clinton", "Description", "user2"));
        arrayList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description", "user3"));
        arrayList.add(new Question("What should I bake?", "Pie", "Cake", "Description", "user4"));
        arrayList.add(new Question("Which class?", "COS 226", "COS 217", "Description", "user5"));
        arrayList.add(new Question("Where should I matriculate?", "Princeton", "Harvard", "Description", "user6"));

        CustomListAdapter adapter = new CustomListAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Bundle bundle = new Bundle();
        Question q = (Question) parent.getItemAtPosition(i);
        bundle.putSerializable(QUESTION_KEY, q);

        Intent intent = new Intent(FeedActivity.this, QDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
