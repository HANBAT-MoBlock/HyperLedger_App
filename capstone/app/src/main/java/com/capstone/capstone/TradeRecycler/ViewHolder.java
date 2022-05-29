package com.capstone.capstone.TradeRecycler;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.capstone.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView sender, receiver, coinName, amount, time;

    public  ViewHolder(View itemView){
        super(itemView);
        sender = (TextView) itemView.findViewById(R.id.tradeHistorySenderId);
        receiver = (TextView) itemView.findViewById(R.id.tradeHistoryReceiverId);
        coinName = (TextView) itemView.findViewById(R.id.tradeHistoryCoinName);
        amount = (TextView) itemView.findViewById(R.id.tradeHistoryAmount);
        time = (TextView) itemView.findViewById(R.id.tradeHistoryTime);
    }
}
