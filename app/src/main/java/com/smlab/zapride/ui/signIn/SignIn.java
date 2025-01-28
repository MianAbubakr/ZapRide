package com.smlab.zapride.ui.signIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.MainActivity;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySignInBinding;
import com.smlab.zapride.ui.signUp.SignUp;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
    }

    private void setListener() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());

        binding.signInButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInOTP.class));
        });

        binding.textViewSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });
    }
}