package com.capstone.capstone.UserGetAsset;

import android.icu.util.Output;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.R;

import java.io.*;
import java.net.*;
import java.net.URL;

public class userGetAsset extends AppCompatActivity {

    EditText studentId, result;
    Button confirm;
    String responseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergetasset);
        setTitle("유저 조회");

        confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String data = "{ \"studentId\" : \"" + studentId.getText().toString() + "\" }";
                    URL url = new URL("localhost:8080/user");
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestMethod("GET");
                    uc.setRequestProperty("Authorization", "test");
                    uc.setRequestProperty("Accept", "application/json");
                    uc.setRequestProperty("Content-Type", "application/json; utf-8");
                    uc.setDoInput(true);
                    uc.setDoOutput(true);
                    try (OutputStream os = uc.getOutputStream()){
                        byte request_data[] = data.getBytes("utf-8");
                        os.write(request_data);
                        os.close();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                    uc.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    while ((responseData = br.readLine()) != null){
                        sb.append(br.readLine());
                    }
                    result.setText(sb.toString());
                    uc.disconnect();
                } catch (MalformedURLException | ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
