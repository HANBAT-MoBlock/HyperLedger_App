package com.capstone.capstone.UserService;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.API.UserApi;
import com.capstone.capstone.DTO.JwtToken;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class userGetAssetActivity extends AppCompatActivity {

    TextView resultText, coinName, amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergetasset);
        setTitle("유저 조회");

        resultText = (TextView) findViewById(R.id.userGetAssetResult);
        coinName = (TextView) findViewById(R.id.getAsset_coinName);
        amount = (TextView) findViewById(R.id.getAsset_amount);

        getAssetService();
    }

    public void getAssetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<UserGetAssetDTO> call = service.getAsset(JwtToken.getJwt());

        StringBuilder coinNameList = new StringBuilder();
        StringBuilder coinAmountList = new StringBuilder();

        Toast toast = Toast.makeText(getApplicationContext(), "자산 정보를 불러오는 중...", Toast.LENGTH_LONG);
        toast.show();

        call.enqueue(new Callback<UserGetAssetDTO>() {
            @Override
            public void onResponse(Call<UserGetAssetDTO> call, Response<UserGetAssetDTO> response) {
                if(response.isSuccessful()){
                    UserGetAssetDTO result = response.body();
                    resultText.setText(result.getOwner());
                    for (String key : result.getCoin().keySet()) {
                        System.out.println("key = " + key);
                        coinNameList.append(key + "\n");
                        coinAmountList.append(result.getCoin().get(key) + "\n");
                    }
                    System.out.println("coinNameList = " + coinNameList);
                    System.out.println("coinAmountList = " + coinAmountList);
                    coinName.setText(coinNameList);
                    amount.setText(coinAmountList);
                    toast.cancel();
                    Toast.makeText(getApplicationContext(), "완료", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserGetAssetDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }

}
