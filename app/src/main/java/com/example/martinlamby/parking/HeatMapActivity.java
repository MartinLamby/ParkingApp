package com.example.martinlamby.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HeatMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);


        Button privateHeatmap = (Button) findViewById(R.id.privateHeatMapButton);
        Button publicHeatmap = (Button) findViewById(R.id.publicHeatMapButton);

        publicHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), publicHeatMapActivity.class));
            }
        });


        privateHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), privateHeatMapActivity.class));
            }
        });

    }
}
