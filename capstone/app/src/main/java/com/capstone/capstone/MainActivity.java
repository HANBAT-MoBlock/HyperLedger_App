package com.capstone.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.UserGetAsset.userGetAsset;

public class MainActivity extends AppCompatActivity {

    Button btnUserGetAsset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인화면");

        btnUserGetAsset = (Button) findViewById(R.id.btnUserGetAsset);

        btnUserGetAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userGetAsset.class);
                startActivity(intent);
            }
        });

    }
}
