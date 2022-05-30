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
        return "자산정보 \n" +
                "학번 = " + studentId + "\n" +
                "이름 = " + owner + "\n" +
                "보유 코인 = " + coin + "\n" +
                "송신자 = " + sender + "\n" +
                "수신자 = " + receiver + "\n" +
                "전송양 = " + amount;
    }
}
