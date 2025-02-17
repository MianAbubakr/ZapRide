package com.smlab.zapride.ui.controller;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.smlab.zapride.ui.editProfile.EditProfile;
import com.smlab.zapride.ui.locationBottomSheet.LocationBottomSheetFragment;
import com.smlab.zapride.ui.setting.Setting;
import com.smlab.zapride.ui.signIn.SignIn;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LocationBottomSheetFragment.OnLocationSelectedListener{
    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int LOCATION_SETTINGS_REQUEST_CODE = 1002;
    ActivityMainBinding binding;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private MarkerOptions markerOptions;
    private Marker marker;
    private RouteDrawer routeDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        setSelectedTextView(binding.includeLocationScreen.rideAC);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndInitializeMap();
    }

    private void checkLocationPermissionAndInitializeMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            checkLocationSettings();
        }
    }

    private void checkLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true); // Ensure the dialog always shows when necessary

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());

        task.addOnCompleteListener(task1 -> {
            try {
                task1.getResult(ApiException.class);
                initializeMap(); // Location settings are satisfied
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        showCustomLocationDialog();
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(this, "Location settings cannot be fixed. Please enable location manually.", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void showCustomLocationDialog() {
        Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_location_permission);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        Button allowBtn = dialog.findViewById(R.id.useMyLocationButton);
        Button skipBtn = dialog.findViewById(R.id.skipForNowButton);
        skipBtn.setOnClickListener(view -> dialog.dismiss());
        allowBtn.setOnClickListener(view -> {
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10000)
                    .setFastestInterval(5000);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .setAlwaysShow(true); // Ensure the dialog always shows when necessary

            Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this)
                    .checkLocationSettings(builder.build());

            task.addOnCompleteListener(task1 -> {
                try {
                    task1.getResult(ApiException.class);
                    initializeMap(); // Location settings are satisfied
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(this, LOCATION_SETTINGS_REQUEST_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Toast.makeText(this, "Location settings cannot be fixed. Please enable location manually.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            });
            dialog.dismiss();
        });
        dialog.show();
    }

    private void initializeMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.mapFragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this::onMapReady);
    }

    private void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Disable default location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getUserLocation();
        }

        // Initialize marker at a default position (this will be updated later)
        markerOptions = new MarkerOptions()
                .position(new LatLng(0, 0))
                .icon(BitmapDescriptorFactory.fromBitmap(Objects.requireNonNull(getBitmapFromVectorDrawable(R.drawable.location_marker_icon))));
        marker = googleMap.addMarker(markerOptions);

        // Update marker position while moving (but do not fetch address)
        googleMap.setOnCameraMoveListener(() -> {
            if (binding.includeLocationScreen.main.getVisibility() == View.VISIBLE) {
                Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                binding.includeLocationScreen.main.startAnimation(slideDown);
                binding.includeLocationScreen.main.setVisibility(View.GONE);
            }
            if (marker != null) {
                marker.setPosition(googleMap.getCameraPosition().target);
            }
        });

        // Fetch address when the camera stops moving
        googleMap.setOnCameraIdleListener(() -> {
            if (binding.includeLocationScreen.main.getVisibility() == View.GONE) {
                LatLng center = googleMap.getCameraPosition().target;
                getAddressFromLocation(center.latitude, center.longitude);
                Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                binding.includeLocationScreen.main.startAnimation(slideUp);
                binding.includeLocationScreen.main.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        new Thread(() -> {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    String fullAddress = addresses.get(0).getAddressLine(0);
                    String[] parts = fullAddress.split(",");
                    String shortAddress = (parts.length >= 2) ? parts[0] + ", " + parts[1] : fullAddress;

                    runOnUiThread(() -> binding.includeLocationScreen.ETFromLocation.setText(shortAddress));
                } else {
                    runOnUiThread(() -> binding.includeLocationScreen.ETFromLocation.setText("Unknown Location"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> binding.includeLocationScreen.ETFromLocation.setText("Location not found"));
            }
        }).start();
    }

    private void setListener() {
        // Custom location button click listener
        binding.includeLocationScreen.fabMyLocation.setOnClickListener(view -> {
            if (googleMap != null) {
                getUserLocation();
            } else {
                checkLocationSettings();
            }
        });

        binding.includeLocationScreen.ETFromLocation.setOnClickListener(view -> showLocationBottomSheet());
        binding.includeLocationScreen.ETToLocation.setOnClickListener(view -> showLocationBottomSheet());

        binding.includeLocationScreen.confirmLocationButton.setOnClickListener(view -> drawRoute());
        binding.profileIcon.setOnClickListener(view -> startActivity(new Intent(this, Setting.class)));

        binding.includeLocationScreen.rideAC.setOnClickListener(view -> setSelectedTextView(binding.includeLocationScreen.rideAC));
        binding.includeLocationScreen.rideMini.setOnClickListener(view -> setSelectedTextView(binding.includeLocationScreen.rideMini));
        binding.includeLocationScreen.auto.setOnClickListener(view -> setSelectedTextView(binding.includeLocationScreen.auto));
        binding.includeLocationScreen.bike.setOnClickListener(view -> setSelectedTextView(binding.includeLocationScreen.bike));
    }

    private void showLocationBottomSheet() {
        String currentLocation = binding.includeLocationScreen.ETFromLocation.getText().toString();
        LocationBottomSheetFragment bottomSheetFragment = LocationBottomSheetFragment.newInstance(currentLocation);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void setSelectedTextView(TextView selectedTextView) {
        // Reset all backgrounds
        binding.includeLocationScreen.rideAC.setSelected(false);
        binding.includeLocationScreen.rideMini.setSelected(false);
        binding.includeLocationScreen.auto.setSelected(false);
        binding.includeLocationScreen.bike.setSelected(false);
        // Set the selected TextView's background
        selectedTextView.setSelected(true);
    }

    private LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void drawRoute() {
        routeDrawer = new RouteDrawer(this, googleMap);
        String fromLocation = binding.includeLocationScreen.ETFromLocation.getText().toString();
        String toLocation = binding.includeLocationScreen.ETToLocation.getText().toString();

        LatLng fromLatLng = getLatLngFromAddress(fromLocation);
        LatLng toLatLng = getLatLngFromAddress(toLocation);

        if (fromLatLng != null && toLatLng != null) {
            routeDrawer.drawRoute(fromLatLng, toLatLng, R.drawable.location_marker_icon, R.drawable.destination_marker);
        } else {
            Toast.makeText(this, "Could not fetch location coordinates", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        if (marker == null) {
                            markerOptions = new MarkerOptions()
                                    .position(userLocation)
                                    .icon(BitmapDescriptorFactory.fromBitmap(Objects.requireNonNull(getBitmapFromVectorDrawable(R.drawable.location_marker_icon))));
                            marker = googleMap.addMarker(markerOptions);
                        } else {
                            marker.setPosition(userLocation);
                        }
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f));
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                        break;
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettings();
            } else {
                Toast.makeText(this, "Location permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                initializeMap();
            } else {
                Toast.makeText(this, "Location services need to be enabled for this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getBitmapFromVectorDrawable(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onLocationSelected(String location) {
        binding.includeLocationScreen.ETToLocation.setText(location);
    }
}