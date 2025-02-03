package com.smlab.zapride.ui.locationBottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smlab.zapride.databinding.FragmentLocationBottomSheetBinding;

public class LocationBottomSheetFragment extends BottomSheetDialogFragment {
    private FragmentLocationBottomSheetBinding binding;

    public LocationBottomSheetFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBottomSheetBinding.inflate(inflater, container, false);
        showKeyboard(binding.ETFromLocation);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}