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
import com.capstone.capstone.DTO.UserCreateBodyDTO;
import com.capstone.capstone.DTO.UserCreateDTO;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class userCreateActivity extends AppCompatActivity {

    EditText studentId, password, name;
    TextView resultText;
    Button confirm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercreate);

        studentId = (EditText) findViewById(R.id.studentId);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        confirm = (Button) findViewById(R.id.userCreateConfirm);
        resultText = (TextView) findViewById(R.id.userCreateResult);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(studentId.getText().toString(), password.getText().toString(), name.getText().toString());
            }
        });

    }

    public void createUser(String studentId, String password, String name){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.110:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        UserCreateBodyDTO userCreateBodyDTO = new UserCreateBodyDTO(studentId, password, name);

        Call<UserCreateDTO> call = service.join(userCreateBodyDTO);

        call.enqueue(new Callback<UserCreateDTO>() {
            @Override
            public void onResponse(Call<UserCreateDTO> call, Response<UserCreateDTO> response) {
                if(response.isSuccessful()){
                    System.out.println("response.body(); = " + response.body().toString());
                    UserCreateDTO result = response.body();
                    resultText.setText(result.toString());
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserCreateDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}
