package com.capstone.capstone.UserService;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.API.UserApi;
import com.capstone.capstone.API.UserTradeApi;
import com.capstone.capstone.DTO.JwtToken;
import com.capstone.capstone.DTO.QrCreateDTO;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.DTO.UserTransferDTO;
import com.capstone.capstone.DTO.UserTransferRequestDTO;
import com.capstone.capstone.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userTransferActivity extends AppCompatActivity {

    EditText receiver, coin, amount;
    TextView resultText;
    Button confirm, qrRead;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertransfer);
        setTitle("코인 전송");

        receiver = (EditText) findViewById(R.id.receiver);
        coin = (EditText) findViewById(R.id.coin);
        amount = (EditText) findViewById(R.id.amount);
        resultText = (TextView) findViewById(R.id.userTransferResult);
        confirm = (Button) findViewById(R.id.userTransferConfirm);
        qrRead = (Button) findViewById(R.id.userTransferReadQr);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTransferRequestDTO userTransferRequestDTO = new UserTransferRequestDTO(
                        Long.parseLong(receiver.getText().toString()),
                        coin.getText().toString(),
                        Long.parseLong(amount.getText().toString())
                );
                coinTransferService(JwtToken.getJwt(), userTransferRequestDTO);
            }
        });

        qrRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator QrCodeIntent = new IntentIntegrator(userTransferActivity.this);
                QrCodeIntent.setOrientationLocked(false);
                QrCodeIntent.setPrompt("QR 코드를 스캔해주세요");
                QrCodeIntent.initiateScan();
            }
        });

    }

    public void coinTransferService(String jwt, UserTransferRequestDTO userTransferRequestDTO){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserTradeApi service = retrofit.create(UserTradeApi.class);

        Call<UserTransferDTO> call = service.transfer(jwt, userTransferRequestDTO);

        call.enqueue(new Callback<UserTransferDTO>() {
            @Override
            public void onResponse(Call<UserTransferDTO> call, Response<UserTransferDTO> response) {
                if(response.isSuccessful()){
                    UserTransferDTO result = response.body();
                    resultText.setText(result.toString());
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserTransferDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //result.getContents 를 이용 데이터 재가공
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                System.out.println("result.getContents() = " + result.getContents());
                System.out.println("result = " + result);
                System.out.println("----------------------------------------------");
                try {
                    // JSONParser로 JSONObject로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    QrCreateDTO qrCreateDTO = objectMapper.readValue(result.getContents().toString(), QrCreateDTO.class);
                    receiver.setText(qrCreateDTO.getReceiverId().toString());
                    System.out.println("qrCreateDTO.getReceiverId() = " + qrCreateDTO.getReceiverId());
                    coin.setText(qrCreateDTO.getCoinName());
                    System.out.println("qrCreateDTO.getCoinName() = " + qrCreateDTO.getCoinName());
                    amount.setText(qrCreateDTO.getAmount().toString());
                    System.out.println("qrCreateDTO.getAmount() = " + qrCreateDTO.getAmount());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
