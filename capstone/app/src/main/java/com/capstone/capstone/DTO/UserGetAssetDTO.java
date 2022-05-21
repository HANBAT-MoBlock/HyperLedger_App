package com.capstone.capstone.DTO;

import java.util.HashMap;

/*
{
    "studentId": 20171601,
    "owner": "최영창",
    "coin": {},
    "sender": null,
    "receiver": null,
    "amount": null
}
 */
public class UserGetAssetDTO {
    private Long studentId;

    private String owner;

    private HashMap<String, String> coin;

    private String sender;

    private String receiver;

    private Long amount;

    @Override
    public String toString() {
        return "UserGetAssetDTO{" +
                "studentId=" + studentId +
                ", owner='" + owner + '\'' +
                ", coin=" + coin +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                '}';
    }
}
