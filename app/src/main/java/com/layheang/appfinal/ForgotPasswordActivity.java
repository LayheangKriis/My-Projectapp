package com.layheang.appfinal;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private EditText confirmPasswordET;
    private Button resetPasswordBtn;
    private TextView showHidePassword;
    private TextView showHideConfirmPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI components
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmpasswordET);
        resetPasswordBtn = findViewById(R.id.resetPasswordButton);
        showHidePassword = findViewById(R.id.showHidePassword);
        showHideConfirmPassword = findViewById(R.id.showHideConfirmPassword);

        // Set listener for "Reset Password" button
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();

                if (!email.isEmpty()) {
                    resetPassword(email);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set listener for "Show/Hide Password" button
        showHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(passwordET, showHidePassword);
            }
        });

        // Set listener for "Show/Hide Confirm Password" button
        showHideConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(confirmPasswordET, showHideConfirmPassword);
            }
        });

        // Set listener for "Sign In" button
        TextView signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish this activity and return to the login screen
                finish();
            }
        });
    }

    private void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Error sending password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void togglePasswordVisibility(EditText editText, TextView textView) {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Password is hidden, show it
            editText.setTransformationMethod(null);
            textView.setText("Hide");
        } else {
            // Password is visible, hide it
            editText.setTransformationMethod(new PasswordTransformationMethod());
            textView.setText("Show");
        }
        // Move cursor to the end of the text
        editText.setSelection(editText.getText().length());
    }
}
