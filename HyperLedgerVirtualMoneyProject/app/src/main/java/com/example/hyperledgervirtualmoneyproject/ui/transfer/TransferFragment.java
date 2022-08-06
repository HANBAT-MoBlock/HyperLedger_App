package com.example.hyperledgervirtualmoneyproject.ui.transfer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperledgervirtualmoneyproject.API.UserTradeApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.QrCreateDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTradeResponseDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTransferDTO;
import com.example.hyperledgervirtualmoneyproject.DTO.UserTransferRequestDTO;
import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.TradeRecycler.Adapter;
import com.example.hyperledgervirtualmoneyproject.TradeRecycler.PaintTitle;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentTransferBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferFragment extends Fragment {

    EditText receiver, coin, amount;
    Button confirm, qrRead;

    private FragmentTransferBinding binding;

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
                IntentIntegrator.forSupportFragment(TransferFragment.this).initiateScan();
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

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}