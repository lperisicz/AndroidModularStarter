package com.perisic.luka.androidmodularstarterv2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hr.gauss.camera.CameraActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_view_hello_world).setOnClickListener(v ->
                startActivityForResult(new Intent(this, CameraActivity.class), 123)
//                        startActivity(new Intent(this, CameraActivity.class))
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123) {
            if (data != null && data.hasExtra(CameraActivity.RESULT_KEY)) {
                ((TextView)findViewById(R.id.text_view_hello_world)).setText(data.getStringExtra(CameraActivity.RESULT_KEY));
            }
        }
    }
}
