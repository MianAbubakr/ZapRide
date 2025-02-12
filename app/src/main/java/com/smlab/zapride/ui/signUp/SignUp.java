package com.smlab.zapride.ui.signUp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.vardemin.materialcountrypicker.PhoneNumberEditText;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySignUpBinding;
import com.smlab.zapride.bussinessLogic.DatabaseHelper;
import com.smlab.zapride.ui.signIn.SignIn;
import com.smlab.zapride.utils.CustomProgressDialog;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "SignUp";
    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;
    private TextView termsTextView;
    CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialized();
        setListener();
        setupTermsAndPrivacyPolicyText();
    }

    private void initialized() {
        progressDialog = new CustomProgressDialog(this);
        databaseHelper = new DatabaseHelper(this);
    }

    private void setListener() {
        binding.backBtn.setOnClickListener(view -> onBackPressed());

        binding.textViewSignIn.setOnClickListener(view -> {
            startActivity(new Intent(this, SignIn.class));
            finishAffinity();
        });

        binding.signUpButton.setOnClickListener(view -> {
            progressDialog.show();
            String phoneNumber = getPhoneNumberWithCountryCode();
            String password = binding.ETPassword.getText().toString();
            String confirmPassword = binding.ETConfirmPassword.getText().toString();

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Please enter a confirm password", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            if (!binding.customCheckBox.isChecked()) {
                Toast.makeText(this, "You must agree to the terms of service and privacy policy", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            if (password.equals(confirmPassword)) {
                Boolean checkUserPhoneNumber = databaseHelper.checkPhoneNumber(phoneNumber);

                if (checkUserPhoneNumber == false) {
                    Boolean insert = databaseHelper.insertData(phoneNumber, password);
                    if (insert == true) {
                        saveUserLoginStatus();
                        Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(this, AccountCreatedSplash.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            } else {
                Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

//            if (progressDialog != null) {
//                progressDialog.dismiss();
//                sendOtp(phoneNumber);
//            }
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

//    private void sendOtp(String phoneNumber) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, SignUp.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Log.e(TAG, "onVerificationFailed: ", e);
//                progressDialog.dismiss();
//                if (e instanceof FirebaseTooManyRequestsException) {
//                    Toast.makeText(SignUp.this, "Too many requests. Please try again later.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                Log.d(TAG, "onCodeSent: " + verificationId);
//                progressDialog.dismiss();
//                Intent intent = new Intent(SignUp.this, SignUpOTPVerification.class);
//                intent.putExtra("verificationId", verificationId);
//                intent.putExtra("fullPhoneNumber", phoneNumber);
//                startActivity(intent);
//            }
//        });
//    }

    private void setupTermsAndPrivacyPolicyText() {
        termsTextView = findViewById(R.id.termsTextView);
        String text = "By signing up. you agree to the Terms of Service and Privacy Policy";

        SpannableString spannableString = new SpannableString(text);

        // Terms of Service ClickableSpan
        ClickableSpan termsOfServiceClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle Terms of Service click
                // For example, open a new Activity or a WebView with the terms of service URL
                changeSpanColor(spannableString, 32, 48, Color.RED);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FEC400")); // Set the text color
                ds.setUnderlineText(false); // Remove underline
            }
        };

        // Privacy Policy ClickableSpan
        ClickableSpan privacyPolicyClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Handle Privacy Policy click
                // For example, open a new Activity or a WebView with the privacy policy URL
                changeSpanColor(spannableString, 53, 67, Color.RED);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FEC400")); // Set the text color
                ds.setUnderlineText(false); // Remove underline
            }
        };

        spannableString.setSpan(termsOfServiceClick, 32, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyPolicyClick, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsTextView.setText(spannableString);
        termsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void changeSpanColor(SpannableString spannableString, int start, int end, int color) {
        ForegroundColorSpan[] spans = spannableString.getSpans(start, end, ForegroundColorSpan.class);
        for (ForegroundColorSpan span : spans) {
            spannableString.removeSpan(span);
        }
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termsTextView.setText(spannableString);
    }
}