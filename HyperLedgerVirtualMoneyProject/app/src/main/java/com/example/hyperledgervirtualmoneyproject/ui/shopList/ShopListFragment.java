package com.example.hyperledgervirtualmoneyproject.ui.shopList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hyperledgervirtualmoneyproject.databinding.FragmentShoplistBinding;

public class ShopListFragment extends Fragment {

    private FragmentShoplistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopListViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}