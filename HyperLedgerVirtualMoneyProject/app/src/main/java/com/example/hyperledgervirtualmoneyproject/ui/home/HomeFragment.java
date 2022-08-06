package com.example.hyperledgervirtualmoneyproject.ui.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.API.UserTradeApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserGetAssetDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTradeHistoryResponseDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTradeResponseDTO;
import com.example.hyperledgervirtualmoneyproject.LoadingDialog;
import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.TradeRecycler.Adapter;
import com.example.hyperledgervirtualmoneyproject.TradeRecycler.PaintTitle;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentHomeBinding;
import com.example.hyperledgervirtualmoneyproject.ui.transfer.userTradeActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static final String TAG = "HomeFragment";

    TextView coinName, amount;
    Button btnTradeListShow;


    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    private int page = 1;
    ArrayList<PaintTitle> myDataset = new ArrayList<>();
    LoadingDialog customProgressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        coinName = (TextView) root.findViewById(R.id.home_coinName);
        amount = (TextView) root.findViewById(R.id.home_amount);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.home_tradeList);

        getAssetService();

        populateData();
        initAdapter();

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


        btnTradeListShow = (Button) root.findViewById(R.id.home_tradeButton);
        btnTradeListShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), userTradeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getAssetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<UserGetAssetDTO> call = service.getAsset(JwtToken.getJwt());

        StringBuilder coinNameList = new StringBuilder();
        StringBuilder coinAmountList = new StringBuilder();

        Toast toast = Toast.makeText(getContext(), "자산 정보를 불러오는 중...", Toast.LENGTH_LONG);
        toast.show();

        call.enqueue(new Callback<UserGetAssetDTO>() {
            @Override
            public void onResponse(Call<UserGetAssetDTO> call, Response<UserGetAssetDTO> response) {
                if(response.isSuccessful()){
                    UserGetAssetDTO result = response.body();
                    for (String key : result.getCoin().keySet()) {
                        System.out.println("key = " + key);
                        coinNameList.append(key + "\n");
                        coinAmountList.append(result.getCoin().get(key) + "\n");
                    }
                    System.out.println("coinNameList = " + coinNameList);
                    System.out.println("coinAmountList = " + coinAmountList);
                    coinName.setText(coinNameList);
                    amount.setText(coinAmountList);
                    toast.cancel();
                    Toast.makeText(getContext(), "완료", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Toast.makeText(getContext(), "불러오기 실패", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserGetAssetDTO> call, Throwable t) {
                Toast.makeText(getContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }

    private void populateData() {
        getUserTradeHistory(page);
    }

    private void initAdapter() {
        mAdapter = new Adapter(myDataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void getUserTradeHistory(int pageInit){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserTradeApi service = retrofit.create(UserTradeApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<UserTradeHistoryResponseDTO> call = service.trade(JwtToken.getJwt(), pageInit);
        Toast loadingToast = Toast.makeText(getContext(), "기록을 불러오는 중...", Toast.LENGTH_SHORT);
        loadingToast.show();

        call.enqueue(new Callback<UserTradeHistoryResponseDTO>() {
            @Override
            public void onResponse(Call<UserTradeHistoryResponseDTO> call, Response<UserTradeHistoryResponseDTO> response) {
                if(response.isSuccessful()){
                    System.out.println(page + "------");
                    UserTradeHistoryResponseDTO result = response.body();
                    List<UserTradeResponseDTO> tradeResponseList = result.getTransferResponseList();
                    for (UserTradeResponseDTO userTradeResponseDTO : tradeResponseList) {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(userTradeResponseDTO.getDateCreated(), formatter);
                        String yyMd = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        System.out.println("JwtToken.getId() = " + JwtToken.getId());
                        System.out.println("userTradeResponseDTO = " + userTradeResponseDTO.getSenderIdentifier().toString());
                        if(JwtToken.getId().equals(userTradeResponseDTO.getSenderIdentifier().toString())){
                            System.out.println("dateTime = " + dateTime);
                            myDataset.add(new PaintTitle
                                    (
                                            userTradeResponseDTO.getReceiverIdentifier().toString(), userTradeResponseDTO.getReceiverName(),
                                            userTradeResponseDTO.getCoinName(), "-" + userTradeResponseDTO.getAmount().toString(),
                                            yyMd, dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                    )
                            );
                            System.out.println(dateTime);
                        }else{
                            myDataset.add(new PaintTitle
                                    (
                                            userTradeResponseDTO.getSenderIdentifier().toString(), userTradeResponseDTO.getSenderName(),
                                            userTradeResponseDTO.getCoinName(), userTradeResponseDTO.getAmount().toString(),
                                            yyMd, dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                    )
                            );

                        }

                    }
                    mAdapter.notifyDataSetChanged();
                    System.out.println("page: " + page);
                    loadingToast.cancel();
                    if(result.toString() == "[]"){
                        Toast.makeText(getContext(), "더 이상 기록이 없습니다.", Toast.LENGTH_SHORT).show();

                        //test용 더미 기록
                        myDataset.add(new PaintTitle
                                (
                                        "studentId", "senderName",
                                        "coin", "1000",
                                        "yyMd", "HH:MM"
                                )
                        );
                    } else{
                        Toast.makeText(getContext(), "완료", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<UserTradeHistoryResponseDTO> call, Throwable t) {
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}