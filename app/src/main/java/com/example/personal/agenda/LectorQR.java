package com.example.personal.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


    public class LectorQR extends AppCompatActivity {

        SurfaceView cameraView;
        TextView barcodeInfo;
        BarcodeDetector barcodeDetector;
        CameraSource cameraSource;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            cameraView = (SurfaceView)findViewById(R.id.camera_view);
            barcodeInfo = (TextView)findViewById(R.id.code_info);
            barcodeDetector =
                    new BarcodeDetector.Builder(this)
                            .setBarcodeFormats(Barcode.QR_CODE)
                            .build();

            cameraSource = new CameraSource
                    .Builder(this, barcodeDetector)
                    .setRequestedPreviewSize(640, 480)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
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
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                    if (barcodes.size() != 0) {
                        barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                            public void run() {
                                //modifica el cambio de funcionalidad al leer QR

                                Barcode barcode = barcodes.valueAt(0);

                                // String textoQR = barcodes.valueAt(0).displayValue;

                                String name = barcode.contactInfo.name.formattedName;
                                String phone = barcode.contactInfo.phones[0].number;
                                String email = barcode.contactInfo.emails[0].address;
                                String address = barcode.contactInfo.addresses[0].addressLines[0];
                                barcodeInfo.setText(name+" "+phone+" "+address+" "+email); // se muestra dentro del text view

                                Contact contact = new Contact("",name,phone,"","");
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("contact",contact);

                                Intent intent = new Intent(LectorQR.this,NuevoContactoActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                finish();

                            }
                        });
                    }
                }
            });


        }


    }

