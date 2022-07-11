package com.example.hyperledgervirtualmoneyproject.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Paint;
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

import com.example.hyperledgervirtualmoneyproject.API.UserApi;
import com.example.hyperledgervirtualmoneyproject.DTO.JwtToken;
import com.example.hyperledgervirtualmoneyproject.DTO.UserGetAssetDTO;
import com.example.hyperledgervirtualmoneyproject.MainActivity;
import com.example.hyperledgervirtualmoneyproject.MainLoginActivity;
import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentHomeBinding;
import com.example.hyperledgervirtualmoneyproject.ui.transfer.userTradeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView coinName, amount;
    Button btnTradeListShow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        coinName = (TextView) root.findViewById(R.id.home_coinName);
        amount = (TextView) root.findViewById(R.id.home_amount);

        getAssetService();

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
}