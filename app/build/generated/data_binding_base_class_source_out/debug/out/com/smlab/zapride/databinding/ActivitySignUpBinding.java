// Generated by view binder compiler. Do not edit!
package com.smlab.zapride.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.github.vardemin.materialcountrypicker.PhoneNumberEditText;
import com.smlab.zapride.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText ETConfirmPassword;

  @NonNull
  public final EditText ETPassword;

  @NonNull
  public final PhoneNumberEditText ETPhoneNumber;

  @NonNull
  public final ConstraintLayout backBtn;

  @NonNull
  public final CheckBox customCheckBox;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final AppCompatButton signUpButton;

  @NonNull
  public final TextView termsTextView;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textViewSignIn;

  @NonNull
  public final View view;

  @NonNull
  public final View view2;

  private ActivitySignUpBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText ETConfirmPassword, @NonNull EditText ETPassword,
      @NonNull PhoneNumberEditText ETPhoneNumber, @NonNull ConstraintLayout backBtn,
      @NonNull CheckBox customCheckBox, @NonNull ImageView imageView3,
      @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6,
      @NonNull ConstraintLayout main, @NonNull AppCompatButton signUpButton,
      @NonNull TextView termsTextView, @NonNull TextView textView3, @NonNull TextView textView4,
      @NonNull TextView textView6, @NonNull TextView textViewSignIn, @NonNull View view,
      @NonNull View view2) {
    this.rootView = rootView;
    this.ETConfirmPassword = ETConfirmPassword;
    this.ETPassword = ETPassword;
    this.ETPhoneNumber = ETPhoneNumber;
    this.backBtn = backBtn;
    this.customCheckBox = customCheckBox;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.imageView5 = imageView5;
    this.imageView6 = imageView6;
    this.main = main;
    this.signUpButton = signUpButton;
    this.termsTextView = termsTextView;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView6 = textView6;
    this.textViewSignIn = textViewSignIn;
    this.view = view;
    this.view2 = view2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ETConfirmPassword;
      EditText ETConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (ETConfirmPassword == null) {
        break missingId;
      }

      id = R.id.ETPassword;
      EditText ETPassword = ViewBindings.findChildViewById(rootView, id);
      if (ETPassword == null) {
        break missingId;
      }

      id = R.id.ETPhoneNumber;
      PhoneNumberEditText ETPhoneNumber = ViewBindings.findChildViewById(rootView, id);
      if (ETPhoneNumber == null) {
        break missingId;
      }

      id = R.id.backBtn;
      ConstraintLayout backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.customCheckBox;
      CheckBox customCheckBox = ViewBindings.findChildViewById(rootView, id);
      if (customCheckBox == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.imageView5;
      ImageView imageView5 = ViewBindings.findChildViewById(rootView, id);
      if (imageView5 == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = ViewBindings.findChildViewById(rootView, id);
      if (imageView6 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.signUpButton;
      AppCompatButton signUpButton = ViewBindings.findChildViewById(rootView, id);
      if (signUpButton == null) {
        break missingId;
      }

      id = R.id.termsTextView;
      TextView termsTextView = ViewBindings.findChildViewById(rootView, id);
      if (termsTextView == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textViewSignIn;
      TextView textViewSignIn = ViewBindings.findChildViewById(rootView, id);
      if (textViewSignIn == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      id = R.id.view2;
      View view2 = ViewBindings.findChildViewById(rootView, id);
      if (view2 == null) {
        break missingId;
      }

      return new ActivitySignUpBinding((ConstraintLayout) rootView, ETConfirmPassword, ETPassword,
          ETPhoneNumber, backBtn, customCheckBox, imageView3, imageView4, imageView5, imageView6,
          main, signUpButton, termsTextView, textView3, textView4, textView6, textViewSignIn, view,
          view2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
