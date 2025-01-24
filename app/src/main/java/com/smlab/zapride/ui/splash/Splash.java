package com.smlab.zapride.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.smlab.zapride.MainActivity;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySplashBinding;
import com.smlab.zapride.ui.onBoarding.OnBoarding;

public class Splash extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        splashDelayUpdate();
    }

    private void userAuthentication() {
            startActivity(new Intent(Splash.this, OnBoarding.class));
            finish();
    }

    private void splashDelayUpdate() {
        new Handler().postDelayed(this::userAuthentication, 3000);
    }
}