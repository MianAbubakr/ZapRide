package com.smlab.zapride;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.smlab.zapride.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.smlab.zapride.ui.aboutUs.AboutUs;
import com.smlab.zapride.ui.complain.Complain;
import com.smlab.zapride.ui.editProfile.EditProfile;
import com.smlab.zapride.ui.helpandsupport.HelpandSupport;
import com.smlab.zapride.ui.locationBottomSheet.LocationBottomSheetFragment;
import com.smlab.zapride.ui.notification.Notification;
import com.smlab.zapride.ui.settings.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int LOCATION_SETTINGS_REQUEST_CODE = 1002;
    ActivityMainBinding binding;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

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
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_location_permission);
        dialog.setCancelable(true);

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

        binding.notificationIcon.setOnClickListener(view -> startActivity(new Intent(this, Notification.class)));

        binding.btnDrawerToggle.setOnClickListener(view -> {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView);
            } else {
                binding.drawerLayout.openDrawer(binding.navigationView);
            }
        });

        // Set listener for back button in navigation header
        View navHeaderView = binding.navigationView.getHeaderView(0);
        ConstraintLayout btnBack = navHeaderView.findViewById(R.id.backBtn);
        btnBack.setOnClickListener(view -> {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView);
            }
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_editProfile) {
                startActivity(new Intent(this, EditProfile.class));
            } else if (itemId == R.id.nav_history) {
                Toast.makeText(MainActivity.this, "History Selected", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_complain) {
                startActivity(new Intent(this, Complain.class));
            } else if (itemId == R.id.nav_referral) {
                Toast.makeText(MainActivity.this, "Referral Selected", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_aboutUs) {
                startActivity(new Intent(this, AboutUs.class));
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, Settings.class));
            } else if (itemId == R.id.nav_helpAndSupport) {
                startActivity(new Intent(this, HelpandSupport.class));
            } else if (itemId == R.id.nav_logout) {
                Toast.makeText(MainActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
            }
            binding.drawerLayout.closeDrawer(binding.navigationView);
            return true;
        });

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
                        updateLocationUI(userLocation, location);
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

    private void updateLocationUI(LatLng userLocation, Location location) {
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(userLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(Objects.requireNonNull(getBitmapFromVectorDrawable(R.drawable.location_marker_icon))));
        googleMap.addMarker(markerOptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f));

        // Fetch place name using Geocoder
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Extract block name, road name, or area name components
                String subLocality = address.getSubLocality();     // Block or neighborhood
                String thoroughfare = address.getThoroughfare();   // Road name
                String locality = address.getLocality();           // Area or city

                String placeInfo = "";
                if (subLocality != null) placeInfo += subLocality;
                if (thoroughfare != null) placeInfo += (placeInfo.isEmpty() ? "" : ", ") + thoroughfare;
                if (locality != null) placeInfo += (placeInfo.isEmpty() ? "" : ", ") + locality;

                if (!placeInfo.isEmpty()) {
                    binding.includeLocationScreen.ETFromLocation.setText(placeInfo);
                } else {
                    Toast.makeText(this, "Unable to fetch specific location details", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Unable to fetch place name", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}