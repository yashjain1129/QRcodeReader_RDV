package com.barcode.mvp.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.barcode.mvp.ui.barcodeCapture.BarCodeCaptureActivity;
import com.example.akshay.barcodereader.R;

public class MainActivity extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;

    private TextView name;
    private TextView college;
    Button scan;
    CheckBox autoFocus;
    CheckBox flash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        college = findViewById(R.id.college);
        scan = findViewById(R.id.scan);
        autoFocus =findViewById(R.id.autoFocus);
        flash = findViewById(R.id.flash);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarCodeCaptureActivity.class);
                intent.putExtra(BarCodeCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(BarCodeCaptureActivity.UseFlash,flash.isChecked());
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
    }

}
