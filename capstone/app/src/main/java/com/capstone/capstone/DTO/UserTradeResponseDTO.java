package com.capstone.capstone.DTO;

import java.time.LocalDateTime;

public class UserTradeResponseDTO {

    private Long senderStudentId;

    private String senderName;

    private Long receiverStudentIdOrPhoneNumber;

    private String receiverName;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTradeResponseDTO{" +
                "senderStudentId=" + senderStudentId +
                ", receiverStudentIdOrPhoneNumber=" + receiverStudentIdOrPhoneNumber +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public Long getSenderStudentId() {
        return senderStudentId;
    }

    public void setSenderStudentId(Long senderStudentId) {
        this.senderStudentId = senderStudentId;
    }

    public Long getReceiverStudentIdOrPhoneNumber() {
        return receiverStudentIdOrPhoneNumber;
    }

    public void setReceiverStudentIdOrPhoneNumber(Long receiverStudentIdOrPhoneNumber) {
        this.receiverStudentIdOrPhoneNumber = receiverStudentIdOrPhoneNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
