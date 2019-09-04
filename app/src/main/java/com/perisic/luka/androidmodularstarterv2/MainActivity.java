package com.perisic.luka.androidmodularstarterv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.perisic.luka.ui.SensorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_view_hello_world).setOnClickListener( v ->
                startActivityForResult(new Intent(this, SensorActivity.class), 123)
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 123) {
            if (data != null && data.hasExtra(SensorActivity.RESULT_KEY)) {
                ((TextView)findViewById(R.id.text_view_hello_world)).setText(String.valueOf(data.getIntExtra(SensorActivity.RESULT_KEY, 0)));
            }
        }
    }
}
