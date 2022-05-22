package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserTransferDTO;
import com.capstone.capstone.DTO.UserTransferRequestDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserTradeApi {

    @POST("/usertrade")
    Call<UserTransferDTO> transfer(
            @Header("Authorization") String Authorization,
            @Body UserTransferRequestDTO userTransferRequestDTO
    );
}
