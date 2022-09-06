package com.example.hyperledgervirtualmoneyproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserCreateBodyDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserLoginDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCreateActivity extends AppCompatActivity {

    EditText studentId, password, name;
    Button confirm, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercreate);

        studentId = (EditText) findViewById(R.id.studentId);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        confirm = (Button) findViewById(R.id.userCreateConfirm);
        cancel = (Button) findViewById(R.id.userCreateCancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = UserCreateActivity.this.password.getText().toString();
                //최소 하나의 숫자와 문자, 최소 8글자 이상 입력
                Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
                if(pattern.matcher(password).find()){
                    createUser(studentId.getText().toString(), password, name.getText().toString(), "ROLE_STUDENT");
                }else {
                    Toast.makeText(getApplicationContext(), "비밀번호 형식에 맞춰주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    JwtToken.setToken(result.getAccessToken());
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserLoginDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 연결에  실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}
