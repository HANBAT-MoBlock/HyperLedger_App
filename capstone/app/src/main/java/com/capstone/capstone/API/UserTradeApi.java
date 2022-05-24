package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserTradeResponseDTO;
import com.capstone.capstone.DTO.UserTransferDTO;
import com.capstone.capstone.DTO.UserTransferRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserTradeApi {

    @POST("/trade")
    Call<UserTransferDTO> transfer(
            @Header("Authorization") String Authorization,
            @Query("page") UserTransferRequestDTO userTransferRequestDTO
    );

    @GET("/trade")
    Call<List<UserTradeResponseDTO>>trade(
            @Header("Authorization") String Authorization,
            @Query("page") int page
    );
}
