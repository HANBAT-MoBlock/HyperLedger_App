package com.capstone.capstone.DTO;

public class UserTransferRequestDTO {
    private Long studentId;

    private String coinName;

    private Long amount;

    public UserTransferRequestDTO(Long studentId, String coinName, Long amount) {
        this.studentId = studentId;
        this.coinName = coinName;
        this.amount = amount;
    }
}
