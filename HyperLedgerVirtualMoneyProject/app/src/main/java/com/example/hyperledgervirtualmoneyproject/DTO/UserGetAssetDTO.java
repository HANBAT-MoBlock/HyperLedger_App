package com.example.hyperledgervirtualmoneyproject.DTO;

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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    private Long amount;

    @Override
    public String toString() {
        return "자산정보 \n" +
                "학번 = " + studentId + "\n" +
                "이름 = " + owner + "\n" +
                "보유 코인 = " + coin;
    }
}
