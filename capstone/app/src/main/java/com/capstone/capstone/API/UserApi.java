package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserCreateBodyDTO;
import com.capstone.capstone.DTO.UserLoginDTO;
import com.capstone.capstone.DTO.UserGetAssetDTO;
import com.capstone.capstone.DTO.UserLoginRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/user")
    Call<UserGetAssetDTO> getAsset(@Header("Authorization") String Authorization);

    @POST("/user")
    Call<UserLoginDTO> join(@Body UserCreateBodyDTO userCreateBodyDTO);

    @GET("/login")
    Call<UserLoginDTO> login(
            @Query("studentId") Long studentId,
            @Query("password") String Password
    );
}
