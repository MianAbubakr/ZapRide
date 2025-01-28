package com.smlab.zapride.ui.welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityWelcomeScreenBinding;
import com.smlab.zapride.ui.signIn.SignIn;
import com.smlab.zapride.ui.signUp.SignUp;

public class WelcomeScreen extends AppCompatActivity {
    ActivityWelcomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWelcomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
    }

    private void setListener() {
        binding.createAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });

        binding.logIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignIn.class));
        });
    }
}