package com.example.hyperledgervirtualmoneyproject.DTO;

import java.util.HashMap;

/*
{
    "identifier": 20171601,
    "owner": "최영창",
    "coin": {},
    "sender": null,
    "receiver": null,
    "amount": null
}
 */
public class UserGetAssetDTO {
    private String identifier;

    private String owner;

    private HashMap<String, String> coin;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, String> getCoin() {
        return coin;
    }

    public void setCoin(HashMap<String, String> coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "자산정보 \n" +
                "학번 = " + identifier + "\n" +
                "이름 = " + owner + "\n" +
                "보유 코인 = " + coin;
    }
}
