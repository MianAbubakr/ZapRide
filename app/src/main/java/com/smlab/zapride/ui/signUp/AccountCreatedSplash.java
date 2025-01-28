package com.smlab.zapride.ui.signUp;

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

public class AccountCreatedSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_created_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        splashDelayUpdate();
    }

    private void userAuthentication() {
        startActivity(new Intent(AccountCreatedSplash.this, MainActivity.class));
        finishAffinity();
    }

    private void splashDelayUpdate() {
        new Handler().postDelayed(this::userAuthentication, 2000);
    }
}