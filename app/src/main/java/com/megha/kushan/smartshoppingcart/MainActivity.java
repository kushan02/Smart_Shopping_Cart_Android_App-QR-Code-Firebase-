package com.megha.kushan.smartshoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import com.megha.kushan.smartshoppingcart.CartDetails;

public class MainActivity extends AppCompatActivity {

    private Button buttonScan;
    private TextView textViewName, textViewAddress;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View objects
        buttonScan = (Button) findViewById(R.id.scan_qr_code);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan a barcode");
        qrScan.setDesiredBarcodeFormats(qrScan.ALL_CODE_TYPES);
        qrScan.setCameraId(0);
        qrScan.setOrientationLocked(false);

// Replace with your own java class location here
//        qrScan.setCaptureActivity(com.share.ants.hotelmenu.CaptureActivity.class);
        qrScan.setBeepEnabled(true);

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
//                    Toast.makeText(this, "Cart has been linked with your account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CartDetails.class);
                    intent.putExtra("cart_id", result.getContents());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void scan_qr_code(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
