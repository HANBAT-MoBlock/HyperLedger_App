package com.example.hyperledgervirtualmoneyproject.DTO;

import com.example.hyperledgervirtualmoneyproject.API.UserTradeApi;

import java.util.List;

public class UserTradeHistoryResponseDTO {

    Long totalTradeNumber;
    Long totalPage;

    List<UserTradeResponseDTO> transferResponseList;

    public Long getTotalPage() {
        return totalPage;
    }

    public Long getTotalTradeNumber() {
        return totalTradeNumber;
    }

    public List<UserTradeResponseDTO> getTransferResponseList() {
        return transferResponseList;
    }
}
