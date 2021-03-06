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
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.alw.holloweffect.R;
import com.example.alw.holloweffect.utils.MeasureUtil;


/**
 * Created by alw on 2017/4/6.
 */

public class DisOutView extends View {
    private Paint mPaint;// 画笔
    Bitmap newbm;
    private PorterDuffXfermode porterDuffXfermode;// 图形混合模式

    private int x, y;// 位图绘制时左上角的起点坐标
    private int screenW, screenH;// 屏幕尺寸

    public DisOutView(Context context) {
        this(context, null);
    }

    public DisOutView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化混合模式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

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
        // 得到新的图片   www.2cto.com

        newbm = Bitmap.createBitmap(bitmapSrc, 0, 0, width, height, matrix, true);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * 将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）我们将在1/3中学习到Canvas的全部用法这里就先follow me
         */
        int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG);
//
//        // 先绘制一层颜色
//        canvas.drawColor(0x000000);

        mPaint.setColor(Color.WHITE);
        RectF r1=new RectF();                         //Rect对象
        r1.left=0;                                 //左边
        r1.top=300;                                  //上边
        r1.right= (float) (screenW * 0.8);                                   //右边
        r1.bottom= (float) (screenH * 0.8 - 300);                              //下边
        canvas.drawRoundRect(r1,60,60,mPaint);

//        // 设置混合模式
        mPaint.setXfermode(porterDuffXfermode);


        mPaint.setColor(Color.parseColor("#00000000"));

        RectF r2=new RectF();                         //Rect对象
        r2.left= (float) (((screenW * 0.8) / 2 ) - 150);                                 //左边
        r2.top=100;                                  //上边
        r2.right= (float) (((screenW * 0.8) / 2 ) + 150);                                   //右边
        r2.bottom=(float) (400);                              //下边
        canvas.drawRoundRect(r2,20,20,mPaint);

        mPaint.setColor(Color.BLUE);
//
//        // 还原混合模式
        mPaint.setXfermode(null);

        //        // 再绘制src源图
        canvas.drawBitmap(newbm, (float) (((screenW * 0.8) / 2 ) - 130), 120, mPaint);

//        // 还原画布
        canvas.restoreToCount(sc);
    }
}