package com.barcode.mvp.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.barcode.mvp.ui.barcodeCapture.BarCodeCaptureActivity;
import com.example.akshay.barcodereader.R;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final int RC_BARCODE_CAPTURE = 9001;
    //    public String TAG="asgdhajd";
//    private java.net.URL URL = http://10.194.2.124:3000/";
    private String url;
    private TextView name;
    private TextView college;
    private RequestQueue queue;
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
        autoFocus = findViewById(R.id.autoFocus);
        flash = findViewById(R.id.flash);
        queue = Volley.newRequestQueue(this);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setVisibility(View.INVISIBLE);
                college.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(MainActivity.this, BarCodeCaptureActivity.class);
                intent.putExtra(BarCodeCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(BarCodeCaptureActivity.UseFlash, flash.isChecked());
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (data != null) {
                Barcode barcode = data.getParcelableExtra(BarCodeCaptureActivity.BarcodeObject);
                initialiseRequest(barcode.displayValue);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initialiseRequest(String barcodeValue) {
        url = "http:/10.184.27.25:3000/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(MainActivity.this, "hi this api up", Toast.LENGTH_SHORT).show();
                        try {
                            if(response.getBoolean("success")){
                            try {
                                name.setVisibility(View.VISIBLE);
                                college.setVisibility(View.VISIBLE);
                                name.setText(response.getString("name"));
                                college.setText(response.getString("college"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

}


