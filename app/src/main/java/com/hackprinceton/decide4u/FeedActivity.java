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

        arrayList.add(new Question("Honda Civic or Toyota Camry?"));
        arrayList.add(new Question("Cat or Dog?"));
        arrayList.add(new Question("Trump or Clinton?"));
        arrayList.add(new Question("Android or iOS?"));
        arrayList.add(new Question("Pie or Cake?"));
        arrayList.add(new Question("COS 226 or COS 217?"));
        arrayList.add(new Question("Princeton or Princeton?"));

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
