package com.capstone.capstone.DTO;

import java.time.LocalDateTime;

public class UserTransferDTO {
    private Long senderStudentId;

    private Long receiverStudentIdOrPhoneNumber;

    private String coinName;

    private Long amount;

    private LocalDateTime dateCreated;

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
