package com.layheang.appfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private EditText usernameET, emailEditText, passwordET;
    private RadioGroup genderRadioGroup;
    private Button registerBtn;
    private TextView signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();

        // Initialize UI components
        usernameET = findViewById(R.id.usernameET);
        emailEditText = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordET);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        registerBtn = findViewById(R.id.registerBtn);
        signInBtn = findViewById(R.id.signInBtn);

        // Set listener for "Register" button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String username = usernameET.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                // Get selected gender
                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

                // Validate inputs
                if (username.isEmpty()) {
                    usernameET.setError("Username is required");
                    return;
                }

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    return;
                }

                // Check for valid email format
                if (!isValidEmail(email)) {
                    emailEditText.setError("Please enter a valid email address");
                    return;
                }

                if (password.isEmpty()) {
                    passwordET.setError("Password is required");
                    return;
                }

                if (selectedGenderId == -1) {
                    // No gender selected
                    Toast.makeText(RegisterActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get selected gender
                RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                String gender = selectedGenderRadioButton.getText().toString();

                // Create a new user with Firebase Authentication
                registerUser(username, email, password);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regular expression
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void registerUser(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Set the display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileUpdateTask -> {
                                        if (profileUpdateTask.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            // Navigate to next activity
                                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Failed to set username", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
