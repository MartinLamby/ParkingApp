package com.example.martinlamby.parking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private Button logIn;
    private EditText username;
    private EditText password;
    private ProgressDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.usernameLoginEditText);
        password = (EditText) findViewById(R.id.passwordLoginEditText);
        logIn = (Button) findViewById(R.id.loginButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logInUser(){
        boolean usernameEmptyField = false;
        boolean passwordEmptyField = false;
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        usernameEmptyField = SignUpActivity.isEmptyString(this, usernameString, getString(R.string.missingUsername));
        passwordEmptyField = SignUpActivity.isEmptyString(this, passwordString, getString(R.string.missingPassword));

        if(usernameEmptyField == false && passwordEmptyField == false) {
            logInParseUser(usernameString, passwordString);
            loginProgressDialog = StartActivity.showProgressDialog("Login in progress",this);

        }

    }

    public void logInParseUser(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    System.out.println("Login Sucessfull");
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);     //Proceed to next activity
                    finish();
                } else {
                    System.out.println("Login failed | Problem:   " + e.getMessage());
                    SignUpActivity.showErrorToast(getApplicationContext(), e.getMessage());
                }
                loginProgressDialog.dismiss();
            }
        });
    }
}
