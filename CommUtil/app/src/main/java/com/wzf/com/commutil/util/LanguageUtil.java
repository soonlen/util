package com.wzf.com.commutil.util;

import java.util.Date;

/**
 * Created by soonlen on 2016/8/29.
 */
public class LanguageUtil {

    /**
     * 获取当前为周几
     *
     * @param date
     * @return
     */
    public static String getChineseDayOfWeekForDate(Date date) {

        int dayOfWeek = DateU.getDayOfWeek(date);
        if (dayOfWeek == 1) {
            dayOfWeek = 7;
        } else {
            dayOfWeek = dayOfWeek - 1;
        }
        String day = getChineseNumFromNum(dayOfWeek, true);
        return "七".equals(day) ? "日" : day;
    }

    /**
     * 根据阿拉伯数字获取中文简体数字
     * @param num 数字
     * @param simpleCh 简体中文还是繁体中文
     * @return
     */
    public static String getChineseNumFromNum(int num, boolean simpleCh) {
        String[] str;
        if (simpleCh) {
            str = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        } else {
            str = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        }
        //      String ss[] = new String[] { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
        //		String ss[] = new String[] { "个", "十", "百", "千", "万", "十", "百", "千", "亿" };
        String s = String.valueOf(num);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb = sb.append(str[Integer.parseInt(index)]);
        }
        return sb.toString();
    }
}
