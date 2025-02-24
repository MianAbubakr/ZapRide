package com.smlab.zapride.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import com.smlab.zapride.databinding.DialogLayoutCustomProgressBinding;

public class CustomProgressDialog extends Dialog {
    private static final String TAG = "CustomProgressDialog";
    DialogLayoutCustomProgressBinding binding;
    private final Context context;

    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogLayoutCustomProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Log.d(TAG, "onCreate: ");
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Log.d(TAG, "onCreate: call: dialog: ");
    }

    public void setMessage(String message) {
        if (binding != null) {
            binding.loadingMessage.setText(message);
        }
    }
}