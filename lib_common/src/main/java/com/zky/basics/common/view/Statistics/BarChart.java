package com.zky.basics.common.view.Statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


import com.zky.basics.common.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class BarChart extends LBaseView {
    private GestureDetector detector;
    protected List<Double> mDatas;
    protected List<String> mDescription;
    private Double mAvgData;
    protected Paint mDataLinePaint;
    protected int defaultLineColor = Color.argb(255, 74, 134, 232);
    protected int descriptionColor;
    protected int dataColor;
    private int mWidth;
    private int mHeight;
    private int mShowNumber;

    private float perBarW;
    private Double maxData;
    private int mMaxScrollx;
    protected int defaultBorderColor = Color.argb(1, 0, 0, 0);
    protected int xxLineColor = Color.argb(255, 219, 219, 219);
    protected Paint mBorderLinePaint;
    protected Paint mxxLinePaint;
    protected Paint mTextPaint;
    protected int descriptionTextSize;
    protected int dataTextSize;
    protected int avgTextSize;
    private int mBottomPadding;
    private int mLeftPadding;
    private int mTopPadding;
    private float textTop = 10;
    protected int xLineColor = Color.argb(255, 219, 219, 219);
    protected int xAVGLineColor;

    private Paint mXXPaint;
    private Paint mAVGPaint;
    private Paint mAVGTextPaint;
    private BarGesture barGesture;

    private DragInerfaces dragInerfaces;

    public BarChart(Context context) {
        super(context);
        init(context, null);
    }


    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint({"CustomViewStyleable", "ResourceAsColor"})
    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.barCharts);
        defaultBorderColor = t.getColor(R.styleable.barCharts_borderColor, defaultBorderColor);
        descriptionTextSize = (int) t.getDimension(R.styleable.barCharts_labelTextSize, 20);
        dataTextSize = (int) t.getDimension(R.styleable.barCharts_dataTextSize, 20);
        descriptionColor = t.getColor(R.styleable.barCharts_descriptionTextColor, Color.GRAY);
        xAVGLineColor = t.getColor(R.styleable.barCharts_avgLineColor, R.color.color_FA6400);
        dataColor = t.getColor(R.styleable.barCharts_dataTextColor, Color.GRAY);
        mShowNumber = t.getInteger(R.styleable.barCharts_barShowNumber, 6);
        avgTextSize = (int) t.getDimension(R.styleable.barCharts_avgTitleTextSize, 20);
        canClickAnimation = t.getBoolean(R.styleable.barCharts_isClickAnimation, false);
        t.recycle();
         barGesture = new BarGesture();
        detector = new GestureDetector(context, barGesture);
        mDatas = new ArrayList<>();
        mDescription = new ArrayList<>();
        mDataLinePaint = new Paint();
        mDataLinePaint.setAntiAlias(true);
        mDataLinePaint.setColor(defaultLineColor);
        mDataLinePaint.setStyle(Paint.Style.STROKE);


        mBorderLinePaint = new Paint();
        mBorderLinePaint.setColor(defaultBorderColor);
        mBorderLinePaint.setStyle(Paint.Style.STROKE);
        mBorderLinePaint.setStrokeWidth(dp2px(2));
        mBorderLinePaint.setAntiAlias(true);

        DashPathEffect pathEffect = new DashPathEffect(new float[]{dp2px(6), dp2px(2)}, 0);

        mxxLinePaint = new Paint();
        mxxLinePaint.setColor(xxLineColor);
        mxxLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mxxLinePaint.setStrokeWidth(dp2px(1));
        mxxLinePaint.setAntiAlias(true);
        mxxLinePaint.setPathEffect(pathEffect);


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextSize(descriptionTextSize);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextSize(descriptionTextSize);
        mTextPaint.setColor(descriptionColor);


        mXXPaint = new Paint();
        mXXPaint.setAntiAlias(true);
        mXXPaint.setColor(xLineColor);
        mXXPaint.setStrokeWidth(3);
        mXXPaint.setPathEffect(pathEffect);


        mAVGPaint = new Paint();
        mAVGPaint.setAntiAlias(true);
        mAVGPaint.setColor(xAVGLineColor);
        mAVGPaint.setStrokeWidth(dp2px(1));
        mAVGPaint.setPathEffect(pathEffect);

        mAVGTextPaint = new Paint();
        mAVGTextPaint.setAntiAlias(true);
        mAVGTextPaint.setColor(xAVGLineColor);
        mAVGTextPaint.setStrokeWidth(4);

        textTop = AutoSizeUtils.dp2px(context, 7);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        setDataLineWidth();
    }

    private synchronized void setDataLineWidth() {
        if (mDatas.size() > 0) {

            if (mDatas.size() < 6) {
                mShowNumber = mDatas.size();
            } else {
                mShowNumber = 6;
            }
            mDataLinePaint.setStrokeWidth(mWidth / (mShowNumber * 2));
            mMaxScrollx = (mWidth / mShowNumber) * mDatas.size() - mWidth;

        }
    }

    private int num = 5;

    @SuppressLint({"ResourceAsColor", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        perBarW = mWidth / mShowNumber;
        Log.e("mMaxScrollx", "mMaxScrollx--" + perBarW + "  mWidth" + mWidth + "mShowNumber--" + mShowNumber);
        canvas.translate(0, mHeight - mBottomPadding);
        setMaxData();
        int lHeight = mHeight - mTopPadding - mBottomPadding;
        for (int i = 1; i < num + 1; i++) {
            int y = -lHeight / num * i;
            canvas.drawLine(0, y, mMaxScrollx + mWidth, y, mxxLinePaint);
        }
        //绘制平均线
        float avgHeight = (float) (-lHeight / maxData * mAvgData);

        canvas.drawLine(0, avgHeight, mMaxScrollx + mWidth, avgHeight, mAVGPaint);
        //平均线 文字
//        int w = mMaxScrollx - dataTextSize - 120;
//        canvas.drawText(avgHeight + "", w, avgHeight - dataTextSize, mAVGTextPaint);
        canvas.drawLine(0, mBorderLinePaint.getStrokeWidth() / 2, mMaxScrollx + mWidth, mBorderLinePaint.getStrokeWidth() / 2, mBorderLinePaint);
        for (int i = 0; i < mDatas.size(); i++) {
//            String perData = String.valueOf(Math.round(scale < 1 ? Math.round(mDatas.get(i) * scale) : mDatas.get(i)));
            String perData = String.valueOf(mDatas.get(i));
            float x = (i + 0.5f) * perBarW;
            float y = (float) ((mHeight - mTopPadding - mBottomPadding) / maxData * mDatas.get(i));
            LinearGradient lg = new LinearGradient(x, 0, x, -y * scale, 0xff31C2EE, 0xff0096FF, Shader.TileMode.CLAMP);
            mDataLinePaint.setShader(lg);
            canvas.drawLine(x, 0, x, -y * scale, mDataLinePaint);
//            RectF rect2 = new RectF(x,-y * scale,100,0);
//            canvas.drawArc(rect2, 0, 180, false, mDataLinePaint);

            canvas.drawText(perData,
                    x - mTextPaint.measureText(perData) / 2,
                    -y * scale - dataTextSize,
                    mTextPaint);

            canvas.drawText(mDescription.get(i),
                    x - mTextPaint.measureText(mDescription.get(i)) / 2,
                    descriptionTextSize + textTop,
                    mTextPaint);
        }
    }

    public void startCliclkAnimation() {
        if (canClickAnimation) {
            animator.start();
        }
    }

    public void setDragInerfaces(DragInerfaces dragInerfaces) {
        this.dragInerfaces = dragInerfaces;
    }

    public void setBootomDrawPadding(int bottomy) {
        mBottomPadding = bottomy;
    }

    public void setLeftDrawPadding(int left) {
        mLeftPadding = left;
    }

    public void setTopDrawPadding(int left) {
        mTopPadding = left;
    }


    private void setMaxData() {
//        Collections.max(mDatas);
        this.maxData = 100.0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
//        if (mDatas.size() <= mShowNumber) {
//            return false;
//        }
        if (detector.onTouchEvent(motionEvent)) {
            return detector.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture(motionEvent);
                break;
            default:
                break;
        }

        return false;
    }

    private void endGesture(MotionEvent motionEvent) {

    }

    private class BarGesture extends GestureDetector.SimpleOnGestureListener {
         int preScrollX = 0;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int scrollX = getScrollX();
            int minScrollX = -scrollX;
            if (scrollX > mMaxScrollx && distanceX > 0) {
                distanceX = 0;
                if (dragInerfaces != null && (scrollX - preScrollX) > 0) {
                    dragInerfaces.onEnd();
                }
            } else {
                if (distanceX < minScrollX) {
                    if (dragInerfaces != null && minScrollX != 0) {
                        dragInerfaces.onStart();
                    }
                    distanceX = minScrollX;
                }
            }
            scrollBy((int) distanceX, 0);

            preScrollX = scrollX;
            if (dragInerfaces != null) {
                dragInerfaces.preScrollX(preScrollX);
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            startCliclkAnimation();
            return super.onSingleTapUp(e);
        }
    }

    public void setDatas(List<Double> mDatas, List<String> mDesciption, Double avgData, boolean isAnimation) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        this.mDescription = mDesciption;
        if (mDatas.size() < 6) {
            mShowNumber = mDatas.size();
        }
        this.mAvgData = avgData;
        setDataLineWidth();
        if (barGesture.preScrollX > 0) {
            scrollBy(-barGesture.preScrollX, 0);
            barGesture.preScrollX = 0;
        }

        if (isAnimation) {
            animator.start();
        } else {
            scale = 1;
            postInvalidate();
        }

    }

    public void addEndMoreData(List<Double> mDatas, List<String> mDesciption) {
        this.mDatas.addAll(mDatas);
        this.mDescription.addAll(mDesciption);
        setDataLineWidth();

        scale = 1;
        postInvalidate();
    }

    private int startX = 0;

    public void addStartMoreData(List<Double> mDatas, List<String> mDesciption) {
        mDatas.addAll(this.mDatas);
        mDesciption.addAll(this.mDescription);
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        this.mDescription.clear();
        this.mDescription.addAll(mDesciption);
//        (mWidth / mShowNumber) *
        startX = (mWidth / mShowNumber) * mDatas.size();
        setDataLineWidth();
        postInvalidate();
    }
}