package com.wzf.com.commutil.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * Created by soonlen on 2016/8/25.
 */
public class TVUtil {

    /**
     * 在一个textview设置不同的颜色字段
     *
     * @param tv
     * @param texts
     * @param colors
     */
    public static void setColorText(TextView tv, String[] texts, int[] colors) {
        if (null == tv) {
            throw new IllegalArgumentException("控件不能为空！");
        }
        if (null == texts || texts.length == 0) {
            throw new IllegalArgumentException("字符串不能为空！");
        }
        if (null == colors || colors.length == 0) {
            throw new IllegalArgumentException("颜色值不能为空！");
        }
        if (colors.length != texts.length) {
            throw new IllegalArgumentException("字段长度必须等于颜色组的长度！");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < texts.length; i++) {
            sb.append(texts[i]);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        int start = 0;
        int end = 0;
        for (int i = 0; i < colors.length; i++) {
            end = texts[i].length() + start;
            ForegroundColorSpan span = new ForegroundColorSpan(colors[i]);
            builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end;
        }
        tv.setText(builder);
    }

    /**
     * 设置一个textview里面的url字段
     *
     * @param tv
     */
    public static void setUrlTextView(TextView tv, String text) {
        if (null == tv || null == text) {
            throw new IllegalArgumentException("参数不正确！");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        //用超链接标记文本
        builder.setSpan(new URLSpan(text), 0, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 设置一个textview里面的style字段
     *
     * @param tv
     * @param texts
     */
    public static void setStyleTextView(TextView tv, String[] texts, int[] styleIds) {
        if (null == tv || null == texts || styleIds == null || texts.length != styleIds.length) {
            throw new IllegalArgumentException("参数不正确！");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < texts.length; i++) {
            sb.append(texts[i]);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        int start = 0;
        int end = 0;
        for (int i = 0; i < styleIds.length; i++) {
            end = texts[i].length() + start;
            StyleSpan span = new StyleSpan(styleIds[i]);
            builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end;
        }
        //用超链接标记文本
//        Typeface
//        public static final int NORMAL = 0;
//        public static final int BOLD = 1;
//        public static final int ITALIC = 2;
//        public static final int BOLD_ITALIC = 3;
        tv.setText(builder);
    }

    /**
     * //用删除线标记文本,
     *
     * @param tv
     * @param texts
     * @param id
     */
    public static void setStrikethroughTextView(TextView tv, String[] texts, int id) {
        if (null == tv || null == texts || id == -1 || id >= texts.length) {
            throw new IllegalArgumentException("参数不正确！");
        }
        int start = 0;
        int end = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < texts.length; i++) {
            if (i == id) {
                start = sb.length();
            }
            sb.append(texts[i]);
        }
        end = start + texts[id].length();
        L.e("start:" + start + ",end:" + end);
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        //用超链接标记文本
        builder.setSpan(new StrikethroughSpan(), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * //用下划线标记文本,
     *
     * @param tv
     * @param texts
     */
    public static void setUnderlineTextView(TextView tv, String[] texts, int[] ids) {
        if (null == tv || null == texts || ids == null) {
            throw new IllegalArgumentException("参数不正确！");
        }
        int start = 0;
        int end = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < texts.length; i++) {
            sb.append(texts[i]);
        }
        L.e("start:" + start + ",end:" + end);
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        //用超链接标记文本
        for (int i = 0; i < ids.length; i++) {
            int[] inds = getStartEnd(texts, ids[i]);
            builder.setSpan(new UnderlineSpan(), inds[0], inds[1],
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(builder);
    }

    /**
     * 获取一段的起始和结束值
     *
     * @param texts
     * @param id
     * @return
     */
    private static int[] getStartEnd(String[] texts, int id) {
        int start = 0, end = 0;
        StringBuffer info = new StringBuffer();
        for (int i = 0; i <= id; i++) {
            if (id == i) {
                start = info.length();
                end = texts[id].length() + start;
            }
            info.append(texts[i]);
        }
        info.delete(0, info.length());
        return new int[]{start, end};
    }

    /**
     * 图片替换文字
     *
     * @param tv
     * @param texts
     * @param context
     */
    public static void setDrawableText(TextView tv, String[] texts, int[] ids, int[] drawableIds, Context context) {
        if (null == tv || null == texts || (ids != null && drawableIds != null && (ids.length != drawableIds.length)))
            throw new IllegalArgumentException("参数不正确！");

        StringBuffer sb = new StringBuffer();
        int start = 0;
        int end = 0;
        for (int i = 0; i < texts.length; i++) {
            sb.append(texts[i]);
        }
        L.e("start:" + start + ",end:" + end);
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        //用ImageSpan替换文本
        for (int i = 0; i < ids.length; i++) {
            int[] se = getStartEnd(texts, ids[i]);
            //获取Drawable资源
            Drawable d = context.getResources().getDrawable(drawableIds[i]);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            //创建ImageSpan
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            builder.setSpan(span, se[0], se[1], Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv.setText(builder);
        tv.setMovementMethod(LinkMovementMethod.getInstance()); //实现文本的滚动
    }

}
