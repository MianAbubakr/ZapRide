package com.smlab.zapride.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySettingsBinding;
import com.smlab.zapride.ui.contactUs.ContactUs;
import com.smlab.zapride.ui.deleteAccount.DeleteAccount;
import com.smlab.zapride.ui.privacyPolicy.PrivacyPolicy;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
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

        binding.deleteAccountButton.setOnClickListener(v -> {
           startActivity(new Intent(this, DeleteAccount.class));
        });

        binding.privacyPolicyButton.setOnClickListener(v -> {
            startActivity(new Intent(this, PrivacyPolicy.class));
        });

        binding.contactUsButton.setOnClickListener(v -> {
           startActivity(new Intent(this, ContactUs.class));
        });
    }
}