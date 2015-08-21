package com.example.martinlamby.parking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.usernameSignUpEditText);
        password = (EditText) findViewById(R.id.passwordSignUpEditText);
        email = (EditText) findViewById(R.id.emailSignUpEditText);
        signUp = (Button) findViewById(R.id.signUpButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    public void signUpUser(){
        boolean emptyField = false;
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String emailString = email.getText().toString();

        emptyField = isEmptyString(this, usernameString, getString(R.string.missingUsername));
        emptyField = isEmptyString(this, passwordString, getString(R.string.missingPassword));
        emptyField = isEmptyString(this, emailString, getString(R.string.missingEmail));


        if(emptyField==false) {
            createParseUser();
        }

    }

    public void createParseUser(){
        ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    System.out.println("Sign Up sucessfull");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));     //Proceed to next activity
                } else {

                    StartActivity.sharedPrefs.edit().putBoolean(getString(R.string.userIsSignedUp), true).commit();

                    System.out.println("ParseError   " + e.getMessage());
                    showErrorToast(getApplicationContext(), e.getMessage());
                }
            }
        });
    }

    public static boolean isEmptyString(Context context, String message, String errorMessage){
        if(message.isEmpty()){
            showErrorToast(context, errorMessage);
            return true;
        }else{
            return false;
        }
    }

    public static void showErrorToast(Context context, String message){
        Toast errorMessage = Toast.makeText(context, message,Toast.LENGTH_SHORT);
        errorMessage.show();
    }
}
