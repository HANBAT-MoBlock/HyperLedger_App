package com.capstone.capstone.UserService;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.API.UserApi;
import com.capstone.capstone.API.UserTradeApi;
import com.capstone.capstone.DTO.JwtToken;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.DTO.UserTradeResponseDTO;
import com.capstone.capstone.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userTradeActivity extends AppCompatActivity {

    EditText page;
    TextView resultText;
    Button confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertradehistory);

        resultText = (TextView) findViewById(R.id.userTradeResult);
        confirm = (Button) findViewById(R.id.userTradeConfirm);
        page = (EditText) findViewById(R.id.userTradePage);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserTradeHistory();
            }
        });
    }

    public void getUserTradeHistory(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserTradeApi service = retrofit.create(UserTradeApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<List<UserTradeResponseDTO>> call = service.trade(JwtToken.getJwt(), Integer.parseInt(page.getText().toString()));

        call.enqueue(new Callback<List<UserTradeResponseDTO>>() {
            @Override
            public void onResponse(Call<List<UserTradeResponseDTO>> call, Response<List<UserTradeResponseDTO>> response) {
                if(response.isSuccessful()){
                    List<UserTradeResponseDTO> result = response.body();
                    for (UserTradeResponseDTO userTradeResponseDTO : result) {
                        System.out.println("userTradeResponseDTO: " + userTradeResponseDTO.toString());
                    }
                    resultText.setText(result.toString());
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<List<UserTradeResponseDTO>> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}
