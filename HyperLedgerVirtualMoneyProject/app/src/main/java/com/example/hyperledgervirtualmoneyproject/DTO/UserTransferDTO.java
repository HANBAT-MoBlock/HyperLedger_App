package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserTransferDTO {

    private Long senderIdentifier;

    private String senderName;

    private Long receiverIdentifier;

    private String receiverName;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTransferDTO{" +
                "senderStudentId=" + senderName +
                ", receiverStudentIdOrPhoneNumber=" + receiverIdentifier +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
