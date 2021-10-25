package com.coba.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    EditText emailInput;
    EditText usernameInput;
    EditText passwordInput;

    TextInputLayout emailLayout;
    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;

    Button registerButton;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        sqliteHelper = new SqliteHelper(this);
        this.init();

        registerButton.setOnClickListener(view -> {

            if (validateEmail()&&validateUsername()&&validatePassword()) {
                String email = emailInput.getText().toString();
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!sqliteHelper.isEmailExists(email)) {
                    sqliteHelper.addUser(new UserModel(null, username, email, password));
                    Snackbar.make(registerButton, "User created successfully! please login!", Snackbar.LENGTH_LONG).show();
                    new Handler(Looper.getMainLooper()).postDelayed(this::finish, Snackbar.LENGTH_LONG);
                } else {
                    Snackbar.make(registerButton, "User already exists with same email!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(registerButton, "Failed to register!", Snackbar.LENGTH_LONG).show();
            }

        });
    }

    private void init() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> finish());

        emailInput = findViewById(R.id.email_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        emailLayout = findViewById(R.id.email_layout);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        registerButton = findViewById(R.id.register_button);
    }

    private boolean validateEmail() {
        boolean isValid;

        String email = emailInput.getText().toString();

        Log.d("Email",email);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            usernameLayout.setError("Please enter valid email!");
        } else {
            isValid = true;
            usernameLayout.setError(null);
        }

        return isValid;
    }


    private boolean validateUsername() {
        boolean isValid;

        String username = usernameInput.getText().toString();

        Log.d("Username",username);

        if (username.isEmpty()) {
            isValid = false;
            usernameLayout.setError("Please enter valid username!");
        } else if (username.length() >= 5) {
            isValid = true;
            usernameLayout.setError(null);
        } else {
            isValid = false;
            usernameLayout.setError("Username is too short!");
        }

        return isValid;
    }

    private boolean validatePassword() {
        boolean isValid;

        String password = passwordInput.getText().toString();

        Log.d("Password",password);

        if (password.isEmpty()) {
            isValid = false;
            passwordLayout.setError("Please enter valid username!");
        } else if (password.length() >= 5) {
            isValid = true;
            passwordLayout.setError(null);
        } else {
            isValid = false;
            passwordLayout.setError("Username is too shor!");
        }

        return isValid;
    }
}