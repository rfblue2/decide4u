package com.hackprinceton.decide4u;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME_KEY = "username";
    public static final String LOGIN_PREF = "login";

    private Context mContext;
    private Button login;
    private EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        login = (Button) findViewById(R.id.btnlogin);
        usernameText = (EditText) findViewById(R.id.etusername);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE).edit()
                        .putString(USERNAME_KEY, usernameText.getText().toString()).apply();
                Intent i = new Intent(mContext, DashActivity.class);
                startActivity(i);
            }
        });
    }
}
