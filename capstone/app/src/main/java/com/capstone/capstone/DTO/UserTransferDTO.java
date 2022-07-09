package com.capstone.capstone.DTO;

import java.time.LocalDateTime;

public class UserTransferDTO {

    private Long senderStudentId;

    private String senderName;

    private Long receiverStudentIdOrPhoneNumber;

    private String receiverName;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTransferDTO{" +
                "senderStudentId=" + senderStudentId +
                ", receiverStudentIdOrPhoneNumber=" + receiverStudentIdOrPhoneNumber +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
