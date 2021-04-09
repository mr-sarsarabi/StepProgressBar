package com.miragestudios.stepprogressbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StepProgressBar stepProgressBar = findViewById(R.id.step_progress_bar);
        stepProgressBar.setStepCount(5);
        stepProgressBar.setSuccessfulStepColor(0xfff);
        stepProgressBar.setFailureStepColor(0xfff);
        stepProgressBar.setBackgroundColor(0xfff);
        stepProgressBar.setInnerPadding(Utils.dpToPx(this, 6));
        stepProgressBar.setStepSates(new Boolean[]{true, false, true, true, true, false, false, true , true , false , null, null, null}, 2);
        StepProgressBar stepProgressBar2 = findViewById(R.id.step_progress_bar2);
        stepProgressBar2.setStepCount(7);
        stepProgressBar2.setStepSates(new Boolean[]{false, true, true, true, null, null, null});
        StepProgressBar stepProgressBar3 = findViewById(R.id.step_progress_bar3);
        stepProgressBar3.setStepCount(13);
        stepProgressBar3.setStepSates(new Boolean[]{true, true, false, true, true, false, true, true, true, false, true,
                null, null, null, null, null, null
        });
        StepProgressBar stepProgressBar4 = findViewById(R.id.step_progress_bar4);
        stepProgressBar4.setStepCount(20);
        stepProgressBar4.setStepSates(new Boolean[]{true, true, false, true, true, false, true,
                null, null, null, null, null, null, null, null, null, null, null, null, null
        });


    }
}
