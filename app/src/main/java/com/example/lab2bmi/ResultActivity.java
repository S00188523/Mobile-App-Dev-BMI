package com.example.lab2bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView bmiResultTextView;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialize UI components
        bmiResultTextView = findViewById(R.id.bmiResultTextView);
        finishButton = findViewById(R.id.finishButton);

        // Retrieve the BMI value from the intent sent by MainActivity
        double bmiValue = getIntent().getDoubleExtra("BMI_VALUE", 0);

        // Display the BMI value in the TextView
        bmiResultTextView.setText(String.format("Your BMI is: %.2f", bmiValue));

        // Add click listener to the Finish button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish this activity and return to MainActivity
                finish();
            }
        });
    }
}
