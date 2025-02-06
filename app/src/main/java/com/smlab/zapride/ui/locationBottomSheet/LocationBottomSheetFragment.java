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
    private LocationSuggestionAdapter adapter;
    private static final String ARG_FROM_ADDRESS = "from_address";
    private String fromAddress;
    private List<String> suggestionList = new ArrayList<>();
    private PlacesClient placesClient;

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
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyASrwmr7b1OTDGQErYvIXyLeD3iNl_qziA"); // Replace with your API key
        }
        placesClient = Places.createClient(requireContext());

        initializeRecyclerView();
        setListeners();
        return binding.getRoot();
    }

    private void initializeRecyclerView() {
        adapter = new LocationSuggestionAdapter(suggestionList);
        binding.searchPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchPlacesRecyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        if (getArguments() != null) {
            fromAddress = getArguments().getString(ARG_FROM_ADDRESS, "");
            binding.ETFromLocation.setText(fromAddress);
        }

        // Add text watchers to both EditText fields
        addTextWatcher(binding.ETFromLocation);
//        addTextWatcher(binding.ETToLocation);
    }

    private void addTextWatcher(View editText) {
        ((android.widget.EditText) editText).addTextChangedListener(new TextWatcher() {
            private final Handler handler = new Handler();
            private Runnable suggestionRunnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (suggestionRunnable != null) {
                    handler.removeCallbacks(suggestionRunnable);
                }

                suggestionRunnable = () -> {
                    if (s.length() > 2) {
                        fetchSuggestions(s.toString());
                    } else {
                        suggestionList.clear();
                        adapter.notifyDataSetChanged();
                    }
                };
                handler.postDelayed(suggestionRunnable, 300); // Delay to reduce API calls
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchSuggestions(String query) {
        android.util.Log.d(TAG, "Starting fetchSuggestions for query: " + query);

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setCountry("PK")
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            android.util.Log.d(TAG, "API call successful");
            suggestionList.clear();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                String place = prediction.getFullText(null).toString();
                suggestionList.add(place);
                android.util.Log.d(TAG, "Suggestion: " + place);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(exception -> {
            android.util.Log.e(TAG, "API call failed: " + exception.getMessage(), exception);
        });
    }
}