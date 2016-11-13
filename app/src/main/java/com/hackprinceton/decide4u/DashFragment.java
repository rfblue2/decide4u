package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;

public class DashFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String title;
    private int page;
    public final static String QUESTION_KEY = "com.example.dashfragment.QUESTION";
    private Button newDec;
    private ListView myQuestions;
    private ArrayList<Question> qList;
    private Context mContext;
    private View view;

    public DashFragment() {
        // Required empty public constructor
    }

    public static DashFragment newInstance(int page, String title) {
        DashFragment fragment = new DashFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt("someInt", page);
            title = getArguments().getString("someTitle", title);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dash, container, false);

        newDec = (Button) view.findViewById(R.id.new_dec);
        newDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                startActivity(intent);
            }
        });

        myQuestions = (ListView) view.findViewById(R.id.my_questions);
        myQuestions.setOnItemClickListener(this);

        qList = new ArrayList<Question>();

        qList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "Description", "user0"));
        qList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description", "user1"));
        qList.add(new Question("Which class?", "COS 226", "COS 217", "Description", "user2"));

        DashListAdapter adapter = new DashListAdapter(mContext, qList);
        myQuestions.setAdapter(adapter);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String questionStr = (String) parent.getItemAtPosition(i).toString();
        Intent intent = new Intent(mContext, QDetailActivity.class);
        intent.putExtra(QUESTION_KEY, questionStr);
        startActivity(intent);
    }
}
