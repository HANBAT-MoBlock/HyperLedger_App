package com.example.hyperledgervirtualmoneyproject.API;

import com.example.hyperledgervirtualmoneyproject.DTO.UserGetAssetDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ShopListApi {

    @GET("/user/stores")
    Call<UserShopListResponseDTO> getStoreList(
            @Header("Authorization") String Authorization,
            @Query("page") int page);

    @GET("/user/store")
    Call<UserShopListDTO> getStore(
            @Header("Authorization") String Authorization,
            @Query("name") String name,
            @Query("phoneNumber") String phoneNumber
    );

    @GET("user/storeimage")
    Call<byte[]> getStoreImage(
            @Header("Authorization") String Authorization,
            @Query("fileName") String fileName
    );
}
