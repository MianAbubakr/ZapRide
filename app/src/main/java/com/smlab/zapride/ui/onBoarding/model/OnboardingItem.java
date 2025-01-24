package com.smlab.zapride.ui.onBoarding.model;

public class OnboardingItem {
    private String title;
    private int imageResId, description;

    public OnboardingItem(String title, int description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }
    public int getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
