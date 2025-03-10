package com.smlab.zapride.ui.history;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityHistoryBinding;
import com.smlab.zapride.ui.history.adaptor.historyAdaptor;
import com.smlab.zapride.ui.history.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialized();
        setupRecyclerViews();
        setupListeners();
    }

    private void initialized() {
        showPastBookings();
    }

    private void setupRecyclerViews() {
        binding.recyclerViewPast.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this));

        List<HistoryModel> pastBookings = new ArrayList<>();
        List<HistoryModel> upcomingBookings = new ArrayList<>();

        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        pastBookings.add(new HistoryModel("14 August 2024, 07:00 pm", 40, "TRNX315872", "Model Town, Lahore", "Airport Road, Lahore", "Completed"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("20 August 2024, 10:00 am", 50, "TRNX415872", "Gulberg, Lahore", "DHA, Lahore", "Scheduled"));
        upcomingBookings.add(new HistoryModel("21 August 2024, 12:30 pm", 60, "TRNX515872", "Johar Town, Lahore", "Wapda Town, Lahore", "Scheduled"));

        binding.recyclerViewPast.setAdapter(new historyAdaptor(pastBookings));
        binding.recyclerViewUpcoming.setAdapter(new historyAdaptor(upcomingBookings));
    }

    private void setupListeners() {
        binding.btnPast.setOnClickListener(v -> showPastBookings());
        binding.btnUpcoming.setOnClickListener(v -> showUpcomingBookings());
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void showPastBookings() {
        updateButtonState(binding.btnPast, true);
        updateButtonState(binding.btnUpcoming, false);
        binding.recyclerViewPast.setVisibility(View.VISIBLE);
        binding.recyclerViewUpcoming.setVisibility(View.GONE);
    }

    private void showUpcomingBookings() {
        updateButtonState(binding.btnUpcoming, true);
        updateButtonState(binding.btnPast, false);
        binding.recyclerViewPast.setVisibility(View.GONE);
        binding.recyclerViewUpcoming.setVisibility(View.VISIBLE);
    }

    private void updateButtonState(Button button, boolean isSelected) {
        int backgroundColor = isSelected ? Color.parseColor("#FFCC33") : Color.LTGRAY;
        int textColor = isSelected ? Color.WHITE : Color.BLACK;
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor);
        drawable.setCornerRadius(25);
        button.setBackground(drawable);
        button.setTextColor(textColor);
    }
}
