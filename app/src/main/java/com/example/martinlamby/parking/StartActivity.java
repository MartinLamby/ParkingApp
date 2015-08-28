package com.example.martinlamby.parking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.parse.Parse;

public class StartActivity extends Activity {

    public static SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPrefs = getSharedPreferences("com.example.martinlamby.parking", MODE_PRIVATE);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Id-here", "Key-here");


        boolean signedUp = sharedPrefs.getBoolean(getString(R.string.userIsSignedUp),false);
        System.out.println(signedUp);

        if(signedUp==false){
            startActivity(new Intent(this,SignUpActivity.class));
        }else{
            startActivity(new Intent(this,LoginActivity.class));
        }
        this.finish();
    }
}
