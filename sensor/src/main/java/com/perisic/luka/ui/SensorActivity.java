package com.perisic.luka.ui;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.perisic.luka.R;

import java.util.Locale;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    public static final String RESULT_KEY = "result";

    private Sensor lightSensor;
    private SensorManager sensorManager;
    private DecibelMeterView decibelMeterView;
    private TextView textViewLuxValue;
    private float value = 0f;
    private int luxValue = 0;
    private Handler handler;
    private final Runnable meterViewUpdater = new Runnable() {
        @Override
        public void run() {
            try {
                decibelMeterView.addLine(value);
            } finally {
                handler.postDelayed(meterViewUpdater, 50);
            }
        }
    };

    //region LIFECYCLE_EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        decibelMeterView = findViewById(R.id.decibel_meter_view);
        textViewLuxValue = findViewById(R.id.text_view_lux_value);

        findViewById(R.id.button_detect).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(RESULT_KEY, luxValue);
            setResult(RESULT_OK, intent);
            finish();
        });

        handler = new Handler();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        startRepeatingTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        stopRepeatingTask();
    }
    //endregion

    //region HELPER_METHODS

    void startRepeatingTask() {
        meterViewUpdater.run();
    }

    void stopRepeatingTask() {
        handler.removeCallbacks(meterViewUpdater);
    }

    //endregion

    //region IMPLEMENTED_METHODS
    @Override
    public void onSensorChanged(SensorEvent event) {
        luxValue = (int)event.values[0];
        value = event.values[0] > 600 ? 1 : event.values[0] / 600;
        handler.post(() -> textViewLuxValue.setText(String.format(Locale.getDefault(), "%d lx", (int) event.values[0])));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //endregion

}
