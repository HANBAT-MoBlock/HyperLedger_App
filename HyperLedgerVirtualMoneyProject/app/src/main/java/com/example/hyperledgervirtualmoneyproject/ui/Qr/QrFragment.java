package com.example.hyperledgervirtualmoneyproject.ui.Qr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hyperledgervirtualmoneyproject.R;
import com.example.hyperledgervirtualmoneyproject.databinding.FragmentQrBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrFragment extends Fragment{

    private FragmentQrBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QrViewModel homeViewModel =
                new ViewModelProvider(this).get(QrViewModel.class);

        binding = FragmentQrBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button qrCreateBtn = (Button) root.findViewById(R.id.QrCreate);
        EditText receiverId = (EditText) root.findViewById(R.id.QrReceiverId);
        EditText amount = (EditText) root.findViewById(R.id.QrAmount);
        ImageView qrImage = (ImageView) root.findViewById(R.id.QrCodeImage);
        EditText coin = (EditText) root.findViewById(R.id.QrCoinName);

        qrCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    String contents = "{ \"receiverId\" : " + receiverId.getText().toString()
                            +  ", \"coinName\" : " + "\"" +coin.getText().toString() + "\""
                            + ", \"amount\" : " + amount.getText().toString()
                            +  " }";
                    BitMatrix QR = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, 2000, 2000);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(QR);
                    qrImage.setImageBitmap(bitmap);
                }catch (Exception e){
                    System.out.println("Exception: " + e);
                }finally {
                    System.out.println("finally");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}