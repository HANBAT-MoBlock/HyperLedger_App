package com.example.hyperledgervirtualmoneyproject.DTO;

import java.util.HashMap;

public class UserShopListDTO {

    private String address;
    private String name;
    private String phoneNumber;
    private String storeImageFileName;

    public UserShopListDTO(String address, String name, String phoneNumber, String storeImageFileName) {
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.storeImageFileName = storeImageFileName;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStoreImageFileName() {
        return storeImageFileName;
    }
}
