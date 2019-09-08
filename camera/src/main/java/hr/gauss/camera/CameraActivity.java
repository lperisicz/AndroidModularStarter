package hr.gauss.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.io.File;

// Your IDE likely can auto-import these classes, but there are several
// different implementations so we list them here to disambiguate
//import android.Manifest
//import android.util.Size
//import android.graphics.Matrix
//import java.util.concurrent.TimeUnit



public class CameraActivity extends AppCompatActivity {

    // This is an arbitrary number we are using to keep tab of the permission
// request. Where an app has multiple context for requesting permission,
// this can help differentiate the different contexts
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    public static final String RESULT_KEY = "result";

    // This is an array of all the permission specified in the manifest
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    private TextureView viewFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        viewFinder = findViewById(R.id.texture_view_finder);
        // Every time the provided texture view changes, recompute layout

        if(checkPermissions()) {
            viewFinder.addOnLayoutChangeListener(
                    (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> updateTransform()
            );
            startCamera();
        }
    }

    private void startCamera() {
        // Create configuration object for the viewfinder use case
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        PreviewConfig previewConfig = new PreviewConfig.Builder()
            .setTargetAspectRatio(new Rational(1, 1))
            .setTargetResolution(new Size(displayMetrics.widthPixels, displayMetrics.widthPixels))
        .build();

        // Build the viewfinder use case
        Preview preview = new Preview(previewConfig);

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener(output -> {
            // To update the SurfaceTexture, we have to remove it and re-add it
            ViewGroup parent = (ViewGroup) viewFinder.getParent();
            parent.removeView(viewFinder);
            parent.addView(viewFinder, 0);

            viewFinder.setSurfaceTexture(output.getSurfaceTexture());
            updateTransform();
        });

        // Add this before CameraX.bindToLifecycle

        // Create configuration object for the image capture use case
        ImageCaptureConfig imageCaptureConfig = new  ImageCaptureConfig.Builder()
            .setTargetAspectRatio(new Rational(1, 1))
            // We don't set a resolution for image capture; instead, we
            // select a capture mode which will infer the appropriate
            // resolution based on aspect ration and requested mode
            .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY).build();

        // Build the image capture use case and attach button click listener
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);
        findViewById(R.id.button_capture).setOnClickListener(v -> {
            File file = new File(getExternalMediaDirs()[0],
                    System.currentTimeMillis() + ".jpg");
            imageCapture.takePicture(
                    file,
                    new ImageCapture.OnImageSavedListener() {
                        @Override
                        public void onImageSaved(@NonNull File file) {
                            String msg = "Photo capture succeeded: " + file.getAbsolutePath();
                            Log.d("CameraXApp", msg);
                            Intent intent = new Intent();
                            intent.putExtra(RESULT_KEY, file.getAbsolutePath());
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {

                        }
                    }
            );
        });
        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview, imageCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkPermissions()) {
                viewFinder.post(this::startCamera);
                viewFinder.addOnLayoutChangeListener(
                        (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> updateTransform()
                );
            }
        }
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();
        // Compute the center of the view finder
        float centerX = viewFinder.getWidth()/ 2f;
        float centerY = viewFinder.getHeight() / 2f;

        // Correct preview output to account for display rotation

        float rotationDegrees = 0;
        if (viewFinder.getDisplay() != null) {
            switch (viewFinder.getDisplay().getRotation()) {
                case Surface.ROTATION_0: rotationDegrees = 0; break;
                case Surface.ROTATION_90: rotationDegrees = 90; break;
                case Surface.ROTATION_180: rotationDegrees = 180; break;
                case Surface.ROTATION_270: rotationDegrees = 270; break;
            }
        } else {
            return;
        }

        matrix.postRotate(-rotationDegrees, centerX, centerY);

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix);
    }

    private boolean checkPermissions() {
        for(String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                return false;
            }
        }
        return true;
    }
}
