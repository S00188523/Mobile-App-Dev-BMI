package com.example.lab2bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText weightEditText;
    private EditText heightEditText;
    private Button calculateButton;
    private Button resetButton;
    private double weight; // Declare weight as a member variable
    private double height; // Declare height as a member variable

    // Define a constant for request code
    private static final int RESET_REQUEST_CODE = 1;

    // Declare TextViews for weight and height
    private TextView weightTextView;
    private TextView heightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);


        // Add click listener to the Calculate button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered weight and height as strings
                String weightStr = weightEditText.getText().toString();
                String heightStr = heightEditText.getText().toString();

                if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
                    // Convert the strings to double values
                    weight = Double.parseDouble(weightStr); // Assign to the member variable
                    height = Double.parseDouble(heightStr); // Assign to the member variable

                    // Check if weight and height are within valid ranges
                    if (weight >= 20 && weight <= 200 && height >= 80 && height <= 300) {
                        // Calculate BMI
                        double heightInMeters = height / 100.0;
                        double bmi = weight / (heightInMeters * heightInMeters);

                        // Navigate to the ResultActivity and pass data
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("weight", weight); // Use the key "weight"
                        intent.putExtra("height", height); // Use the key "height"
                        intent.putExtra("bmi", bmi); // Use the key "bmi" for the BMI value
                        startActivityForResult(intent, RESET_REQUEST_CODE); // Start with a request code

                    } else {
                        // Display an error message if input is out of range
                        Toast.makeText(MainActivity.this, "Invalid input values", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Display an error message if either weight or height is empty
                    Toast.makeText(MainActivity.this, "Please enter weight and height", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add click listener to the Reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset weightEditText and heightEditText
                weightEditText.setText("");
                heightEditText.setText("");

                // Reset weightTextView and heightTextView
                weightTextView.setText("Weight: ");
                heightTextView.setText("Height: ");
            }
        });
    }


}
