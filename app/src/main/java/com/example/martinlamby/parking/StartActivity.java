package com.example.martinlamby.parking;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseUser;

public class StartActivity extends Application {

    public static SharedPreferences sharedPrefs;
    private static boolean isUserNew;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "yrtjBvsxg5XuPeOED8H12dm5X7gdN1v3P7bajf9L","ZeSXy39DIWy5Br6ncKKTYIYf0EMCIM6SIahBSrbV");

        logOutCurrentUser();
        checkIfUserIsNew();


    }
    //make sure Current User is logged Out
    public void logOutCurrentUser(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.logOut();
    }

    public void checkIfUserIsNew(){
        sharedPrefs = getSharedPreferences("com.example.martinlamby.parking", MODE_PRIVATE);

        //only for testing purposes if enabled App will not recognize exisiting user and will pass you to SignUp Screen
        //sharedPrefs.edit().clear().commit();

        isUserNew = sharedPrefs.getBoolean(getString(R.string.userIsSignedUp), false);
    }

    public static boolean getIsUserNew(){
        return isUserNew;
    }
    //shows Pop up Progress Dialog in after Sign Up (Activity) and after Login (Activity)
    public static ProgressDialog showProgressDialog(String titleMessage, Context context){
        ProgressDialog progressDialog = ProgressDialog.show(context,titleMessage,"Please wait...", true);
        progressDialog.show();
        return progressDialog;
    }

}
