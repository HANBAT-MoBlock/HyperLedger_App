package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserGetAssetDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserGetAssetApi {
    @GET("/user")
    Call<UserGetAssetDTO> getAsset(@Query("Authorization") String Au);
}
