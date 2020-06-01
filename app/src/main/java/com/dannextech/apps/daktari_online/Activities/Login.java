package com.dannextech.apps.daktari_online.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.database.Queries;
import com.dannextech.apps.daktari_online.model.UserModel;
import com.dannextech.apps.daktari_online.utils.Utilities;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = findViewById(R.id.etPasswordLogin);
        etUsername = findViewById(R.id.etUserName);

        final Queries query = new Queries(Login.this);
        final Utilities utils = new Utilities();
        final UserModel[] user = query.getPassword(etUsername.getText().toString().trim());

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().trim().equals(user[0].getPassword())){
                    startActivity(new Intent(Login.this,PatientHome.class));
                }else if (query.getPassword(etUsername.getText().toString().trim()).equals(null)){
                    utils.alertMessage(Login.this, "New Member","Seems you have not created an account yet");
                }else{
                    utils.alertMessage(Login.this,"Error","You have entered a wrong password");
                }
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ChooseAccountType.class));
            }
        });
    }
}
