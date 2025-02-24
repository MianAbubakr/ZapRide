package com.smlab.zapride.ui.signIn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.vardemin.materialcountrypicker.PhoneNumberEditText;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySignInBinding;
import com.smlab.zapride.bussinessLogic.DatabaseHelper;
import com.smlab.zapride.ui.controller.MainActivity;
import com.smlab.zapride.ui.signUp.SignUp;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;
    DatabaseHelper databaseHelper;

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
        initialized();
        setListener();
    }

    private void initialized() {
        databaseHelper = new DatabaseHelper(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());

        binding.textViewSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });

        binding.signInButton.setOnClickListener(v -> {
            String phoneNumber = getPhoneNumberWithCountryCode();
            String password = binding.ETPassword.getText().toString();

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                binding.ETPhoneNumber.setError("Please enter phone number");
                binding.ETPassword.setError("Please enter password");
            } else {
                Boolean checkCredentials = databaseHelper.checkPhoneNumberPassword(phoneNumber, password);

                if (checkCredentials) {
                    saveUserLoginStatus();
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ETPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (binding.ETPassword.getRight() - binding.ETPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // Toggle password visibility
                    int inputType = binding.ETPassword.getInputType();
                    if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        binding.ETPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        binding.ETPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_hide_icon, 0);
                    } else {
                        binding.ETPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        binding.ETPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_hide_icon, 0);
                    }
                    binding.ETPassword.setSelection(binding.ETPassword.getText().length()); // Maintain cursor position
                    return true;
                }
            }
            return false;
        });
    }

    private void saveUserLoginStatus() {
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .putBoolean("isLoggedIn", true)
                .apply();
    }

    private String getPhoneNumberWithCountryCode() {
        PhoneNumberEditText phoneNumberEditText = binding.ETPhoneNumber;
        String countryCode = phoneNumberEditText.getSelectedCountryDialCode();
        String phoneNumber = phoneNumberEditText.getText().toString();
        return countryCode + phoneNumber;
    }
}