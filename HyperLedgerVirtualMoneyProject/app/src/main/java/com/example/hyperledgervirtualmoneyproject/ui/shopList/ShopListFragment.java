package com.example.hyperledgervirtualmoneyproject.ui.shopList;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.ShopListRecycler.ShopListAdapter;
import com.example.hyperledgervirtualmoneyproject.ShopListRecycler.ShopListPaintTitle;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentShoplistBinding;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private FragmentShoplistBinding binding;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    private int page = 1;
    boolean isLoading = false;

    ArrayList<ShopListPaintTitle> myDataset = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopListViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = (RecyclerView) root.findViewById(R.id.shopList_recycler);
        populateData();
        initAdapter();
        initScrollListener();

        myDataset.add(new ShopListPaintTitle
                (
                        "가게", "전화번호",
                        "주소"
                )
        );
        myDataset.add(new ShopListPaintTitle
                (
                        "가게", "전화번호",
                        "주소"
                )
        );

        return root;
    }

    private void populateData() {
        //getUserTradeHistory(page);
    }

    private void initAdapter() {
        mAdapter = new ShopListAdapter(myDataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initScrollListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(!isLoading) {
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == myDataset.size() - 1){
                        loadMore();
                        isLoading = true;
                    }

                }
            }
        });
    }

    private void loadMore(){
        myDataset.add(null);
        mAdapter.notifyItemInserted(myDataset.size() - 1);

        System.out.println("myDataset.size()1 = " + (myDataset.size() - 1));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("myDataset.size()2 = " + (myDataset.size() - 1));

                //getUserTradeHistory(page);
                isLoading = false;
            }
        }, 2000);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}