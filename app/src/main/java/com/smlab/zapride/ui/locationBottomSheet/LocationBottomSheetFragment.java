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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.smlab.zapride.databinding.FragmentLocationBottomSheetBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LocationBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = "LocationBottomSheetFrag";
    private FragmentLocationBottomSheetBinding binding;
    private static final String ARG_FROM_ADDRESS = "from_address";
    private static final String ARG_TO_ADDRESS = "to_address";
    private String fromAddress, toAddress;
    private LocationSuggestionAdapter adapter;
    private List<String> suggestions = new ArrayList<>();

    public interface OnLocationSelectedListener {
        void onLocationSelected(String location);
    }

    public static LocationBottomSheetFragment newInstance(String fromAddress, String toAddress) {
        LocationBottomSheetFragment fragment = new LocationBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FROM_ADDRESS, fromAddress);
        args.putString(ARG_TO_ADDRESS, toAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBottomSheetBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setListeners();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new LocationSuggestionAdapter(suggestions, location -> {
            binding.ETToLocation.setText(location);
            binding.autoCompleteRecyclerView.setVisibility(View.GONE);
            binding.textViewNoResult.setVisibility(View.VISIBLE);
            // Send location back to main activity and dismiss
            if (getActivity() instanceof OnLocationSelectedListener) {
                ((OnLocationSelectedListener) getActivity()).onLocationSelected(location);
            }
            dismiss();
        });
        binding.autoCompleteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.autoCompleteRecyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        if (getArguments() != null) {
            fromAddress = getArguments().getString(ARG_FROM_ADDRESS, "");
            toAddress = getArguments().getString(ARG_TO_ADDRESS, "");
            binding.ETFromLocation.setText(fromAddress);
            binding.ETToLocation.setText(toAddress);
        }

        // Request focus and open keyboard for ETToLocation
        new Handler().postDelayed(() -> {
            binding.ETToLocation.requestFocus();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(binding.ETToLocation, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 300); // Delay to ensure the UI is fully loaded

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

        binding.ETToLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    fetchLocationSuggestions(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchLocationSuggestions(String query) {
        new Thread(() -> {
            try {
                // Define bounding box (min longitude, min latitude, max longitude, max latitude)
                // Example: Lahore (Pakistan) bounding box
                String viewbox = "74.20,31.40,74.60,31.60"; // Adjust according to your needs
                String bounded = "1"; // Restricts results within the bounding box

                String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + query +
                        "&viewbox=" + viewbox + "&bounded=" + bounded;

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "ZapRide-App");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                List<String> newSuggestions = new ArrayList<>();
                for (int i = 0; i < Math.min(jsonArray.length(), 5); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String displayName = obj.getString("display_name");

                    // Ensure the address contains at least one English letter
                    if (displayName.matches(".*[A-Za-z].*")) {
                        // Split the address and take only the first two parts
                        String[] parts = displayName.split(",");
                        String shortAddress = (parts.length >= 2) ? parts[0].trim() + ", " + parts[1].trim() : displayName;

                        newSuggestions.add(shortAddress);
                    }
                }

                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        suggestions.clear();
                        suggestions.addAll(newSuggestions);
                        adapter.notifyDataSetChanged();
                        binding.autoCompleteRecyclerView.setVisibility(View.VISIBLE);
                        binding.textViewNoResult.setVisibility(View.GONE);
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Error fetching locations", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        }).start();
    }
}