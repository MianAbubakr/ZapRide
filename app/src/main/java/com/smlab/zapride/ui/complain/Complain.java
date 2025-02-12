package com.smlab.zapride.ui.complain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityComplainBinding;

public class Complain extends AppCompatActivity {
    ActivityComplainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityComplainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
    }

    private void setListener() {
        binding.submitComplainButton.setOnClickListener(view -> {
            showCustomDialog();
        });


    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.complaindailog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Button dialogButton = dialog.findViewById(R.id.backHomeButton);
        ImageView closeIcon = dialog.findViewById(R.id.closeIcon);
        dialogButton.setOnClickListener(view -> dialog.dismiss());
        closeIcon.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}