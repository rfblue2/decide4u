package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class FeedFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG-FeedFragment";

    public final static String QUESTION_KEY = "com.example.feedfragment.QUESTION";
    private Button button;
    private ListView listView;
    private CustomListAdapter adapter;

    private Context mContext;

    private ApiEndpointInterface mApiService;

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
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);

        return view;
    }

    @Override
    public void onResume() {
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
