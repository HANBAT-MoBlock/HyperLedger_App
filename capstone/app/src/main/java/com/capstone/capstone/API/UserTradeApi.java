package com.capstone.capstone.API;

import com.capstone.capstone.DTO.UserTransferDTO;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserTradeApi {

    @POST("/usertrade")
    Call<UserTransferDTO> transferCoin(@Header("Authorization") String Authorization);
}
