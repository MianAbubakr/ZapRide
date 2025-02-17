package com.smlab.zapride.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySettingBinding;
import com.smlab.zapride.ui.editProfile.EditProfile;
import com.smlab.zapride.ui.history.History;
import com.smlab.zapride.ui.signIn.SignIn;

public class Setting extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
    }

    private void setListener() {
        binding.closeIcon.setOnClickListener(view -> onBackPressed());
        binding.logoutButton.setOnClickListener(view -> logoutUser());
        binding.constraintProfile.setOnClickListener(view -> startActivity(new Intent(this, EditProfile.class)));
        binding.constraintHistory.setOnClickListener(view -> startActivity(new Intent(this, History.class)));
    }

    private void logoutUser() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .remove("isLoggedIn")
                .apply();

        startActivity(new Intent(this, SignIn.class));
        finishAffinity();
    }
}