package com.example.alw.holloweffect.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * ��湤����
 * 
 * @author Aige
 * @since 2014/11/19
 */
public final class MeasureUtil {
	/**
	 * ��ȡ��Ļ�ߴ�
	 * 
	 * @param activity
	 *            Activity
	 * @return ��Ļ�ߴ�����ֵ���±�Ϊ0��ֵΪ���±�Ϊ1��ֵΪ��
	 */
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}

	public static int[] getActicitySize(Activity activity) {
//		Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
//		return new int[] { defaultDisplay.getWidth(), defaultDisplay.getHeight() };

		Display mDisplay = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		mDisplay.getMetrics(outMetrics);
//		WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
//		layoutParams.width = (int) (outMetrics.widthPixels * 0.8);
//		layoutParams.height = (int) (outMetrics.heightPixels * 0.8);
		return new int[] {outMetrics.widthPixels, outMetrics.heightPixels };
	}



}
