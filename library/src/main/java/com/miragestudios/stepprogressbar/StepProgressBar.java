package com.miragestudios.stepprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class StepProgressBar extends View {

    private int width, height;
    private int minStepHeight = 20;//class pre-defined variable (in #init())
    private int innerPadding;//user pre-defined variable
    private int backgroundLeftCircleCenterX, backgroundCircleCenterY;
    private int backgroundRightCircleCenterX;
    private int backgroundCircleRadius;
    private int backgroundRectHStart, backgroundRectVStart, backgroundRectHEnd, backgroundRectVEnd;
    private int backgroundRectWidht, backgroundRectHeight;
    private int stepWidth, stepHeight;
    private int firstStepWidth;
    private int[] stepsRectHStart, stepsRectHEnd;
    private int stepRectVStart, stepRectVEnd;
    private int[] stepsRightCircleX;
    private int[] stepsLeftCircleX;
    private int stepCircleY;
    private int stepCircleRadius;
    private int stepOverlay = 3;//class pre-defined variable
    private int backgroundBarColor;//user pre-defined variable
    private int successfulStepColor;//user pre-defined variable
    private int failureStepColor;//user pre-defined variable
    private int stepCount;//user pre-defined variable

    private Paint backgroundPaint, successfulPaint, failurePaint;
    private Rect backgroundRect;
    /**
     * true: step successful , false: step failure , null: step not reached
     */
    private Boolean[] stepSates;

    public StepProgressBar(Context context) {
        super(context);
    }

    public StepProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StepProgressBar,
                defStyleAttr, 0);
        try {
            innerPadding = a.getDimensionPixelSize(R.styleable.StepProgressBar_innerPadding, 24);
            backgroundBarColor = a.getColor(R.styleable.StepProgressBar_backgroundBarColor, 0x493A0D);
            successfulStepColor = a.getColor(R.styleable.StepProgressBar_successfulStepColor, 0xFFC107);
            failureStepColor = a.getColor(R.styleable.StepProgressBar_failureStepColor, 0xB92926);
            stepCount = a.getInteger(R.styleable.StepProgressBar_stepCount, 5);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        minStepHeight = Utils.dpToPx(getContext(), 16);
        stepOverlay = Utils.dpToPx(getContext(), 2);
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundBarColor);
        backgroundPaint.setAlpha(255);

        successfulPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        successfulPaint.setColor(successfulStepColor);
        successfulPaint.setAlpha(255);

        failurePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        failurePaint.setColor(failureStepColor);
        failurePaint.setAlpha(255);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //drawing background left circle
        canvas.drawCircle(backgroundLeftCircleCenterX, backgroundCircleCenterY, backgroundCircleRadius, backgroundPaint);
        //drawing background middle rectangle
        canvas.drawRect(backgroundRect, backgroundPaint);
        //drawing background right circle
        canvas.drawCircle(backgroundRightCircleCenterX, backgroundCircleCenterY, backgroundCircleRadius, backgroundPaint);

        //drawing steps
        for (int i = stepCount - 1; i >= 0; i--) {
            if (stepSates[i] != null){
                //drawing right circle of step
                canvas.drawCircle(stepsRightCircleX[i], stepCircleY, stepCircleRadius, (stepSates[i])? successfulPaint: failurePaint);
                //drawing step rect
                canvas.drawRect(stepsRectHStart[i], stepRectVStart, stepsRectHEnd[i], stepRectVEnd,(stepSates[i])? successfulPaint: failurePaint);
                if (i == 0) {
                    //first step left circle
                    canvas.drawCircle(stepsLeftCircleX[i], stepCircleY, stepCircleRadius, (stepSates[i])? successfulPaint: failurePaint);
                } else {
                    //drawing left hole
//                    canvas.drawCircle(stepsLeftCircleX[i], stepCircleY, stepCircleRadius, backgroundPaint);
                    canvas.drawCircle(stepsRightCircleX[i-1], stepCircleY, stepCircleRadius + innerPadding, backgroundPaint);

                }

            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidthMS, finalHeightMS;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec), heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int suggestedWidth = MeasureSpec.getSize(widthMeasureSpec), suggestedHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = suggestedHeight;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {//not sure about unspecified
//            height = 100;// may need change
            height = suggestedWidth / 5;
//            height = suggestedHeight;// may need change
            Log.e("StepProgressBar", "onMeasure: unspecified height: " + suggestedHeight);
        } else {//MeasureSpec.AT_MOST
            int minReqHeight = getPaddingTop() + getPaddingBottom() + innerPadding * 2 + minStepHeight;
            if (suggestedHeight > minReqHeight) {
                height = Math.min(suggestedHeight, suggestedWidth / 5);
            } else {
                height = minReqHeight;
            }
        }
        stepCircleRadius = minStepHeight / 2;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = suggestedWidth;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {//not sure about unspecified
//            width = 250;// may need change
            width = suggestedWidth;// may need change
            Log.e("StepProgressBar", "onMeasure: unspecified width: " + suggestedWidth);
        } else {//MeasureSpec.AT_MOST
            int minReqWidth = (stepCount * stepCircleRadius * 2) - (stepCount - 1) * stepOverlay +
                    getPaddingRight() + getPaddingLeft() + innerPadding * 2;
            if (suggestedWidth > minReqWidth) {
                width = suggestedWidth;
            } else {
                width = minReqWidth;
            }
        }
        finalHeightMS = resolveSizeAndState(height, heightMeasureSpec, 1);
        finalWidthMS = resolveSizeAndState(width, widthMeasureSpec, 1);

        calculateSizes();

        setMeasuredDimension(finalWidthMS, finalHeightMS);
    }

    private void calculateSizes() {
        //width and height are given value
        int topStart = getPaddingTop();
        int bottomEnd = height - getPaddingBottom();
        int leftStart = getPaddingLeft();
        int rightEnd = width - getPaddingRight();
        backgroundRectHeight = bottomEnd - topStart;
        backgroundRectWidht = rightEnd - leftStart - backgroundCircleRadius * 2;

        backgroundCircleRadius = backgroundRectHeight / 2;
        backgroundLeftCircleCenterX = leftStart + backgroundCircleRadius;
        backgroundCircleCenterY = topStart + backgroundCircleRadius;
        backgroundRightCircleCenterX = rightEnd - backgroundCircleRadius;
//        backgroundRightCircleCenterY = backgroundCircleCenterY;
        backgroundRectHStart = backgroundLeftCircleCenterX;
        backgroundRectHEnd = backgroundRightCircleCenterX;
        backgroundRectVStart = topStart;
        backgroundRectVEnd = bottomEnd;
        stepHeight = backgroundRectHeight - innerPadding * 2;
        stepCircleRadius = stepHeight / 2;
        stepWidth = (rightEnd - leftStart - innerPadding * 2 - stepCircleRadius/*for extra first step widht*/) / stepCount + stepOverlay;
        firstStepWidth = stepWidth + stepCircleRadius;
        stepRectVStart = topStart + innerPadding;
        stepRectVEnd = bottomEnd - innerPadding;
        stepCircleY = topStart + innerPadding + stepCircleRadius;
        stepsRectHStart = new int[stepCount];
        stepsRectHEnd = new int[stepCount];
        for (int i = 0; i < stepCount; i++) {
            if (i == 0) {
                stepsRectHStart[0] = leftStart + innerPadding + stepCircleRadius;
            } else {
                stepsRectHStart[i] = stepsRectHStart[i - 1] + stepWidth - stepOverlay;
            }
            stepsRectHEnd[i] = stepsRectHStart[i] + stepWidth - stepCircleRadius;
        }
        stepsLeftCircleX = stepsRectHStart;
        stepsRightCircleX = stepsRectHEnd;

        backgroundRect = new Rect(backgroundRectHStart, backgroundRectVStart, backgroundRectHEnd, backgroundRectVEnd);
    }

    public void setInnerPadding(int innerPadding) {
        this.innerPadding = innerPadding;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundBarColor = backgroundColor;
    }

    public void setSuccessfulStepColor(int successfulStepColor) {
        this.successfulStepColor = successfulStepColor;
    }

    public void setFailureStepColor(int failureStepColor) {
        this.failureStepColor = failureStepColor;
    }

    public void setStepCount(int stepCount) throws IllegalArgumentException {
        if (stepCount <= 0) {
            throw new IllegalArgumentException("stepCount cannot be less than 1");
        }
        this.stepCount = stepCount;
    }

    public void setStepSates(Boolean[] stepSates) {
        this.stepSates = stepSates;
        invalidate();
        requestLayout();
    }
}
