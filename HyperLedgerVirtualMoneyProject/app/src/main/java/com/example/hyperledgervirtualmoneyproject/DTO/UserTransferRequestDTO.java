package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserTransferRequestDTO {
    private Long receiverStudentIdOrPhoneNumber;

    private String coinName;

    private Long amount;

    public UserTransferRequestDTO(Long receiverStudentIdOrPhoneNumber, String coinName, Long amount) {
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
        this.coinName = coinName;
        this.amount = amount;
    }
}
