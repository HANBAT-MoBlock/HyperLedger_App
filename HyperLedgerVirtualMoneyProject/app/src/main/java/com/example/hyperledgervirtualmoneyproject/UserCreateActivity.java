package com.example.hyperledgervirtualmoneyproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserCreateBodyDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserLoginDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCreateActivity extends AppCompatActivity {

    EditText studentId, password, name;
    TextView resultText;
    Button confirm;
    CheckBox user, shop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercreate);

        studentId = (EditText) findViewById(R.id.studentId);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        confirm = (Button) findViewById(R.id.userCreateConfirm);
        resultText = (TextView) findViewById(R.id.userCreateResult);
        user = (CheckBox) findViewById(R.id.userCreateRoleCheckUser);
        shop = (CheckBox) findViewById(R.id.userCreateRoleCheckShop);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shop.setChecked(false);
                user.setChecked(true);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shop.setChecked(true);
                user.setChecked(false);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shop.isChecked()) {
                    createUser(studentId.getText().toString(), password.getText().toString(), name.getText().toString(), "ROLE_SHOP");
                } else if(user.isChecked()){
                    createUser(studentId.getText().toString(), password.getText().toString(), name.getText().toString(), "ROLE_STUDENT");
                }
            }
        });

    }

    public void createUser(String identifier, String password, String name, String role){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        UserCreateBodyDTO userCreateBodyDTO = new UserCreateBodyDTO(identifier, password, name, role);

        Call<UserLoginDTO> call = service.join(userCreateBodyDTO);

        call.enqueue(new Callback<UserLoginDTO>() {
            @Override
            public void onResponse(Call<UserLoginDTO> call, Response<UserLoginDTO> response) {
                if(response.isSuccessful()){
                    System.out.println("response.body(); = " + response.body().toString());
                    UserLoginDTO result = response.body();
                    resultText.setText("회원가입에 성공했습니다.");
                    JwtToken.setToken(result.getAccessToken());
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                    finish();
                }else{
                    resultText.setText("회원가입에 실패했습니다.");
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                resultText.setText("서버 연결에 실패했습니다.");
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}
