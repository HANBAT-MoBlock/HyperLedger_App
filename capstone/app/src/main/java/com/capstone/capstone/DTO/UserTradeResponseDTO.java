package com.capstone.capstone.DTO;

import java.time.LocalDateTime;

public class UserTradeResponseDTO {

    private Long senderStudentId;

    private Long receiverStudentIdOrPhoneNumber;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTradeResponseDTO{" +
                "sender=" + senderStudentId +
                ", receiver=" + receiverStudentIdOrPhoneNumber +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }

    public Long getSender() {
        return senderStudentId;
    }

    public Long getReceiver() {
        return receiverStudentIdOrPhoneNumber;
    }

    public String getCoinName() {
        return coinName;
    }

    public Long getAmount() {
        return amount;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
