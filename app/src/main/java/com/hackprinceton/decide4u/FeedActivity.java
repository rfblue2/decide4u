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
    private ArrayAdapter<Question> adapter;
    private ArrayList<Question> arrayList;
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";

    public FeedActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        arrayList = new ArrayList<Question>();

        arrayList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", ""));
        arrayList.add(new Question("Which pet?", "Cat", "Dog", ""));
        arrayList.add(new Question("Who should I vote for?", "Trump", "Clinton", ""));
        arrayList.add(new Question("What phone OS should I use?", "Android", "iOS", ""));
        arrayList.add(new Question("What should I bake?", "Pie", "Cake", ""));
        arrayList.add(new Question("Which class?", "COS 226", "COS 217", ""));
        arrayList.add(new Question("Where should I matriculate?", "Princeton", "Harvard", ""));

        adapter = new ArrayAdapter<Question>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String questionStr = (String) parent.getItemAtPosition(i).toString();
        Intent intent = new Intent(FeedActivity.this, QDetailActivity.class);
        intent.putExtra(QUESTION_KEY, questionStr);
        startActivity(intent);
    }
}
