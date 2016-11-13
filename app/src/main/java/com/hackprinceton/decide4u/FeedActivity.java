package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hackprinceton.decide4u.model.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Displays other people's questions
 */
public class FeedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG-FeedActivity";
    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";

    private Button button;
    private ListView listView;
    private CustomListAdapter adapter;
    private Context mContext;

    private ApiEndpointInterface mApiService;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);
//        arrayList = new ArrayList<Question>();

//        arrayList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "I want to get my wife a birthday present!", "user0"));
//        arrayList.add(new Question("Which pet?", "Cat", "Dog", "Description", "user1"));
//        arrayList.add(new Question("Who should I vote for?", "Trump", "Clinton", "Description", "user2"));
//        arrayList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description", "user3"));
//        arrayList.add(new Question("What should I bake?", "Pie", "Cake", "Description", "user4"));
//        arrayList.add(new Question("Which class?", "COS 226", "COS 217", "Description", "user5"));
//        arrayList.add(new Question("Where should I matriculate?", "Princeton", "Harvard", "Description", "user6"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Pull from server recent
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Question>> call = mApiService.listRecent();
                call.enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                        Log.d(TAG, "List feed q's success: " + (new Gson()).toJson(response.body()));

                        adapter = new CustomListAdapter(mContext, (List<Question>)response.body());
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {
                        Log.d(TAG, "List feed q's fail");
                        t.printStackTrace();
                    }
                });
            }
        });
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
