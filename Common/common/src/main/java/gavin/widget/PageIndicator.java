package gavin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class PageIndicator extends View implements ViewPager.OnPageChangeListener
{
    // 指示器的数量
    private int mIndicatorCount = 5;
    private int mIndicatorColor = Color.WHITE;
    // 指示器大小，默认12像素
    private float mIndicatorSize = 20;
    // 指示器之间的边距
    private float mIndicatorSpacing = 15;

    // 被选择的指示器，默认是第一个
    private int mSelectedIndicator = -1;

    // 内边距
    private int mPadding = 0;

    // 控件的宽度和高度
    private int mWidth;
    private int mHeight;

    private Paint mOnPaint;
    private Paint mOffPaint;

    public PageIndicator(Context context)
    {
        super(context);

        init();
    }

    private void init()
    {
        mOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public PageIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (mIndicatorCount > 0)
        {
            // 如果指示器数量>0，那么开始居中绘制
            // 我们需要的宽度
            final int needWidth = (int) (mIndicatorSize * mIndicatorCount + mIndicatorSpacing * (mIndicatorCount - 1) + 0.5f) + mPadding * 2;
            // 居中
            final float startWidthOffset = (mWidth - needWidth) / 2.0f;
            // 我们需要的高度
            final int needHeight = (int) (mIndicatorSize + 0.5f) + mPadding * 2;
            // 居中
            final float startHeightOffset = (mHeight - needHeight) / 2.0f;

            for (int i = 0; i < mIndicatorCount; i++)
            {

                float cx = startWidthOffset + mPadding + (mIndicatorSize * i) + mIndicatorSize / 2 + mIndicatorSpacing * i;

                float cy = startHeightOffset + mIndicatorSize / 2;

                if (i == mSelectedIndicator)
                {
                    canvas.drawCircle(cx, cy, mIndicatorSize / 2, mOnPaint);
                }
                canvas.drawCircle(cx, cy, mIndicatorSize / 2, mOffPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // 我们需要的尺寸
        int w = (int) (mIndicatorSize * mIndicatorCount + mIndicatorSpacing * (mIndicatorCount - 1) + 0.5f) + mPadding * 2 + getSuggestedMinimumWidth();
        int h = (int) (mIndicatorSize + 0.5f) + mPadding * 2 + getSuggestedMinimumHeight();
        // 布局给的宽度
        int widthGiven = MeasureSpec.getSize(widthMeasureSpec);
        // 获取布局提供的宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode)
        {
            case MeasureSpec.AT_MOST:
                // 想要多大就有多大
                // 这个时候也不用太贪
                break;
            case MeasureSpec.EXACTLY:
                // 我们需要的更多
                // 尝试缩小mIndicatorSize
                while (w > widthGiven)
                {
                    mIndicatorSize--;
                    mIndicatorSpacing--;
                    w = (int) (mIndicatorSize * mIndicatorCount + mIndicatorSpacing * (mIndicatorCount - 1) + 0.5f);
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        int heightGiven = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (heightMode)
        {
            case MeasureSpec.EXACTLY:
                while (h > heightGiven)
                {
                    mIndicatorSize--;
                    mIndicatorSpacing--;
                    h = (int) (mIndicatorSize + 0.5f);
                    if (h <= 0)
                    {
                        throw new RuntimeException("这是被逼死的节奏啊！");
                    }
                }
                break;
        }

        // 最终确定的大小
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        // 这里没有检查新的尺寸是否能够显示得下所有指示器
    }

    /**
     * 设置指示器的数量
     *
     * @param count
     */
    public void setIndicatorCount(int count)
    {
        mIndicatorCount = count;
        invalidate();
    }

    /**
     * 设置指示器颜色
     *
     * @param color
     */
    public void setIndicatorColor(int color)
    {
        mIndicatorColor = color;
        mOnPaint.setColor(mIndicatorColor);
        mOnPaint.setAlpha(255);
        mOffPaint.setColor(0xF3F7FB);
        mOffPaint.setAlpha(128);
        invalidate();
    }

    /**
     * 设置指示器大小
     *
     * @param size
     */
    public void setIndicatorSize(float size)
    {
        mIndicatorSize = size;
        invalidate();
    }

    public void setIndicatorSpacing(float spacing)
    {
        mIndicatorSpacing = spacing;
        invalidate();
    }

    public void moveIndicator(int position)
    {
        if (position < 0)
        {
            position = 0;
        }
        if (position > mIndicatorCount - 1)
        {
            position = mIndicatorCount - 1;
        }
        mSelectedIndicator = position;
        invalidate();
    }

    public void setPadding(int padding)
    {
        mPadding = padding;
        invalidate();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        moveIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
