package com.smlab.zapride.ui.locationBottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smlab.zapride.databinding.FragmentLocationBottomSheetBinding;

import java.util.ArrayList;
import java.util.List;

public class LocationBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = "LocationBottomSheetFrag";
    private FragmentLocationBottomSheetBinding binding;
    private static final String ARG_FROM_ADDRESS = "from_address";
    private String fromAddress;

    public static LocationBottomSheetFragment newInstance(String fromAddress) {
        LocationBottomSheetFragment fragment = new LocationBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FROM_ADDRESS, fromAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBottomSheetBinding.inflate(inflater, container, false);

        setListeners();
        return binding.getRoot();
    }

    public interface OnLocationSelectedListener {
        void onLocationSelected(String location);
    }

    private void setListeners() {
        if (getArguments() != null) {
            fromAddress = getArguments().getString(ARG_FROM_ADDRESS, "");
            binding.ETFromLocation.setText(fromAddress);
        }

        binding.continueButton.setOnClickListener(v -> {
            String selectedLocation = binding.ETToLocation.getText().toString().trim();
            if (!selectedLocation.isEmpty()) {
                OnLocationSelectedListener listener = (OnLocationSelectedListener) getActivity();
                if (listener != null) {
                    listener.onLocationSelected(selectedLocation);
                }
                dismiss();
            } else {
                binding.ETToLocation.setError("Please enter a location");
            }
        });
    }
}