package com.example.martinlamby.parking;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ShakeDetectorService extends Service {

    private static final int SHAKE_INTERVAL = 1000;
    private CountDownTimer shakeIntervalSeperaterTimer;
    private boolean shakeAlreadyHappened;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            float xAcc = event.values[0];
            float yAcc = event.values[1];
            float zAcc = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (xAcc * xAcc + yAcc * yAcc + zAcc * zAcc));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * (float) 0.9 + delta;

            shakeSeperationTimer();

            if (shakeAlreadyHappened == false && mAccel > 12) {
                    onShakeEvent();
            }

        }

        public void onShakeEvent(){
            shakeIntervalSeperaterTimer.start();
            shakeAlreadyHappened = true;

            //get Location (will be replaced with Data from GeoLocationService (not existend to this date 13.09.2015)
            //System.out.println("SHAKE DETECTED");

            saveParkedCarPositionToParse(GeoLocationService.getLastLocationLatitude(),GeoLocationService.getLastLocationLongitude());
            Toast toast = Toast.makeText(getApplicationContext(), "Position saved", Toast.LENGTH_SHORT);
            toast.show();
        }

        public void shakeSeperationTimer(){
            shakeIntervalSeperaterTimer = new CountDownTimer(SHAKE_INTERVAL, SHAKE_INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //not needed
                }

                @Override
                public void onFinish() {
                    //System.out.println("Timer finished");
                    shakeAlreadyHappened = false;
                }
            };
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //not needed
        }
    };



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        mAccel = (float) 0.00;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("ShakeDetectorService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("ShakeDetectorService stopped");
        super.onDestroy();
    }

    public void saveParkedCarPositionToParse(double latitude, double longitude) {
        ParseObject parkedCarPosition = new ParseObject("ParkedCarPosition");
        parkedCarPosition.add("latitude",String.valueOf(latitude));
        parkedCarPosition.add("longitude", String.valueOf(longitude));
        parkedCarPosition.add("username", ParseUser.getCurrentUser().getUsername().toString());
        parkedCarPosition.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //System.out.println("Parked Car Position has been saved successfully");
                } else {
                    System.out.println("Failed to saved Position of parked Car");
                }
            }
        });
    }

}
