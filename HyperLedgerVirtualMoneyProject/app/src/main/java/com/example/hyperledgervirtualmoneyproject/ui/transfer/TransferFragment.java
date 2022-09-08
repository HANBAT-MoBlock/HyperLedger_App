package com.example.hyperledgervirtualmoneyproject.ui.transfer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.API.UserTradeApi;
import com.example.hyperledgervirtualmoneyproject.DTO.ErrorBody;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.QrCreateDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserGetAssetDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTransferDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTransferRequestDTO;
import com.example.hyperledgervirtualmoneyproject.LoadingDialog;
import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentTransferBinding;
import com.example.hyperledgervirtualmoneyproject.ui.home.CoinListView.CoinData;
import com.example.hyperledgervirtualmoneyproject.ui.home.CoinListView.CoinLIstAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferFragment extends Fragment {

    TextView amountHint;
    EditText receiver, coin, amount;
    Button confirm, qrRead;
    Spinner coinSpinner;
    String[] coinList;
    HashMap<String, String> coinMap;
    String[] coinValues;


    private FragmentTransferBinding binding;
    private IntentIntegrator qrScan;
    private LoadingDialog customProgressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransferViewModel dashboardViewModel =
                new ViewModelProvider(this).get(TransferViewModel.class);

        binding = FragmentTransferBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        receiver = (EditText) root.findViewById(R.id.receiver);
        coin = (EditText) root.findViewById(R.id.coin);
        amount = (EditText) root.findViewById(R.id.amount);
        confirm = (Button) root.findViewById(R.id.userTransferConfirm);
        qrRead = (Button) root.findViewById(R.id.userTransferReadQr);
        coinSpinner = (Spinner) root.findViewById(R.id.spinner_coin);
        amountHint = (TextView) root.findViewById(R.id.amountHint);

        getAssetService();

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
                        if (customProgressDialog.isShowing()) {
                            customProgressDialog.cancel();
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 10000);
            }
        });
        thread.start();

        coinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coinMap.get(coinList[i]);
                coin.setText(coinList[i]);
                amountHint.setHint("잔액: "+ coinValues[i] + "원");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                coin.setText("코인을 선택해 주세요");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserTransferRequestDTO userTransferRequestDTO = new UserTransferRequestDTO(
                        receiver.getText().toString(),
                        coin.getText().toString(),
                        Long.parseLong(amount.getText().toString())
                );
                coinTransferService(v, JwtToken.getJwt(), userTransferRequestDTO);
            }
        });

        qrRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan = IntentIntegrator.forSupportFragment(TransferFragment.this);
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void coinTransferService(View v, String jwt, UserTransferRequestDTO userTransferRequestDTO){
        Snackbar snackbar = Snackbar.make(v, "전송중...", 10000000);
        snackbar.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserTradeApi service = retrofit.create(UserTradeApi.class);

        Call<UserTransferDTO> call = service.transfer(jwt, userTransferRequestDTO);

        call.enqueue(new Callback<UserTransferDTO>() {
            @Override
            public void onResponse(Call<UserTransferDTO> call, Response<UserTransferDTO> response) {
                if(response.isSuccessful()){
                    UserTransferDTO result = response.body();
                    snackbar.dismiss();
                    Snackbar.make(v, "전송 완료했습니다.", 2000).show();
                    Log.d(TAG, "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    snackbar.dismiss();
                    Snackbar.make(v, "전송에 살패했습니다.", 2000).show();
                    Log.d(TAG, "onResponse: 실패");
                    try {
                        String errorMessage = response.errorBody().string();
                        ObjectMapper objectMapper = new ObjectMapper();
                        ErrorBody errorBody = objectMapper.readValue(errorMessage, ErrorBody.class);
                        if (errorBody.getMessage().equals("잘못된 식별 번호로 요청했습니다")) {
                            Toast.makeText(getContext(), "다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserTransferDTO> call, Throwable t) {
                snackbar.dismiss();
                Snackbar.make(v, "서버 연결에 실패했습니다.", 2000).show();
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //result.getContents 를 이용 데이터 재가공
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                System.out.println("result.getContents() = " + result.getContents());
                System.out.println("result = " + result);
                System.out.println("----------------------------------------------");
                try {
                    // JSONParser로 JSONObject로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    QrCreateDTO qrCreateDTO = objectMapper.readValue(result.getContents().toString(), QrCreateDTO.class);
                    receiver.setText(qrCreateDTO.getReceiverId().toString());
                    System.out.println("qrCreateDTO.getReceiverId() = " + qrCreateDTO.getReceiverId());
                    coin.setText(qrCreateDTO.getCoinName());
                    System.out.println("qrCreateDTO.getCoinName() = " + qrCreateDTO.getCoinName());
                    amount.setText(qrCreateDTO.getAmount().toString());
                    System.out.println("qrCreateDTO.getAmount() = " + qrCreateDTO.getAmount());

                    for (int i = 0; i < coinList.length; i++) {
                        if (coinList[i].equals(coin)) {
                            coinSpinner.setSelection(i);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getAssetService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.localhost))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);

        System.out.println("jwtToken = " + JwtToken.getJwt());
        Call<UserGetAssetDTO> call = service.getAsset(JwtToken.getJwt());

        Toast toast = Toast.makeText(getContext(), "자산 정보를 불러오는 중...", Toast.LENGTH_LONG);
        toast.show();

        call.enqueue(new Callback<UserGetAssetDTO>() {
            @Override
            public void onResponse(Call<UserGetAssetDTO> call, Response<UserGetAssetDTO> response) {
                if(response.isSuccessful()){
                    UserGetAssetDTO result = response.body();
                    coinMap = result.getCoin();
                    coinList = coinMap.keySet().toArray(new String[coinMap.size()]);
                    coinValues = coinMap.values().toArray(new String[coinMap.size()]);

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, coinList);
                    coinSpinner.setAdapter(spinnerAdapter);

                    customProgressDialog.cancel();
                }else{
                    Toast.makeText(getContext(), "불러오기 실패", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: 실패");
                    try {
                        String errorMessage = response.errorBody().string();
                        ObjectMapper objectMapper = new ObjectMapper();
                        ErrorBody errorBody = objectMapper.readValue(errorMessage, ErrorBody.class);
                        if (errorBody.getMessage().equals("잘못된 식별 번호로 요청했습니다")) {
                            Toast.makeText(getContext(), "다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserGetAssetDTO> call, Throwable t) {
                Toast.makeText(getContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onFailure" + t.getMessage());
            }
        });
    }
}