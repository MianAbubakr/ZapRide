// Generated by view binder compiler. Do not edit!
package com.smlab.zapride.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.smlab.zapride.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpOtpverificationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView backBtn;

  @NonNull
  public final EditText editTextUniqueCode1;

  @NonNull
  public final EditText editTextUniqueCode2;

  @NonNull
  public final EditText editTextUniqueCode3;

  @NonNull
  public final EditText editTextUniqueCode4;

  @NonNull
  public final EditText editTextUniqueCode5;

  @NonNull
  public final EditText editTextUniqueCode6;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView phoneNumberTextView;

  @NonNull
  public final TextView resendTextView;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView timerTextView;

  @NonNull
  public final AppCompatButton verifyButton;

  private ActivitySignUpOtpverificationBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView backBtn, @NonNull EditText editTextUniqueCode1,
      @NonNull EditText editTextUniqueCode2, @NonNull EditText editTextUniqueCode3,
      @NonNull EditText editTextUniqueCode4, @NonNull EditText editTextUniqueCode5,
      @NonNull EditText editTextUniqueCode6, @NonNull ImageView imageView2,
      @NonNull ConstraintLayout main, @NonNull TextView phoneNumberTextView,
      @NonNull TextView resendTextView, @NonNull TextView textView10, @NonNull TextView textView7,
      @NonNull TextView timerTextView, @NonNull AppCompatButton verifyButton) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.editTextUniqueCode1 = editTextUniqueCode1;
    this.editTextUniqueCode2 = editTextUniqueCode2;
    this.editTextUniqueCode3 = editTextUniqueCode3;
    this.editTextUniqueCode4 = editTextUniqueCode4;
    this.editTextUniqueCode5 = editTextUniqueCode5;
    this.editTextUniqueCode6 = editTextUniqueCode6;
    this.imageView2 = imageView2;
    this.main = main;
    this.phoneNumberTextView = phoneNumberTextView;
    this.resendTextView = resendTextView;
    this.textView10 = textView10;
    this.textView7 = textView7;
    this.timerTextView = timerTextView;
    this.verifyButton = verifyButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpOtpverificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpOtpverificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up_otpverification, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpOtpverificationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backBtn;
      ImageView backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode1;
      EditText editTextUniqueCode1 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode1 == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode2;
      EditText editTextUniqueCode2 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode2 == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode3;
      EditText editTextUniqueCode3 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode3 == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode4;
      EditText editTextUniqueCode4 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode4 == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode5;
      EditText editTextUniqueCode5 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode5 == null) {
        break missingId;
      }

      id = R.id.editTextUniqueCode6;
      EditText editTextUniqueCode6 = ViewBindings.findChildViewById(rootView, id);
      if (editTextUniqueCode6 == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.phoneNumberTextView;
      TextView phoneNumberTextView = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumberTextView == null) {
        break missingId;
      }

      id = R.id.resendTextView;
      TextView resendTextView = ViewBindings.findChildViewById(rootView, id);
      if (resendTextView == null) {
        break missingId;
      }

      id = R.id.textView10;
      TextView textView10 = ViewBindings.findChildViewById(rootView, id);
      if (textView10 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.timerTextView;
      TextView timerTextView = ViewBindings.findChildViewById(rootView, id);
      if (timerTextView == null) {
        break missingId;
      }

      id = R.id.verifyButton;
      AppCompatButton verifyButton = ViewBindings.findChildViewById(rootView, id);
      if (verifyButton == null) {
        break missingId;
      }

      return new ActivitySignUpOtpverificationBinding((ConstraintLayout) rootView, backBtn,
          editTextUniqueCode1, editTextUniqueCode2, editTextUniqueCode3, editTextUniqueCode4,
          editTextUniqueCode5, editTextUniqueCode6, imageView2, main, phoneNumberTextView,
          resendTextView, textView10, textView7, timerTextView, verifyButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
