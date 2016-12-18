package MovementDetection;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import Geofencing.Geofencer;

/**
 * Created by Dan on 04-12-2016.
 * Credits to anthropomo on stackoverflow for code snippets
 */

public class MovementDetector implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private CountDownTimer cTimer = null;
    private Geofencer geofencer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private static final double DETECTION_THRESHOLD = 3;
    private int movementCounter = 0;
    private Context context;

    private static final String TAG = "MovementDetector";
    private static final int COUNTDOWNTIME_IN_MILLISECONDS = 60000;

    public MovementDetector(Context context){
        this.context = context;
        geofencer = new Geofencer(context);
        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_UI);
        this.geofencer = geofencer;
        Log.d(TAG,"Accelerometer created");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            mGravity = event.values.clone();

            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];

            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if(mAccel > DETECTION_THRESHOLD){
                Log.d(TAG, "Movement detected!");
                movementCounter++;
                if (movementCounter >= 10){
                    Log.d(TAG, "User is moving!");
                    geofencer.startLocationMonitoring();

                    if (cTimer != null)
                        cTimer.cancel();
                    startTimer();

                    movementCounter = 0;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void startTimer() {
        cTimer = new CountDownTimer(COUNTDOWNTIME_IN_MILLISECONDS, 10000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Log.d(TAG, "User is idle - Shutting off geofence monitoring");
                geofencer.stopLocationMonitoring();
            }
        };
        cTimer.start();
    }
}
