package com.example.hyperledgervirtualmoneyproject.DTO;

public class UserTradeResponseDTO {

    private Long senderIdentifier;

    private String senderName;

    private Long receiverIdentifier;

    private String receiverName;

    private String coinName;

    private Long amount;

    private String dateCreated;

    @Override
    public String toString() {
        return "UserTradeResponseDTO{" +
                "senderStudentId=" + senderIdentifier +
                ", receiverStudentIdOrPhoneNumber=" + receiverIdentifier +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", coinName='" + coinName + '\'' +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public Long getSenderIdentifier() {
        return senderIdentifier;
    }

    public void setSenderIdentifier(Long senderIdentifier) {
        this.senderIdentifier = senderIdentifier;
    }

    public Long getReceiverIdentifier() {
        return receiverIdentifier;
    }

    public void setReceiverIdentifier(Long receiverIdentifier) {
        this.receiverIdentifier = receiverIdentifier;
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
