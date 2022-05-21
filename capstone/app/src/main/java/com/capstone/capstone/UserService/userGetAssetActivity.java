package com.capstone.capstone.UserService;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.capstone.API.UserApi;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class userGetAssetActivity extends AppCompatActivity {

    EditText auField, resultText;
    Button confirm;
    String Au = getString(R.string.au);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergetasset);
        setTitle("유저 조회");

        auField = (EditText) findViewById(R.id.studentId);
        resultText = (EditText) findViewById(R.id.result);
        confirm = (Button) findViewById(R.id.confirm);

        auField.setText(Au);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAssetService();
            }
        });
    }

    public void getAssetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.110:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        Call<UserGetAssetDTO> call = service.getAsset(Au);

        call.enqueue(new Callback<UserGetAssetDTO>() {
            @Override
            public void onResponse(Call<UserGetAssetDTO> call, Response<UserGetAssetDTO> response) {
                if(response.isSuccessful()){
                    UserGetAssetDTO result = response.body();
                    resultText.setText(result.toString());
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
