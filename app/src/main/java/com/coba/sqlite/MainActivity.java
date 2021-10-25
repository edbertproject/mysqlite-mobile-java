package com.coba.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;

    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;

    Button loginButton;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelper = new SqliteHelper(this);
        init();

        loginButton.setOnClickListener(view -> {

            if (validateUsername()&&validatePassword()) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                UserModel currentUser = sqliteHelper.Authenticate(new UserModel(null, username, null, password));

                if (currentUser != null) {
                    Snackbar.make(loginButton, "Successfully logged in!", Snackbar.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("us",username);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(loginButton, "Username or password is incorrect, please try again!", Snackbar.LENGTH_LONG).show();
                }
            }

        });
    }

    private void init() {
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        loginButton = findViewById(R.id.login_button);
    }

    private boolean validateUsername() {
        boolean isValid;

        String username = usernameInput.getText().toString();

        if (username.isEmpty()) {
            isValid = false;
            usernameLayout.setError("Please enter valid username!");
        } else if (username.length() >= 5) {
            isValid = true;
            usernameLayout.setError(null);
        } else {
            isValid = false;
            usernameLayout.setError("Username is too shor!");
        }

        return isValid;
    }

    private boolean validatePassword() {
        boolean isValid;

        String password = passwordInput.getText().toString();

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