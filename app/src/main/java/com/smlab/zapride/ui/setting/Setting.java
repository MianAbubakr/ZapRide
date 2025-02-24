package com.smlab.zapride.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
    String currentLocation;

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
        getIntentData();
        setListener();
    }

    private void getIntentData() {
        currentLocation = getIntent().getStringExtra("currentLocation");
    }

    private void setListener() {
        binding.closeIcon.setOnClickListener(view -> onBackPressed());
        binding.logoutButton.setOnClickListener(view -> showLogOutDialog());
        binding.constraintProfile.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditProfile.class);
            intent.putExtra("currentLocation", currentLocation);
            startActivity(intent);
        });
        binding.constraintHistory.setOnClickListener(view -> startActivity(new Intent(this, History.class)));
        binding.nameTextView.setText(getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("userName", ""));
    }

    private void showLogOutDialog() {
        Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.logout_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        Button yesBtn = dialog.findViewById(R.id.btnYes);
        Button noBtn = dialog.findViewById(R.id.btnNo);
        noBtn.setOnClickListener(view -> dialog.dismiss());
        yesBtn.setOnClickListener(view -> {
            logoutUser();
            dialog.dismiss();
        });
        dialog.show();
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