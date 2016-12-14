package net.diskroom.loancalculator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

/**
 * TODO: document your custom view class.
 */
public class DrawView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    //画布宽高度
    private int width = 0;
    private int height = 0;
    //画笔
    private Paint mPaintCircle;
    private Paint mPaintCircle2;
    private TextPaint mTextPaint_;
    //半径
    private int radius;
    //第一个圆心位置
    private float oneCircleX;
    private float oneCircleY;
    //第二个圆心位置
    private float twoCircleX;
    private float twoCircleY;
    //第三个圆心位置
    private float threeCircleX;
    private float threeCircleY;
    //各按钮背景图资源及绘制位置
    private Bitmap car;
    private Bitmap house;
    private Bitmap bao;
    private float carLeft = oneCircleX - 35;
    private float carTop= oneCircleY - 50;
    private float houseLeft = twoCircleX - 35;
    private float houseTop= twoCircleY - 50;
    private float baoLeft = threeCircleX - 35;
    private float baoTop= threeCircleY - 50;
    //各按钮文字绘制位置
    private float carStringX;
    private float carStringY;
    private float houseStringX;
    private float houseStringY;
    private float baoStringX;
    private float baoStringY;

    public DrawView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //初始化文本画笔
        mTextPaint_ = new TextPaint();
        mTextPaint_.setColor(Color.WHITE);
        mTextPaint_.setTextSize(sp2px(getContext(),18));

        //初始化按钮圆形画笔
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.FILL); //实心圆还是空心圆
        mPaintCircle.setAntiAlias(true);         //抗锯齿效果
        mPaintCircle.setColor(getResources().getColor(R.color.colorCircleButton));

        mPaintCircle2 = new Paint();
        mPaintCircle2.setStyle(Paint.Style.FILL); //实心圆还是空心圆
        mPaintCircle2.setAntiAlias(true);         //抗锯齿效果
        mPaintCircle2.setColor(Color.WHITE);


        car = BitmapFactory.decodeResource(getResources(), R.drawable.car50);
        house = BitmapFactory.decodeResource(getResources(), R.drawable.house50);
        bao = BitmapFactory.decodeResource(getResources(), R.drawable.bao50);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DrawView, defStyle, 0);
        mExampleString = a.getString(R.styleable.DrawView_exampleString);
        mExampleColor = a.getColor(R.styleable.DrawView_exampleColor, mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(R.styleable.DrawView_exampleDimension, mExampleDimension);
        if (a.hasValue(R.styleable.DrawView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(R.styleable.DrawView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        a.recycle();
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算画布宽高度
        DrawView dv = (DrawView) findViewById(R.id.drawView);
        height = dv.getHeight();
        width = dv.getWidth();
        //半径
        radius = width / 8;
        //第一个圆心位置
        oneCircleX = width / 2;
        oneCircleY = height / 5 * 2;
        //第二个圆心位置
        twoCircleX = width / 3;
        twoCircleY = height / 5 * 3;
        //第三个圆心位置
        threeCircleX = width / 3 * 2;
        threeCircleY = height / 5 * 3;
        //各按钮文字绘制位置
        carStringX = oneCircleX - mTextPaint_.measureText("车贷")/2;
        carStringY = oneCircleY + radius/2;
        houseStringX = twoCircleX - mTextPaint_.measureText("房贷")/2;
        houseStringY = twoCircleY + radius/2;
        baoStringX = threeCircleX - mTextPaint_.measureText("余额宝")/2;
        baoStringY = threeCircleY + radius/2;
        //各按钮背景图绘制位置
        carLeft = oneCircleX-25;
        carTop = oneCircleY-25;
        //绘制各按钮
        canvas.drawCircle(oneCircleX, oneCircleY, radius, mPaintCircle);
        canvas.drawCircle(twoCircleX, twoCircleY, radius, mPaintCircle);
        canvas.drawCircle(threeCircleX, threeCircleY, radius, mPaintCircle);


        //绘制各按钮背景图
        canvas.drawBitmap(car, carLeft,carTop, null);
        canvas.drawBitmap(house, houseLeft,houseTop, null);
        canvas.drawBitmap(bao, baoLeft,baoTop, null);
        //canvas.drawCircle(oneCircleX,oneCircleY,2,mPaintCircle2);
        LogUtils.v(car.getWidth());
        LogUtils.v(car.getHeight());
        //绘制各按钮文字
        canvas.drawText(getContext().getString(R.string.carButton),carStringX,carStringY,mTextPaint_);
        canvas.drawText(getContext().getString(R.string.houseButton),houseStringX,houseStringY,mTextPaint_);
        canvas.drawText(getContext().getString(R.string.baoButton),baoStringX,baoStringY,mTextPaint_);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    public boolean onTouchEvent(MotionEvent event) {
        // 获取点击屏幕时的点的坐标
        float x = event.getX();
        float y = event.getY();
        //计算点击点到三个按钮圆心的距离
        double carDistance = getDistance(x,y,oneCircleX,oneCircleY);
        double houseDistance = getDistance(x,y,twoCircleX,twoCircleY);
        double baoDistance = getDistance(x,y,threeCircleX,threeCircleY);
        if(carDistance < radius){
            //打开车贷页
            //Toast.makeText(getContext(),"车贷",Toast.LENGTH_LONG).show();
            Intent openCarLoan = new Intent(getContext(),CarLoanActivity.class);
            getContext().startActivity(openCarLoan);
        }
        if(houseDistance < radius){
            //打开房贷页
            Intent openHouseLoan = new Intent(getContext(),HouseLoanActivity.class);
            getContext().startActivity(openHouseLoan);
        }
        if(baoDistance < radius){
            //打开余额宝页
            Intent openBao = new Intent(getContext(),BaoActivity.class);
            getContext().startActivity(openBao);
        }
        return super.onTouchEvent(event);
    }



    /**
     * @param x     起始点x坐标
     * @param y     起始点y坐标
     * @param x1    目的点x坐标
     * @param y1    目的点y坐标
     * @return      两点距离
     */
    private double getDistance(float x,float y,float x1,float y1){
        double distance = Math.sqrt(Math.pow((x1-x),2)+Math.pow((y1-y),2));
        return distance;
    }
}
