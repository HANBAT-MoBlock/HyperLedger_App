package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserTransferRequestDTO {
    private String receiverIdentifier;

    private String coinName;

    private Long amount;

    public UserTransferRequestDTO(String receiverIdentifier, String coinName, Long amount) {
        this.receiverIdentifier = receiverIdentifier;
        this.coinName = coinName;
        this.amount = amount;
    }
}
