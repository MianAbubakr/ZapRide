package com.smlab.zapride.ui.editProfile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityEditProfileBinding;

public class EditProfile extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
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
        binding.ETAddress.setText(currentLocation);
    }

    private void setListener() {
        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.textViewName.setText(getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("userName", ""));
        String phoneNumber = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("userPhoneNumber", "");

        // Detect and remove country code
        String localNumber = removeCountryCode(phoneNumber);

        binding.ETPhoneNumber.setText(localNumber);
    }

    private String removeCountryCode(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            // Parse the number and detect country
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(phoneNumber, null);

            // Get the national number without country code
            return String.valueOf(parsedNumber.getNationalNumber());
        } catch (NumberParseException e) {
            e.printStackTrace();
            return phoneNumber; // Return original if parsing fails
        }
    }
}