package com.example.hyperledgervirtualmoneyproject.ui.shopList;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperledgervirtualmoneyproject.API.ShopListApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserShopListResponseDTO;
import com.example.hyperledgervirtualmoneyproject.LoadingDialog;
import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.ShopListRecycler.ShopListAdapter;
import com.example.hyperledgervirtualmoneyproject.ShopListRecycler.ShopListPaintTitle;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentShoplistBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopListFragment extends Fragment {

    private static final String TAG = "ShopListFragment";

    private FragmentShoplistBinding binding;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    private int page = 1;
    boolean isLoading = false;
    LoadingDialog customProgressDialog;

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

        customProgressDialog = new LoadingDialog(getContext());
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        customProgressDialog.cancel();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 3000);
            }
        });
        thread.start();

        return root;
    }

    private void populateData() {
        getShopList(page);
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
                //LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

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
                getShopList(page);
                isLoading = false;
            }
        }, 2000);
    }

    public void getShopList(int pageInit){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ShopListApi service = retrofit.create(ShopListApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<UserShopListResponseDTO> call = service.getStoreList(JwtToken.getJwt(), pageInit);

        Toast loadingToast = Toast.makeText(getContext(), "가맹점 리스트를 불러오는 중...", Toast.LENGTH_SHORT);
        loadingToast.show();

        call.enqueue(new Callback<UserShopListResponseDTO>() {
            @Override
            public void onResponse(Call<UserShopListResponseDTO> call, Response<UserShopListResponseDTO> response) {
                if(response.isSuccessful()){
                    System.out.println(page + "------");
                    if(page > 1){
                        myDataset.remove(myDataset.size() - 1);
                        mAdapter.notifyItemRemoved(myDataset.size());
                    }
                    UserShopListResponseDTO result = response.body();
                    List<UserShopListDTO> storeResponseList = result.getStoreResponseList();
                    for (UserShopListDTO userShopListDTO : storeResponseList) {

                        System.out.println("JwtToken.getId() = " + JwtToken.getId());
                        System.out.println("userShopListDTO = " + userShopListDTO.getName());
                        myDataset.add(new ShopListPaintTitle
                                (
                                        userShopListDTO.getName(), userShopListDTO.getPhoneNumber(), userShopListDTO.getAddress()
                                )
                        );
                    }
                    mAdapter.notifyDataSetChanged();
                    page++;
                    System.out.println("page: " + page);
                    loadingToast.cancel();
                    if(result.toString() == "[]"){
                        Toast.makeText(getContext(), "더 이상 기록이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "완료", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserShopListResponseDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}