package com.example.hyperledgervirtualmoneyproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserLoginDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainLoginActivity extends AppCompatActivity {

    EditText id, password;
    Button signIn, signUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsign);
        setTitle("");

        id = (EditText) findViewById(R.id.signId);
        password = (EditText) findViewById(R.id.signPassword);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLoginService(Long.parseLong(id.getText().toString()), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this, UserCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void userLoginService(Long studentId, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        Call<UserLoginDTO> call = service.login(studentId, password);
        System.out.println("studentId = " + studentId);
        System.out.println("password = " + password);

        call.enqueue(new Callback<UserLoginDTO>() {
            @Override
            public void onResponse(Call<UserLoginDTO> call, Response<UserLoginDTO> response) {
                if(response.isSuccessful()){
                    UserLoginDTO result = response.body();
                    JwtToken.setToken(result.getAccessToken());
                    JwtToken.setId(id.getText().toString());

                    Intent intent = new Intent(MainLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
                Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
