package com.capstone.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.UserService.userCreateActivity;
import com.capstone.capstone.UserService.userGetAssetActivity;
import com.capstone.capstone.UserService.userTradeActivity;
import com.capstone.capstone.UserService.userTransferActivity;

public class MainActivity extends AppCompatActivity {

    Button btnUserGetAsset, btnUserCreate, btnUserTradeHistory, btnUserTransfer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인화면");

        btnUserGetAsset = (Button) findViewById(R.id.btnUserGetAsset);
        btnUserCreate = (Button) findViewById(R.id.btnUserCreate);
        btnUserTransfer = (Button) findViewById(R.id.btnUserTransfer);
        btnUserTradeHistory = (Button) findViewById(R.id.btnUserTradeHistory);

        btnUserGetAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userGetAssetActivity.class);
                startActivity(intent);
            }
        });

        btnUserTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userTransferActivity.class);
                startActivity(intent);
            }
        });

        btnUserCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userCreateActivity.class);
                startActivity(intent);
            }
        });

        btnUserTradeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userTradeActivity.class);
                startActivity(intent);
            }
        });

    }
}
