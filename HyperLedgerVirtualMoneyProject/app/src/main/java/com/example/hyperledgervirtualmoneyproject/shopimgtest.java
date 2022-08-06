package com.example.hyperledgervirtualmoneyproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperledgervirtualmoneyproject.API.ShopListApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListResponseDTO;
import com.example.hyperledgervirtualmoneyproject.ShopListRecycler.ShopListPaintTitle;

import java.io.ByteArrayInputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class shopimgtest extends AppCompatActivity {


    private static final String TAG = "test";
    ImageView test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        test = (ImageView) findViewById(R.id.testImg);


    }

    public void getImg(int pageInit){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopListApi service = retrofit.create(ShopListApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<byte[]> call = service.getStoreImage(JwtToken.getJwt(), "46b8b791-0329-4f32-a85c-13f09f293261.PNG");


        call.enqueue(new Callback<byte[]>() {
            @Override
            public void onResponse(Call<byte[]> call, Response<byte[]> response) {
                if(response.isSuccessful()){

                    byte[] byteArray = response.body();

                    Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length );
                    test.setImageBitmap(bitmap);

                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<byte[]> call, Throwable t) {
                Log.d(TAG, "onFailure" + t.getMessage());
            }
        });
    }

}
