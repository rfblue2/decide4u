package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;


public class FeedFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String title;
    private int page;
    private Button button;
    private ListView listView;
    private ArrayList<Question> arrayList;
    public final static String QUESTION_KEY = "com.example.feedfragment.QUESTION";
    private Context mContext;
    private View view;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(int page, String title) {
        FeedFragment fragment = new FeedFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        arrayList = new ArrayList<Question>();

        arrayList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "I want to get my wife a birthday present!", "user0"));
        arrayList.add(new Question("Which pet?", "Cat", "Dog", "Description", "user1"));
        arrayList.add(new Question("Who should I vote for?", "Trump", "Clinton", "Description", "user2"));
        arrayList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description", "user3"));
        arrayList.add(new Question("What should I bake?", "Pie", "Cake", "Description", "user4"));
        arrayList.add(new Question("Which class?", "COS 226", "COS 217", "Description", "user5"));
        arrayList.add(new Question("Where should I matriculate?", "Princeton", "Harvard", "Description", "user6"));

        CustomListAdapter adapter = new CustomListAdapter(mContext, arrayList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Bundle bundle = new Bundle();
        Question q = (Question) parent.getItemAtPosition(i);
        bundle.putSerializable(QUESTION_KEY, q);

        Intent intent = new Intent(mContext, QDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
