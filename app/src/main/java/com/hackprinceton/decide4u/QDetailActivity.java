package com.hackprinceton.decide4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

import static com.hackprinceton.decide4u.R.id.listView;

public class QDetailActivity extends AppCompatActivity{
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";
    private ListView listView;
    private ArrayList<Question> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdetail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final Question question = (Question) bundle.getSerializable(QUESTION_KEY);

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

        // Two layouts for toggling between buttons and percentage bar
        final LinearLayout btnLayout = (LinearLayout) findViewById(R.id.btnLayout);
        final RelativeLayout progBarLayout = (RelativeLayout) findViewById(R.id.progBarLayout);

        // Listeners for two option buttons
        questionOpt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);

                TextView prog1Name = (TextView) findViewById(R.id.prog1Name);
                TextView prog2Name = (TextView) findViewById(R.id.prog2Name);
                prog1Name.setText(question.getOpt1());
                prog2Name.setText(question.getOpt2());

                progBarLayout.setVisibility(view.VISIBLE);
            }
        });

        questionOpt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                btnLayout.setVisibility(view.GONE);

                TextView prog1Name = (TextView) findViewById(R.id.prog1Name);
                TextView prog2Name = (TextView) findViewById(R.id.prog2Name);
                prog1Name.setText(question.getOpt1());
                prog2Name.setText(question.getOpt2());

                progBarLayout.setVisibility(view.VISIBLE);
            }
        });

        // Comments listview
        listView = (ListView) findViewById(listView);
        arrayList = new ArrayList<Question>();



        CustomListAdapter adapter = new CustomListAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}
