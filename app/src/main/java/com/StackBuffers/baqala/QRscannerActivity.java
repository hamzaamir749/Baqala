package com.StackBuffers.baqala;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class QRscannerActivity extends AppCompatActivity {

    SurfaceView cameraView;
    //TextView txtResult;
    EditText pname;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        //Bind XML view
        cameraView = findViewById(R.id.cameraPreview);
        //txtResult = findViewById(R.id.showResult);
       /* pname = findViewById(R.id.checkin_product_name);*/

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480).build();
        //Now add event to show camera preview

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //Check permissions here first

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QRscannerActivity.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    //If permission Granted then start Camera
                    cameraSource.start(cameraView.getHolder());
                } catch (Exception e) {
                    Toast.makeText(QRscannerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> sparseArray = detections.getDetectedItems();
                if (sparseArray.size() != 0) {
                   pname .post(new Runnable() {
                        @Override
                        public void run() {
                            //Here i am creating vibration so when we scan any barcode its vibrate
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            assert vibrator != null;
                            vibrator.vibrate(100);//here give the time in MilliSeconds
                            pname.setText(sparseArray.valueAt(0).displayValue);

                        }
                    });
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        //If permission Granted then start Camera
                        cameraSource.start(cameraView.getHolder());
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}
