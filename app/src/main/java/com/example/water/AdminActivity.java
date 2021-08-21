package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    private EditText adminEditText;
    private Button adminButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminEditText = (EditText) findViewById(R.id.admin);
        adminButton = (Button) findViewById(R.id.adminButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value="admin@123";
                String key = adminEditText.getText().toString().trim();
                if (key.equals(value)) {
                    startActivity(new Intent(AdminActivity.this, ViewOrder.class));
                } else {
                    Toast.makeText(AdminActivity.this, "Please Provide Valid Key!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}