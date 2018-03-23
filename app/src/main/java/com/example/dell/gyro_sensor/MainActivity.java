package com.example.dell.gyro_sensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener sensorEventListener;
    TextView textView1,textView2,textView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscopeSensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textView2);
        textView3=(TextView)findViewById(R.id.textView3);

        if(gyroscopeSensor == null){
            Toast.makeText(this, "Gyroscope sensor is not available !",Toast.LENGTH_LONG).show();
            finish();
        }

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);

                    }


                else if(sensorEvent.values[2]< -0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                }

                for(int i = 0; i < 3; i++) {
                    sensorEvent.values[i] = (float)(Math.toDegrees(sensorEvent.values[i]));
                }

                textView1.setText("" +sensorEvent.values[0]);
                textView2.setText("" +sensorEvent.values[1]);
                textView3.setText("" +sensorEvent.values[2]);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

}
