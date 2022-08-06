package com.example.hyperledgervirtualmoneyproject.DTO;

import java.util.List;

public class UserShopListResponseDTO {

    List<UserShopListDTO> storeResponseList;
    Long totalPage;
    Long totalUserNumber;

    public List<UserShopListDTO> getStoreResponseList() {
        return storeResponseList;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public Long getTotalUserNumber() {
        return totalUserNumber;
    }
}
