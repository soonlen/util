package com.wzf.com.commutil.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 * @author wzf
 *
 */
public class AnimUtils {
	/**
	 * 
	 * @param fromX
	 * @param toX
	 * @param fromY
	 * @param toY
	 * @param pivotXType
	 * @param pivotXValue
	 * @param pivotYType
	 * @param pivotYValue
	 * @param duration
	 * @return
	 */
	public static ScaleAnimation getScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, int duration) {
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
		animation.setDuration(duration);
		return animation;
	}
	/**
	 * 
	 * @param fromAlpha 开始透明度
	 * @param toAlpha 动画结束透明度
	 * @param duration 时长
	 * @return
	 */
	public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, int duration) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
		alphaAnimation.setDuration(duration);
		return alphaAnimation;
	}

	/**
	 * 获取位移动画
	 * @param fromXDelta
	 * @param toXDelta
	 * @param fromYDelta
	 * @param toYDelta
	 * @param duration
	 * @param repeatCount
	 * @param animationType
     * @return
     */
	public static TranslateAnimation getTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
														   int duration, int repeatCount, int animationType) {
		TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		animation.setDuration(duration);//设置动画持续时间
		animation.setRepeatCount(repeatCount);//设置重复次数
		animation.setRepeatMode(animationType);//设置反方向执行Animation.REVERSE
		return animation;
	}
}
