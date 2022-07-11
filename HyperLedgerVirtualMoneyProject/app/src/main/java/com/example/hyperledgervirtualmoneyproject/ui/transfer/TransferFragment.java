package com.example.hyperledgervirtualmoneyproject.ui.transfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hyperledgervirtualmoneyproject.databinding.FragmentTransferBinding;

public class TransferFragment extends Fragment {

    private FragmentTransferBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransferViewModel dashboardViewModel =
                new ViewModelProvider(this).get(TransferViewModel.class);

        binding = FragmentTransferBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}