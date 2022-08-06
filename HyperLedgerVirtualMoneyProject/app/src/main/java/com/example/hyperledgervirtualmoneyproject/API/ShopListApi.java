package com.example.hyperledgervirtualmoneyproject.API;

import com.example.hyperledgervirtualmoneyproject.DTO.UserGetAssetDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ShopListApi {

    @GET("/user/stores")
    Call<List<UserShopListDTO>> getStoreList(
            @Header("Authorization") String Authorization,
            @Query("page") int page);
}
