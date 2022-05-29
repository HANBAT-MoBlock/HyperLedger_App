package com.capstone.capstone.TradeRecycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.capstone.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private ArrayList<PaintTitle> mDataset;

    public  Adapter(ArrayList<PaintTitle> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if(viewType == VIEW_TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_tradehistory, parent, false);
            return new ItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof ItemViewHolder){
            populateItemRows((ItemViewHolder) viewHolder, position);
        }else if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    public  int getItemCount(){
        return mDataset == null ? 0 : mDataset.size();
    }

    @Override
    public int getItemViewType(int position){
        return mDataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView sender, receiver, coinName, amount, time;

        public  ItemViewHolder(@NonNull View itemView){
            super(itemView);
            sender = itemView.findViewById(R.id.tradeHistorySenderId);
            receiver = itemView.findViewById(R.id.tradeHistoryReceiverId);
            coinName = itemView.findViewById(R.id.tradeHistoryCoinName);
            amount = itemView.findViewById(R.id.tradeHistoryAmount);
            time = itemView.findViewById(R.id.tradeHistoryTime);
        }

    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position){}

    private void populateItemRows(ItemViewHolder viewHolder, int position){
        PaintTitle item = mDataset.get(position);
        viewHolder.sender.setText(item.sender);
        viewHolder.receiver.setText(item.receiver);
        viewHolder.coinName.setText(item.coinName);
        viewHolder.amount.setText(item.amount);
        viewHolder.time.setText(item.time);
    }
}
