package com.coba.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    CrudHelper crudHelper;
    Button submitButton, backButton;
    TextView nameText, classText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        crudHelper = new CrudHelper(this);
        nameText = findViewById(R.id.nameTextView);
        classText = findViewById(R.id.classTextView);
        submitButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(view -> {
            crudHelper.insert(new StudentModel(null,nameText.getText().toString(), classText.getText().toString()));
            Toast.makeText(getApplicationContext(), "Successfully create student", Toast.LENGTH_LONG).show();
            ListActivity.current.RefreshList();
            finish();
        });
        backButton.setOnClickListener(view -> finish());
    }
}