package com.smlab.zapride.ui.privacyPolicy;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicy extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
    }

    private void setListener() {
        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}