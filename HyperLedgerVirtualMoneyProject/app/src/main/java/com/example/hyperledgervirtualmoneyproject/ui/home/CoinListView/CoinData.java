package com.example.hyperledgervirtualmoneyproject.ui.home.CoinListView;

public class CoinData {
    private String coinName;
    private String coinValue;

    public CoinData(String coinName, String coinValue){
        this.coinName = coinName;
        this.coinValue = coinValue;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(String coinValue) {
        this.coinValue = coinValue;
    }
}