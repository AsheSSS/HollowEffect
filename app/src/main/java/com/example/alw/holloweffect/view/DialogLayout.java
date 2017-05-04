package com.example.alw.holloweffect.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alw.holloweffect.R;
import com.example.alw.holloweffect.utils.MeasureUtil;


/**
 * Created by denny on 2017/4/7 0007.
 */
public class DialogLayout extends LinearLayout {


    private Paint mPaint;

    public DialogLayout(Context context) {
        this(context, null);
    }

    public DialogLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化资源
        initRes(context);
        initView();
    }

    private void initView() {

        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setStrokeWidth(25);
        mPaint.setAntiAlias(true);
        setBackgroundColor(Color.TRANSPARENT);


    }

    private int iconLeft;
    private int iconTop;
    private int iconRight;
    private int iconBottom;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    Bitmap newbm;
    private PorterDuffXfermode porterDuffXfermode;// 图形混合模式

    private int x, y;// 位图绘制时左上角的起点坐标
    private int screenW, screenH;// 屏幕尺寸

    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取位图
        Bitmap bitmapSrc = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);

        int width = bitmapSrc.getWidth();
        int height = bitmapSrc.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) 260) / width;
        float scaleHeight = ((float) 260) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap bitmap = Bitmap.createBitmap(bitmapSrc, 0, 0, width, height, matrix, true);
        newbm = makeRoundCorner(bitmap);
        // 获取包含屏幕尺寸的数组
        int[] screenSize = MeasureUtil.getActicitySize((Activity) context);

        // 获取屏幕尺寸
        screenW = screenSize[0];
        screenH = screenSize[1];

        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
        x = screenW / 2 - bitmapSrc.getWidth() / 2;
        y = screenH / 2 - bitmapSrc.getHeight() / 2;

    }

    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }
//        ZLog.i(TAG, "ps:"+ left +", "+ top +", "+ right +", "+ bottom);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//            if (view instanceof CircleImageView) {
                View view = findViewById(R.id.profile_image);
                iconBottom = view.getBottom();
                iconTop = view.getTop();
                iconRight = view.getRight();
                iconLeft = view.getLeft();
//                break;
//            }
//        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas1 = new Canvas(bitmap);

        canvas1.drawColor(Color.TRANSPARENT);


        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        RectF r1 = new RectF();                         //Rect对象
        r1.left = 0;                                 //左边
        r1.top = (iconBottom - iconTop) / 2;                                  //上边
        r1.right = (float) (w);                                   //右边
        r1.bottom = (float) (h);                              //下边
        canvas1.drawRoundRect(r1, 30, 30, mPaint);


        RectF oval = new RectF();                     //RectF对象
        oval.left = iconLeft;                              //左边
        oval.top = iconTop;                                   //上边
        oval.right = iconRight;                             //右边
        oval.bottom = iconBottom;
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.TRANSPARENT);
        canvas1.drawArc(oval, 0, 360, true, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);

        /*
         * 将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）我们将在1/3中学习到Canvas的全部用法这里就先follow me
         */
       /* int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG);
//
//        // 先绘制一层颜色
        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.WHITE);
        RectF r1 = new RectF();                         //Rect对象
        r1.left = 0;                                 //左边
        r1.top = 300;                                  //上边
        r1.right = (float) (screenW * 0.8);                                   //右边
        r1.bottom = (float) (screenH * 0.8 - 300);                              //下边
        canvas.drawRoundRect(r1, 60, 60, mPaint);

//        // 设置混合模式
        mPaint.setXfermode(porterDuffXfermode);


        mPaint.setColor(Color.parseColor("#00000000"));


//        RectF oval=new RectF();                     //RectF对象
//        oval.left=iconLeft;                              //左边
//        oval.top=iconTop;                                   //上边
//        oval.right=iconRight;                             //右边
//        oval.bottom=iconBottom;
//
//        canvas.drawArc(oval,0,360,true,mPaint);


        RectF r2 = new RectF();                         //Rect对象
        r2.left = (float) (((screenW * 0.8) / 2) - 150);                                 //左边
        r2.top = 100;                                  //上边
        r2.right = (float) (((screenW * 0.8) / 2) + 150);                                   //右边
        r2.bottom = (float) (400);                              //下边
//        canvas.drawRoundRect(r2,20,20,mPaint);
//        canvas.drawCircle((float) ((screenW * 0.8) / 2),200,20,mPaint );
//        canvas.drawOval(r2, mPaint);
        mPaint.setColor(Color.BLUE);
//
//      // 还原混合模式
        mPaint.setXfermode(null);

        // 再绘制src源图
        canvas.drawBitmap(newbm, (float) (((screenW * 0.8) / 2) - 130), 120, mPaint);

//        // 还原画布
        canvas.restoreToCount(sc);*/


    }
}
