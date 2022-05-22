package com.capstone.capstone.UserService;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.API.UserApi;
import com.capstone.capstone.API.UserTradeApi;
import com.capstone.capstone.DTO.JwtToken;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.DTO.UserTransferDTO;
import com.capstone.capstone.DTO.UserTransferRequestDTO;
import com.capstone.capstone.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userTransferActivity extends AppCompatActivity {

    EditText receiver, coin, amount;
    TextView resultText;
    Button confirm;

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
}
