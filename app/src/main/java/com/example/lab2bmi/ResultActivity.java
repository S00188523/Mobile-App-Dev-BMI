package com.example.lab2bmi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView bmiResultTextView;
    private Button finishButton;
    private TextView bmiMessageTextView;
    private TextView userBmiTextView;
    private ProgressBar underweightProgressBar;
    private ProgressBar healthyProgressBar;
    private ProgressBar overweightProgressBar;
    private View pointerIndicator; // Add this View

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
        pointerIndicator = findViewById(R.id.pointerIndicator); // Initialize the pointer indicator

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

        // Set the user's BMI value on the pointer
        userBmiTextView.setText(String.format("Your BMI: %.2f", bmiValue));

        // Calculate the position of the pointer indicator based on the user's BMI
        int pointerPosition = calculatePointerPosition(underweightProgress, healthyProgress, overweightProgress, bmiValue);

        // Set the layout parameters for the pointer indicator
        LayoutParams params = (LayoutParams) pointerIndicator.getLayoutParams();
        params.leftMargin = pointerPosition;
        pointerIndicator.setLayoutParams(params);

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
            return "You are considered Underweight.";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "You are considered Healthy.";
        } else {
            return "You are considered Overweight.";
        }
    }

    // Calculate the position of the pointer indicator based on BMI value
    private int calculatePointerPosition(int underweightProgress, int healthyProgress, int overweightProgress, double bmi) {
        if (bmi < 18.5) {
            return (int) (underweightProgress + ((bmi / 18.5) * (healthyProgress - underweightProgress)));
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return (int) (healthyProgress + (((bmi - 18.5) / (24.9 - 18.5)) * (overweightProgress - healthyProgress)));
        } else {
            return (int) (overweightProgress + (((bmi - 24.9) / (40 - 24.9)) * (100 - overweightProgress)));
        }
    }
}
