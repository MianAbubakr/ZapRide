package com.smlab.zapride.ui.signUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivitySignUpOtpverificationBinding;
import com.smlab.zapride.utils.CustomProgressDialog;

import java.util.concurrent.TimeUnit;

public class SignUpOTPVerification extends AppCompatActivity {
    private static final String TAG = "SignUpOTPVerification";
    ActivitySignUpOtpverificationBinding binding;
    CustomProgressDialog progressDialog;
    String verificationIdValue, verificationCodeValue, phoneNumber;
    private boolean isResending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpOtpverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getIntentData();
        initialized();
        setListener();
    }

    @SuppressLint("SetTextI18n")
    private void getIntentData() {
        verificationIdValue = getIntent().getStringExtra("verificationId");
        phoneNumber = getIntent().getStringExtra("fullPhoneNumber");
        binding.phoneNumberTextView.setText("Code has been send to " + phoneNumber);
    }

    private void initialized() {
        progressDialog = new CustomProgressDialog(this);
        binding.editTextUniqueCode1.requestFocus();
    }

    private void setListener() {
        binding.backBtn.setOnClickListener(view -> onBackPressed());

        binding.editTextUniqueCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode1.clearFocus();
                        binding.editTextUniqueCode1.requestFocus();
                    } else if (editable.length() == 1) {
                        binding.editTextUniqueCode2.requestFocus();
                    }
                }
            }
        });

        binding.editTextUniqueCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode2.clearFocus();
                        binding.editTextUniqueCode1.requestFocus();
                    } else if (editable.length() == 1) {
                        binding.editTextUniqueCode3.requestFocus();
                    }
                }
            }
        });

        binding.editTextUniqueCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode3.clearFocus();
                        binding.editTextUniqueCode2.requestFocus();
                    } else if (editable.length() == 1) {
                        binding.editTextUniqueCode4.requestFocus();
                    }
                }
            }
        });

        binding.editTextUniqueCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode4.clearFocus();
                        binding.editTextUniqueCode3.requestFocus();
                    } else if (editable.length() == 1) {
                        binding.editTextUniqueCode5.requestFocus();
                    }
                }
            }
        });

        binding.editTextUniqueCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode5.clearFocus();
                        binding.editTextUniqueCode4.requestFocus();
                    } else if (editable.length() == 1) {
                        binding.editTextUniqueCode6.requestFocus();
                    }
                }
            }
        });

        binding.editTextUniqueCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() == 0) {
                        binding.editTextUniqueCode6.clearFocus();
                        binding.editTextUniqueCode1.requestFocus();
                    }
                }
            }
        });

        binding.verifyButton.setOnClickListener(view -> {
            progressDialog.show();
            setUpVerificationCode();
        });

        binding.resendTextView.setOnClickListener(view -> {
            if (!isResending) {
                isResending = true;
                resendOTP(phoneNumber);
                progressDialog.show();
            }
        });
    }

    private void setUpVerificationCode() {
        verificationCodeValue = binding.editTextUniqueCode1.getText().toString() +
                binding.editTextUniqueCode2.getText().toString() +
                binding.editTextUniqueCode3.getText().toString() +
                binding.editTextUniqueCode4.getText().toString() +
                binding.editTextUniqueCode5.getText().toString() +
                binding.editTextUniqueCode6.getText().toString();
        if (TextUtils.isEmpty(verificationCodeValue)) {
            binding.editTextUniqueCode1.setError("Enter OTP");
            progressDialog.dismiss();
        } else {
            if (verificationIdValue != null) {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationIdValue, verificationCodeValue);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            Log.d(TAG, "setUpVerificationCode: firebase: " + uid);
                            Intent intent = new Intent(SignUpOTPVerification.this, AccountCreatedSplash.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(this, "The Verification code entered was invalid", Toast.LENGTH_SHORT).show();
                        clearEditTextFields();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    private void clearEditTextFields() {
        binding.editTextUniqueCode1.getText().clear();
        binding.editTextUniqueCode2.getText().clear();
        binding.editTextUniqueCode3.getText().clear();
        binding.editTextUniqueCode4.getText().clear();
        binding.editTextUniqueCode5.getText().clear();
        binding.editTextUniqueCode6.getText().clear();
        binding.editTextUniqueCode1.requestFocus();
    }

    private void resendOTP(String phoneNumber) {
        progressDialog.show();
        Log.d(TAG, "resendOTP: format: phoneNumber: " + phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, SignUpOTPVerification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(SignUpOTPVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.d("TAG", "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationIdValue = newVerificationId;
                Log.d(TAG, "onCodeSent: resendCode: ");
                Toast.makeText(SignUpOTPVerification.this, "Verification code resent successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                isResending = false;
            }
        });
    }
}