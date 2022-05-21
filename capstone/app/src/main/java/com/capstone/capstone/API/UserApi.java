package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserCreateBodyDTO;
import com.capstone.capstone.DTO.UserCreateDTO;
import com.capstone.capstone.DTO.UserGetAssetDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    @GET("/user")
    Call<UserGetAssetDTO> getAsset(@Header("Authorization") String Authorization);

    @POST("/user")
    Call<UserCreateDTO> join(@Body UserCreateBodyDTO userCreateBodyDTO);
}
