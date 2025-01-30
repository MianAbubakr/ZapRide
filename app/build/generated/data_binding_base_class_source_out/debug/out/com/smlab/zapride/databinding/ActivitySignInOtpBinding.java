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

public final class ActivitySignInOtpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout backBtn;

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
  public final ImageView imageView3;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView textView9;

  @NonNull
  public final TextView timerTextView;

  @NonNull
  public final AppCompatButton verifyButton;

  private ActivitySignInOtpBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout backBtn, @NonNull EditText editTextUniqueCode1,
      @NonNull EditText editTextUniqueCode2, @NonNull EditText editTextUniqueCode3,
      @NonNull EditText editTextUniqueCode4, @NonNull EditText editTextUniqueCode5,
      @NonNull EditText editTextUniqueCode6, @NonNull ImageView imageView2,
      @NonNull ImageView imageView3, @NonNull ConstraintLayout main, @NonNull TextView textView10,
      @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9,
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
    this.imageView3 = imageView3;
    this.main = main;
    this.textView10 = textView10;
    this.textView7 = textView7;
    this.textView8 = textView8;
    this.textView9 = textView9;
    this.timerTextView = timerTextView;
    this.verifyButton = verifyButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignInOtpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignInOtpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_in_otp, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignInOtpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backBtn;
      ConstraintLayout backBtn = ViewBindings.findChildViewById(rootView, id);
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

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

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

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = ViewBindings.findChildViewById(rootView, id);
      if (textView9 == null) {
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

      return new ActivitySignInOtpBinding((ConstraintLayout) rootView, backBtn, editTextUniqueCode1,
          editTextUniqueCode2, editTextUniqueCode3, editTextUniqueCode4, editTextUniqueCode5,
          editTextUniqueCode6, imageView2, imageView3, main, textView10, textView7, textView8,
          textView9, timerTextView, verifyButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
