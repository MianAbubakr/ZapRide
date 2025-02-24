package com.smlab.zapride.ui.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.smlab.zapride.R;
import com.smlab.zapride.utils.CustomProgressDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RouteDrawer {
    private Context context;
    private GoogleMap googleMap;
    CustomProgressDialog customProgressDialog;

    private static final LatLng CIRCLE_CENTER = new LatLng(31.56876, 74.30569); // Example: Lahore
    private static final double CIRCLE_RADIUS = 23750; // Radius in meters

    public RouteDrawer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
    }

    public void drawRoute(LatLng fromLatLng, LatLng toLatLng, int startMarkerIconResId, int destinationMarkerIconResId) {
        if (googleMap == null) {
            Toast.makeText(context, "Map is not ready. Please try again.", Toast.LENGTH_SHORT).show();
            return; // Exit if googleMap is null
        }

        if (fromLatLng != null && toLatLng != null) {
            // Check if both points are within the circular area
            if (!isWithinCircle(fromLatLng) || !isWithinCircle(toLatLng)) {
                Toast.makeText(context, "Route is outside the allowed area.", Toast.LENGTH_SHORT).show();
                return;
            }

            googleMap.clear(); // Clear existing markers and polylines

            // Show progress dialog before making the API call
            customProgressDialog = new CustomProgressDialog(context);
            customProgressDialog.show();  // Show the progress dialog

            // Create custom markers
            BitmapDescriptor startIcon = BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(startMarkerIconResId));
            BitmapDescriptor destinationIcon = BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(destinationMarkerIconResId));

            // Add markers with custom icons
            googleMap.addMarker(new MarkerOptions().position(fromLatLng).icon(startIcon));
            googleMap.addMarker(new MarkerOptions().position(toLatLng).icon(destinationIcon));

            // Fetch route from OSRM API
            String url = "https://router.project-osrm.org/route/v1/driving/" +
                    fromLatLng.longitude + "," + fromLatLng.latitude + ";" +
                    toLatLng.longitude + "," + toLatLng.latitude +
                    "?overview=full&geometries=polyline";

            new FetchRouteTask().execute(url);
        } else {
            Toast.makeText(context, "Could not fetch location coordinates", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isWithinCircle(LatLng point) {
        float[] results = new float[1];
        Location.distanceBetween(CIRCLE_CENTER.latitude, CIRCLE_CENTER.longitude, point.latitude, point.longitude, results);
        return results[0] <= CIRCLE_RADIUS;
    }

    private Bitmap getBitmapFromVectorDrawable(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // AsyncTask to fetch route from OSRM API
    private class FetchRouteTask extends AsyncTask<String, Void, List<LatLng>> {
        @Override
        protected List<LatLng> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                inputStream.close();

                return decodePolyline(new JSONObject(response.toString())
                        .getJSONArray("routes")
                        .getJSONObject(0)
                        .getString("geometry"));

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<LatLng> polylinePoints) {
            // Dismiss progress dialog once the route is fetched
            if (customProgressDialog != null && customProgressDialog.isShowing()) {
                customProgressDialog.dismiss();
            }
            if (polylinePoints != null) {
                googleMap.addPolyline(new PolylineOptions()
                        .addAll(polylinePoints)
                        .width(8)
                        .color(ContextCompat.getColor(context, R.color.mainColor))
                        .geodesic(true));

                // Move camera to fit route
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        new LatLngBounds.Builder()
                                .include(polylinePoints.get(0))
                                .include(polylinePoints.get(polylinePoints.size() - 1))
                                .build(), 200));

                // **Calculate total distance**
                double totalDistanceMeters = calculateTotalDistance(polylinePoints);
                double totalDistanceKm = totalDistanceMeters / 1000; // Convert to kilometers
                double totalFare = totalDistanceKm * 30; // Rs 30 per kilometer

                // **Update TextView with fare**
                TextView textViewAmount = ((Activity) context).findViewById(R.id.textViewAmount);
                int roundedFare = (int) Math.round(totalFare);
                textViewAmount.setText("PKR: " + roundedFare);
            } else {
                Toast.makeText(context, "Failed to fetch route", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private double calculateTotalDistance(List<LatLng> polylinePoints) {
        float totalDistance = 0;
        for (int i = 0; i < polylinePoints.size() - 1; i++) {
            LatLng point1 = polylinePoints.get(i);
            LatLng point2 = polylinePoints.get(i + 1);

            float[] results = new float[1];
            Location.distanceBetween(point1.latitude, point1.longitude,
                    point2.latitude, point2.longitude,
                    results);
            totalDistance += results[0]; // Distance in meters
        }
        return totalDistance;
    }

    // Decode polyline from OSRM response
    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> polyline = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            polyline.add(new LatLng(lat / 1E5, lng / 1E5));
        }
        return polyline;
    }
}
