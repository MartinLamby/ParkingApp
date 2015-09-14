package com.example.martinlamby.parking;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button navigateToCar;
    private Button shareCarPosition;
    private Button heatMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigateToCar = (Button) findViewById(R.id.NavigateToCarButton);
        shareCarPosition = (Button) findViewById(R.id.shareButton);
        heatMap = (Button) findViewById(R.id.heatMapButton);

        startService(new Intent(getBaseContext(), GeoLocationService.class));
        startService(new Intent(getBaseContext(), ShakeDetectorService.class));


        navigateToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send Location to Google Maps and start navigation
                startNavigation();
            }
        });
        shareCarPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShareCarPositionActivity.class));
                //send Location to SMS app
            }
        });
        heatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HeatMapActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getBaseContext(), GeoLocationService.class));
        stopService(new Intent(getBaseContext(), ShakeDetectorService.class));
        super.onDestroy();
    }

    public void startNavigation() {
        boolean isInstalled = isGoogleMapsInstalled();
        if (isInstalled == true) {
            Uri gMapsIntentUri = Uri.parse("google.navigation:q=" + GeoLocationService.getLastLocationLatitude() + "," + GeoLocationService.getLastLocationLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gMapsIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please install Google Maps");
            builder.show();

        }
    }

    public boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
