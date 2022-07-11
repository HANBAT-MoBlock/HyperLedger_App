package com.example.hyperledgervirtualmoneyproject.DTO;

import java.util.Objects;


public class QrCreateDTO {
    public Long receiverId;
    public String coinName;
    public Long amount;

    public QrCreateDTO(Long receiverId, String coinName, Long amount) {
        this.receiverId = receiverId;
        this.coinName = coinName;
        this.amount = amount;
    }

    public QrCreateDTO() {
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getCoinName() {
        return coinName;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCreateDTO that = (QrCreateDTO) o;
        return Objects.equals(receiverId, that.receiverId) && Objects.equals(coinName, that.coinName) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, coinName, amount);
    }
}
