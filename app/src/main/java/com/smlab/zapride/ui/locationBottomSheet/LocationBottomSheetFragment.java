package com.smlab.zapride.ui.locationBottomSheet;

import android.content.Context;
import android.os.Bundle;
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
    private FragmentLocationBottomSheetBinding binding;
    private LocationSuggestionAdapter adapter;
    private static final String ARG_FROM_ADDRESS = "from_address";
    private String fromAddress;
    private List<String> suggestionList = new ArrayList<>();

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
        initialized();
        setListener();
        return binding.getRoot();
    }

    private void initialized() {
        adapter = new LocationSuggestionAdapter(suggestionList);
        binding.searchPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchPlacesRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        if (getArguments() != null) {
            fromAddress = getArguments().getString(ARG_FROM_ADDRESS, "");
            binding.ETFromLocation.setText(fromAddress);
        }

        // Add text watchers to both EditText fields
        addTextWatcher(binding.ETFromLocation);
        addTextWatcher(binding.ETToLocation);
    }

    private void addTextWatcher(View editText) {
        ((android.widget.EditText) editText).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    fetchSuggestions(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchSuggestions(String query) {
        Places.initialize(requireContext(), "AIzaSyCNReplTjOrJpfTL5-cS2I5oPVD0-ohH9M"); // Replace with your API key

        PlacesClient placesClient = Places.createClient(requireContext());

        // Specify the type of place predictions
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setCountry("PK") // You can set a specific country if required
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            suggestionList.clear();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                suggestionList.add(prediction.getFullText(null).toString());
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
        });
    }
}