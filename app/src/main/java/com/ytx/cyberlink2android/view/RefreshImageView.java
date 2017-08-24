package com.ytx.cyberlink2android.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.scorpio.framework.utils.ScreenUtil;
import com.ytx.cyberlink2android.R;


/**
 * Created by Administrator on 2017/8/18.
 */


public class RefreshImageView extends AppCompatImageView implements Runnable{

    private static final String TAG = "RefreshImageView";


    // 默认的半径
    private int DEFAULT_RADIUS;
    // 默认宽度
    private int DEFAULT_WIDTH;
    // 默认高度
    private int DEFAULT_HEIGHT;

    private int DEFAULT_SPACE = ScreenUtil.dip2px(4);
    private int DEFAULT_STROKE = ScreenUtil.dip2px(1.4f);

    private boolean isRefresh = false;

    private float max = 100;
    private int progress;
    private int startAngle = -90;
    private Paint paint;

    private int color;

    private Thread thread;

    private int beginAngle;
    private volatile int sweepAngle = 40;
    private int speed = 4;
    private static final int PEROID = 10;

    public RefreshImageView(Context context) {
        super(context);
        init();
    }

    public RefreshImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        paint = new Paint();
        color = Color.parseColor("#DBDBDB");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 处理宽高都为 wrap_content 的情况
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
        // 处理宽为 wrap_content 的情况
        else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode != MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFAULT_WIDTH, heightSpecSize);
        }
        // 处理高为 wrap_content 的情况
        else if (heightSpecMode == MeasureSpec.AT_MOST && widthSpecMode != MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, DEFAULT_HEIGHT);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }

        DEFAULT_RADIUS = widthSpecSize / 2;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStrokeWidth(DEFAULT_STROKE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(DEFAULT_STROKE * 3);
        float r = DEFAULT_RADIUS - DEFAULT_STROKE - DEFAULT_SPACE * 2.4f;
        canvas.drawCircle(DEFAULT_RADIUS, DEFAULT_RADIUS, r, paint);

        if (isRefresh) {//旋转时外环
            //外环
            paint.setStrokeWidth(DEFAULT_STROKE);
            RectF oval = new RectF(3 * DEFAULT_STROKE, 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE);
            canvas.drawArc(oval, 0, 360, false, paint);

            //弧
            paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
            paint.setStrokeWidth(DEFAULT_STROKE + 1);
            RectF oval1 = new RectF(3 * DEFAULT_STROKE, 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE);

            canvas.drawArc(oval1, beginAngle, sweepAngle, false, paint);

            //球
            paint.setStrokeWidth(DEFAULT_STROKE);
            paint.setColor(color);
            double x = DEFAULT_RADIUS + (DEFAULT_RADIUS - 3 * DEFAULT_STROKE) * Math.cos((beginAngle + sweepAngle / 2) * Math.PI / 180);
            double y = DEFAULT_RADIUS + (DEFAULT_RADIUS - 3 * DEFAULT_STROKE) * Math.sin((beginAngle + sweepAngle / 2) * Math.PI / 180);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float) x, (float) y, DEFAULT_STROKE * 1.6f, paint);
        } else {
            paint.setStrokeWidth(DEFAULT_STROKE);
            float sweepAngle2 = 360 * progress / max;

            RectF oval = new RectF(3 * DEFAULT_STROKE, 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE, DEFAULT_RADIUS * 2 - 3 * DEFAULT_STROKE);

            canvas.drawArc(oval, startAngle, sweepAngle2, false, paint);

        }

    }

    public void setMaxDistance(float max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        if (!isRefresh) {
            this.progress = progress;
        }
        postInvalidate();
    }

    public float getMax() {
        return max;
    }


    public void isRotate(boolean isRotate) {

        if(isRefresh == isRotate){
            return;
        }

        isRefresh = isRotate;
        if (isRotate) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (isRefresh) {
            long startTime = System.currentTimeMillis();
            beginAngle += speed;
            postInvalidate();
            long time = System.currentTimeMillis() - startTime;
            if (time < PEROID) {
                try {
                    Thread.sleep(PEROID - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
