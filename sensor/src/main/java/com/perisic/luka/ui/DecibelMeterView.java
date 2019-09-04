package com.perisic.luka.ui;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.perisic.luka.R;

import java.util.Stack;

public class DecibelMeterView extends View {

    //region PARAMETERS

    private String PROPERTY_KEY_LINE_LENGTH_PERCENT = DecibelMeterView.class.getSimpleName() + "PROPERTY_LINE_LENGTH";
    private Stack<Integer> lines = new Stack<>();
    private Paint paint = new Paint();
    private boolean startAnimationEnabled = true;
    private boolean notStarted = true;
    private float lineLengthPercent = 0;
    private float linePadding = dpToPx(7);
    private float lineWidth = dpToPx(1);
    private long startAnimationDuration = 700;
    private int numOfLines;
    private int width;
    private int height;
    private int lineNormalColor;
    private int lineAccentColor;

    //endregion

    //region CONSTRUCTORS

    public DecibelMeterView(Context context) {
        super(context);
        init();
    }

    public DecibelMeterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DecibelMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //endregion

    //region PUBLIC METHODS

    public void addLine(float percentage) {
        lines.add(height / 2 - (int) ((((float) height) / 2) * percentage));
        notStarted = false;
        invalidate();
    }

    //endregion

    //region OVERRIDE_METHODS

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!notStarted) {
            drawLines(canvas);
        } else {
            drawStartLines(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        calculateDimensions();
        this.setMeasuredDimension(width, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //endregion

    //region HELPER_METHODS

    private void init() {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineNormalColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : lineNormalColor);
        initValueAnimator();
    }

    private void init(Context context, AttributeSet attrs) {
        findAttributes(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setColor(lineNormalColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : lineNormalColor);
        initValueAnimator();
    }

    private void findAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.DecibelMeterView, 0, 0);
            lineWidth = typedArray.getDimension(R.styleable.DecibelMeterView_lineWidth, lineWidth);
            linePadding = typedArray.getDimension(R.styleable.DecibelMeterView_linePadding, linePadding);
            lineNormalColor = typedArray.getColor(R.styleable.DecibelMeterView_lineNormalColor, lineNormalColor);
            lineAccentColor = typedArray.getColor(R.styleable.DecibelMeterView_lineAccentColor, lineAccentColor);
            startAnimationEnabled = typedArray.getBoolean(R.styleable.DecibelMeterView_startAnimationEnabled, startAnimationEnabled);
            startAnimationDuration = typedArray.getInteger(R.styleable.DecibelMeterView_startAnimationDuration, (int) startAnimationDuration);
            typedArray.recycle();
        }
    }

    private void initValueAnimator() {
        if (startAnimationEnabled) {
            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat(PROPERTY_KEY_LINE_LENGTH_PERCENT, 0, 1);
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setValues(propertyValuesHolder);
            valueAnimator.setDuration(startAnimationDuration);
            valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
            valueAnimator.addUpdateListener((animation) -> {
                lineLengthPercent = (float) animation.getAnimatedValue(PROPERTY_KEY_LINE_LENGTH_PERCENT);
                invalidate();
            });
            valueAnimator.start();
        } else {
            lineLengthPercent = 1;
        }
    }

    private void drawLines(Canvas canvas) {
        if (lines.size() - numOfLines > 0) {
            lines.subList(0, lines.size() - numOfLines).clear();
        }
        for (int i = 0; i < lines.size(); i++) {
            boolean toHigh = (float) lines.get(i) < (0.15 * (float) height) / 2;
            if (toHigh)
                paint.setColor(lineAccentColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimaryDark) : lineAccentColor);
            canvas.drawLine(
                    i * (lineWidth + linePadding),
                    lines.get(i),
                    i * (lineWidth + linePadding),
                    height - lines.get(i),
                    paint

            );
            if (toHigh)
                paint.setColor(lineNormalColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : lineNormalColor);
        }
    }

    private void drawStartLines(Canvas canvas) {
        for (int i = 0; i < lines.size(); i++) {
            boolean toHigh = (float) lines.get(i) < (0.15 * (float) height) / 2;
            if (toHigh) {//set to too loud color
                paint.setColor(lineAccentColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimaryDark) : lineAccentColor);
            }
            canvas.drawLine(
                    i * (lineWidth + linePadding),
                    (((float) height) / 2) * (1 - lineLengthPercent) + lineLengthPercent * (float) lines.get(i),
                    i * (lineWidth + linePadding),
                    (((float) height) / 2) * (1 + lineLengthPercent) - lineLengthPercent * (float) lines.get(i),
                    paint

            );
            if (toHigh) {//reset to default color
                paint.setColor(lineNormalColor == 0 ? ContextCompat.getColor(getContext(), R.color.colorPrimary) : lineNormalColor);
            }
        }
    }

    private void calculateDimensions() {
        numOfLines = width / (int) (linePadding + lineWidth);
        if (lines.size() < numOfLines) {
            for (int i = numOfLines - 1; i >= 0; i--) {
                lines.add((i % 3 + i & 10) * 10);
            }
        }
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    //endregion

}