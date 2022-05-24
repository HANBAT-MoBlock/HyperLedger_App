package com.capstone.capstone.UserService;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class userQrCreateActivity extends AppCompatActivity {

    Button btnQrCreate;
    EditText receiverId, amount, coin;
    ImageView qrImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreate);

        btnQrCreate = (Button) findViewById(R.id.QrCreate);
        receiverId = (EditText) findViewById(R.id.QrReceiverId);
        amount = (EditText) findViewById(R.id.QrAmount);
        qrImage = (ImageView) findViewById(R.id.QrCodeImage);
        coin = (EditText) findViewById(R.id.QrCoinName);

        btnQrCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    String contents = "{ \"receiverId\" : " + receiverId.getText().toString()
                            +  ", \"coinName\" : " + "\"" +coin.getText().toString() + "\""
                            + ", \"amount\" : " + amount.getText().toString()
                            +  " }";
                    BitMatrix QR = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, 2000, 2000);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(QR);
                    qrImage.setImageBitmap(bitmap);
                }catch (Exception e){
                    System.out.println("Exception: " + e);
                }finally {
                    System.out.println("finally");
                }
            }
        });
    }
}
