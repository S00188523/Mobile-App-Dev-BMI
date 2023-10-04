package com.example.lab2bmi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView bmiResultTextView;
    private Button finishButton;
    private TextView bmiMessageTextView;
    private TextView userBmiTextView;
    private ProgressBar underweightProgressBar;
    private ProgressBar healthyProgressBar;
    private ProgressBar overweightProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialize UI components
        bmiResultTextView = findViewById(R.id.bmiResultTextView);
        finishButton = findViewById(R.id.finishButton);
        underweightProgressBar = findViewById(R.id.underweightProgressBar);
        healthyProgressBar = findViewById(R.id.healthyProgressBar);
        overweightProgressBar = findViewById(R.id.overweightProgressBar);
        bmiMessageTextView = findViewById(R.id.bmiMessageTextView);
        userBmiTextView = findViewById(R.id.userBmiTextView);

        // Retrieve the BMI value from the intent sent by MainActivity
        double bmiValue = getIntent().getDoubleExtra("bmi", 0); // Use the key "bmi"

        // Calculate the progress for each section of the ProgressBar
        int underweightProgress = (int) ((bmiValue / 18.5) * 33);
        int healthyProgress = 33 + (int) (((bmiValue - 18.5) / (24.9 - 18.5)) * 33);
        int overweightProgress = 66 + (int) (((bmiValue - 24.9) / (40 - 24.9)) * 33);

        // Set the progress and color of each section of the ProgressBar
        setProgressBarColor(underweightProgressBar, underweightProgress, Color.BLUE);
        setProgressBarColor(healthyProgressBar, healthyProgress, Color.GREEN);
        setProgressBarColor(overweightProgressBar, overweightProgress, Color.RED);

        // Set the message based on BMI value
        String bmiMessage = getBmiMessage(bmiValue);
        bmiMessageTextView.setText(bmiMessage);

        // Set the user's BMI value on the TextView
        userBmiTextView.setText(String.format("Your BMI: %.2f", bmiValue));


        // Retrieve the weight and height values from the intent
        double weight = getIntent().getDoubleExtra("weight", 0);
        double height = getIntent().getDoubleExtra("height", 0);

        // Initialize TextViews for weight and height
        TextView weightTextView = findViewById(R.id.weightTextView);
        TextView heightTextView = findViewById(R.id.heightTextView);

        // Display the BMI, weight, and height values in the TextViews
        bmiResultTextView.setText(String.format("Your BMI is: %.2f", bmiValue));
        weightTextView.setText(String.format("Weight: %.2f Kg", weight));
        heightTextView.setText(String.format("Height: %.2f cm", height));

        // Add click listener to the Finish button
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to send the reset values back to MainActivity
                Intent intent = new Intent();
                intent.putExtra("resetWeight", 0.0); // Reset weight to 0.0
                intent.putExtra("resetHeight", 0.0); // Reset height to 0.0
                setResult(RESULT_OK, intent);
                // Finish this activity and return to MainActivity
                finish();
            }
        });
    }

    // Set ProgressBar color and progress
    private void setProgressBarColor(ProgressBar progressBar, int progress, int color) {
        progressBar.setProgress(progress);
        progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
    }


    // Get the BMI message based on BMI value
    private String getBmiMessage(double bmi) {
        if (bmi < 18.5) {
            return "You are considered within the Underweight range.";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "You are considered within the Healthy range.";
        } else {
            return "You are considered in the Overweight range.";
        }
    }


}
