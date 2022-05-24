package com.capstone.capstone.DTO;

import java.time.LocalDateTime;

public class UserTradeResponseDTO {

    private Long sender;

    private Long receiver;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTradeResponseDTO{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
