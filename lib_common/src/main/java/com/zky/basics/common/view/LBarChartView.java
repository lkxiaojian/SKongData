package com.zky.basics.common.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;

import android.util.AttributeSet;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zky.basics.common.R;
import com.zky.basics.common.view.Statistics.BarChart;
import com.zky.basics.common.view.Statistics.DragInerfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 *
 */
public class LBarChartView extends FrameLayout {
    protected int defaultBorderColor = Color.argb(255, 217, 217, 217);
    protected int titleTextColor = Color.argb(255, 0, 0, 0);
    protected int xLineColor = Color.argb(255, 219, 219, 219);
    private int blueProgressBarColor = Color.argb(255, 51, 136, 239);
    private int progressBarColor = Color.argb(255, 217, 217, 217);
    protected int labelTextColor;
    protected int mTitleTextSize = 42;
    protected int mLabelTextSize = 20;
    private int avgTitleTextSize = 30;
    protected String mTitle;
    private String mAvgTitle;
    private int mWidth;
    private int mHeight;
    int mLeftTextSpace;
    private int mBottomTextSpace;
    private int mTopTextSpace;
    protected Paint mBorderLinePaint;
    private Double maxData;


    private List<Double> mDatas;

    /**
     * 备注文本画笔
     */
    private Paint mTextPaint;

    private Paint mXXPaint;

    private Paint mAvgTextPaint;

    private Paint progressBar;

    private Paint blueProgressBar;
    private int srcollBar = 0;
    /**
     * 标题文本画笔
     */
    private Paint mTitleTextPaint;

    private BarChart barChartView;
    private int dp24 = 70;
    private int avgLeft=70;
    private int mShowNumber = 6;
    private float perBarW;

    public LBarChartView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    @SuppressLint("CustomViewStyleable")
    private void init(Context context, AttributeSet attrs) {
        mDatas = new ArrayList<>();

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.barCharts);
        defaultBorderColor = t.getColor(R.styleable.barCharts_borderColor, defaultBorderColor);
        titleTextColor = t.getColor(R.styleable.barCharts_titleTextColor, Color.GRAY);
        mTitleTextSize = (int) t.getDimension(R.styleable.barCharts_titleTextSize, mTitleTextSize);
        mLabelTextSize = (int) t.getDimension(R.styleable.barCharts_labelTextSize, mLabelTextSize);
        avgTitleTextSize = (int) t.getDimension(R.styleable.barCharts_avgTitleTextSize, avgTitleTextSize);
        labelTextColor = t.getColor(R.styleable.barCharts_labelTextColor, Color.GRAY);
        xLineColor = t.getColor(R.styleable.barCharts_xlineColor, xLineColor);
        mLeftTextSpace = (int) t.getDimension(R.styleable.barCharts_leftTextSpace, 30);
        mBottomTextSpace = (int) t.getDimension(R.styleable.barCharts_bottomTextSpace, 20);
        mTopTextSpace = (int) t.getDimension(R.styleable.barCharts_topTextSpace, 50);
        mTitle = t.getString(R.styleable.barCharts_title);
        mShowNumber = t.getInteger(R.styleable.barCharts_barShowNumber, 6);

        t.recycle();

        mBorderLinePaint = generatePaint();
        mBorderLinePaint.setColor(defaultBorderColor);

        mBorderLinePaint.setStrokeWidth(AutoSizeUtils.dp2px(context, 1));

        mTextPaint = generatePaint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setColor(labelTextColor);
        mTextPaint.setTextSize(mLabelTextSize);
        mTextPaint.setStrokeWidth(1);

        mTitleTextPaint = generatePaint();
        mTitleTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTitleTextPaint.setColor(titleTextColor);
        mTitleTextPaint.setTextSize(mTitleTextSize);
        mTitleTextPaint.setStrokeWidth(3);

        mAvgTextPaint = generatePaint();
        mAvgTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAvgTextPaint.setColor(titleTextColor);
        mAvgTextPaint.setTextSize(avgTitleTextSize);
        mAvgTextPaint.setStrokeWidth(2);


        DashPathEffect pathEffect = new DashPathEffect(new float[]{15, 5}, 0);

        mXXPaint = generatePaint();
        mXXPaint.setColor(xLineColor);
        mXXPaint.setStrokeWidth(3);
        mXXPaint.setPathEffect(pathEffect);

        progressBar = generatePaint();
        progressBar.setColor(progressBarColor);
        progressBar.setStrokeWidth(AutoSizeUtils.dp2px(context, 4));
        progressBar.setStrokeCap(Paint.Cap.ROUND);

        blueProgressBar = generatePaint();
        blueProgressBar.setColor(blueProgressBarColor);
        blueProgressBar.setStrokeWidth(AutoSizeUtils.dp2px(context, 4));
        blueProgressBar.setStrokeCap(Paint.Cap.ROUND);

        dp24 = AutoSizeUtils.dp2px(context, 24);
        avgLeft=AutoSizeUtils.dp2px(context, 93);
        barChartView = new BarChart(context, attrs);
        LayoutParams parames = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parames.setMargins(mLeftTextSpace+AutoSizeUtils.dp2px(context, 4), mTopTextSpace, mLeftTextSpace, 0);
        barChartView.setLayoutParams(parames);
        barChartView.setBootomDrawPadding(mBottomTextSpace);
        barChartView.setLeftDrawPadding(mLeftTextSpace);
        barChartView.setTopDrawPadding(mTopTextSpace);
        addView(barChartView);
    }


    private Paint generatePaint() {
        Paint m = new Paint();
        m.setAntiAlias(true);
        m.setStyle(Paint.Style.STROKE);
        return m;
    }

    private void setMaxData() {
        Collections.max(mDatas);
        this.maxData = 100.0;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        perBarW = mWidth / mShowNumber;
        if (mTitle != null) {
            canvas.drawText(mTitle, dp24 / 2 - 10,
                    mTopTextSpace - mTitleTextSize + mBottomTextSpace / 2-mTitleTextSize+10, mTitleTextPaint);
        }
        if (mAvgTitle != null) {
            canvas.drawText(mAvgTitle, mWidth - avgLeft,
                    mTopTextSpace - mTitleTextSize + mBottomTextSpace / 2, mAvgTextPaint);
        }


        canvas.translate(mLeftTextSpace, mHeight - mBottomTextSpace);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i <= 5; i++) {
//            String v = String.valueOf(maxData / 5 * i);
            String v=""+(int)(maxData / 5 * i);
            int y = (-mHeight + mBottomTextSpace + mTopTextSpace) / 6 * i;
            canvas.drawText(v,
                    -mLeftTextSpace,
                    y,
                    mTextPaint);
//            if (i != 0) {
//                canvas.drawLine(0, -y, mWidth, -y, mXXPaint);
//            }

        }
        int withX = mWidth - mLeftTextSpace * 2;
        canvas.drawLine(0, dp24, withX, dp24, progressBar);

        if (mDatas.size() <= mShowNumber) {
            canvas.drawLine(-10, dp24, withX + 10, dp24, blueProgressBar);
        } else {


            int i = (int) (srcollBar / perBarW * 1.5) + mShowNumber;
            int stopX = (withX * i / mDatas.size());
            if (stopX > withX) {
                stopX = withX;
            }
            canvas.drawLine(-10, dp24, stopX + 10, dp24, blueProgressBar);
        }


    }


    public void setScroBall(int scroBall) {
        this.srcollBar = scroBall;
        postInvalidate();
    }


    public void setDatas(List<Double> mDatas, List<String> mDesciption, Double avgData, String title, boolean isAnimation) {
        this.mDatas = mDatas;
        setMaxData();
        this.mTitle = title;
        this.mAvgTitle = "平均分数:" + avgData;
        if (mDatas.size() < 6) {
            mShowNumber = mDatas.size();
        }else {
            mShowNumber=6;
        }
;
        postInvalidate();
        barChartView.setDatas(mDatas, mDesciption, avgData, isAnimation);
    }

    public void setDragInerfaces(DragInerfaces dragInerfaces) {
        barChartView.setDragInerfaces(dragInerfaces);
    }


    public void addEndMoreData(List<Double> mDatas, List<String> mDesciption) {
        barChartView.addEndMoreData(mDatas, mDesciption);
    }

//    public void addStartMoreData(List<Double> mDatas, List<String> mDesciption) {
//        barChartView.addStartMoreData(mDatas,mDesciption);
//    }

}
