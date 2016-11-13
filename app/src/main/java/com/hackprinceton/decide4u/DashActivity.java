package com.hackprinceton.decide4u;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Displays your questions
 */
public class DashActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "TAG-DashActivity";

    public final static String QUESTION_KEY = "com.example.feedactivity.QUESTION";
    private Button newDec;
    private ListView myQuestions;
    private List<Question> qList;
    private DrawerLayout dLayout;
    private ListView menuOpts;
    private String[] menuList;

    private Context mContext;

    private ApiEndpointInterface mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mContext = this;

        newDec = (Button) findViewById(R.id.new_dec);
        newDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        myQuestions = (ListView) findViewById(R.id.my_questions);
        myQuestions.setOnItemClickListener(this);

//        qList = new ArrayList<Question>();

//        qList.add(new Question("Which car?", "Honda Civic", "Toyota Camry", "Description", "user0"));
//        qList.add(new Question("What phone OS should I use?", "Android", "iOS", "Description", "user1"));
//        qList.add(new Question("Which class?", "COS 226", "COS 217", "Description", "user2"));

        // Init the api service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://decide4u-149303.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiEndpointInterface.class);

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuOpts = (ListView) findViewById(R.id.drawer_opts);
        menuList = new String[] {"Answer Questions", "My Questions"};

        menuOpts.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuList));
//        menuOpts.setOnItemClickListener(new DrawerItemClickListener());

    }

    @Override
    protected void onResume() {
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
                        qList = response.body();

                        DashListAdapter adapter = new DashListAdapter(mContext, qList);
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
        new AlertDialog.Builder(this)
                .setTitle("Invalid Entry")
                .setMessage("Parent: " + parent)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
//            String questionStr = (String) parent.getItemAtPosition(i).toString();
//            Intent intent = new Intent(DashActivity.this, QDetailActivity.class);
//            intent.putExtra(QUESTION_KEY, questionStr);
//            startActivity(intent);
    }
}
