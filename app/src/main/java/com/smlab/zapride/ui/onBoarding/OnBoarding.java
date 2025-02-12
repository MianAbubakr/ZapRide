package com.smlab.zapride.ui.onBoarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.smlab.zapride.R;
import com.smlab.zapride.databinding.ActivityOnBoardingBinding;
import com.smlab.zapride.ui.onBoarding.adapter.OnboardingAdapter;
import com.smlab.zapride.ui.onBoarding.model.OnboardingItem;
import com.smlab.zapride.ui.welcome.WelcomeScreen;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {
    ActivityOnBoardingBinding binding;
    private OnboardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListener();
        setUpViewPager();
    }

    private void setListener() {
        binding.btnNext.setOnClickListener(v -> {
            int currentItem = binding.viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                // Move to the next screen
                binding.viewPager.setCurrentItem(currentItem + 1);
            } else {
                // Last screen, go to next activity
                startActivity(new Intent(OnBoarding.this, WelcomeScreen.class));
                finish();
            }
        });

        binding.skipTextView.setOnClickListener(view -> {
            startActivity(new Intent(OnBoarding.this, WelcomeScreen.class));
            finish();
        });
    }

    private void setUpViewPager() {
        // Set up the ViewPager with the adapter
        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem("Anywhere you are", R.string.onBoardingDesc, R.drawable.onboarding_1));
        items.add(new OnboardingItem("At anytime", R.string.onBoardingDesc, R.drawable.onboading_2));
        items.add(new OnboardingItem("Book your car", R.string.onBoardingDesc, R.drawable.onboarding_3));
        adapter = new OnboardingAdapter(items);
        binding.viewPager.setAdapter(adapter);

        // Change button text on the last page
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getItemCount() - 1) {
                    binding.btnNext.setImageResource(R.drawable.go);
                } else {
                    binding.btnNext.setImageResource(R.drawable.next_arrow);
                }
            }
        });
    }
}