package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
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

public class DashFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG-DashFragment";

    private Button newDec;
    private ListView myQuestions;

    private Context mContext;

    private ApiEndpointInterface mApiService;

    public DashFragment() {
        // Required empty public constructor
    }

    public static DashFragment newInstance() {
        DashFragment fragment = new DashFragment();

        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_dash, container, false);

        newDec = (Button) view.findViewById(R.id.new_dec);
        newDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionActivity.class);
                startActivity(intent);
            }
        });

        myQuestions = (ListView) view.findViewById(R.id.my_questions);
        myQuestions.setOnItemClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);

        List<Question> qList = new ArrayList<Question>();

        DashListAdapter adapter = new DashListAdapter(mContext, qList);
        myQuestions.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Pull from server this user's questions
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                // defaults to "user2"
                String name = mContext.getSharedPreferences(LoginActivity.LOGIN_PREF, Context.MODE_PRIVATE).getString(LoginActivity.USERNAME_KEY, "user2");

                Call<List<Question>> call = mApiService.list(name);
                call.enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                        Log.d(TAG, "List q's success: " + (new Gson()).toJson(response.body()));
                        DashListAdapter adapter = new DashListAdapter(mContext, response.body());
                        myQuestions.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Question>> call, Throwable t) {
                        Log.d(TAG, "List q's fail");
                        t.printStackTrace();
                    }
                });
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String questionStr = ((Question) parent.getItemAtPosition(i)).getQuestion();
        Intent intent = new Intent(mContext, QDetailActivity.class);
        intent.putExtra(QDetailActivity.QUESTION_KEY, questionStr);
        startActivity(intent);
    }
}
