package com.wzf.com.commutil.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，方法签名一定要直白、简洁，尽量作到不需要注释
 * 
 * 
 */
public class StringU {
    
    public static String removeSpecilChar(String str) {
        String result = "";
        if (null != str) {
            Pattern pat = Pattern.compile("\\s*|\n|\r|\t");
            Matcher mat = pat.matcher(str);
            result = mat.replaceAll("");
        }
        return result;
    }

    public static boolean isNumber(String s) {
        String check = "[-]?\\d+";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(s);
        return matcher.matches();
    }
    
    
    public static List<String> getSubString(String str, int div){
        int length = str.length();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < length;){
            String divStr = str.substring(i, i + div);
            list.add(divStr);
            i = i + div;
        }
        return list;
    }
    /**
     * 根据数组组装字符串，以逗号隔开
     * 
     * @param strArr
     * @return
     */
    public static String getStr(String[] strArr) {
        String str = "";
        for (int i = 0; i < strArr.length; i++) {
            if (i == 0) {
                str = "'" + strArr[i] + "'";
            } else {
                str = str + ",'" + strArr[i] + "'";
            }
        }
        return str;
    }
    public static boolean isBlank(String s) {
        return s == null || s.trim().equals("");
    }


    public static boolean isEmpty(String arg)
    {
        return arg == null || "".equals(arg.trim());
    }
    
    public static boolean isBlankForInput(String s) {
        return s == null || s.trim().equals("") || "yyyy-mm-dd".equals(s);
    }

    public static String trim(String s) {
        return s == null ? null : s.trim();
    }
    
    public static String trim(String s, String defaultValue) {
        return s == null ? defaultValue : s.trim();
    }

    public static String upper(String s) {
        return s == null ? null : s.toUpperCase();
    }

    public static String lower(String s) {
        return s == null ? null : s.toLowerCase();
    }

    public static String nullToEmpty(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    public static boolean equals(String s1, String s2) {
        if (s1 != null)
            return s1.equals(s2);
        else if (s2 != null)
            return s2.equals(s1);
        else
            return true;
    }

    public static int compare(String s1, String s2) {
        if (s1 == null && s2 == null)
            return 0;
        else if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;
        else
            return s1.compareTo(s2);
    }

    public static int compareToIgnoreCase(String s1, String s2) {
        if (s1 == null && s2 == null)
            return 0;
        else if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;
        else
            return s1.compareToIgnoreCase(s2);
    }

    public static boolean equalsText(String s1, String s2) {
        if (s1 != null)
            return s1.equalsIgnoreCase(s2);
        else if (s2 != null)
            return s2.equalsIgnoreCase(s1);
        else
            return true;
    }

    public static boolean equalsAny(String s, Object... texts) {
        for (Object o : texts) {
            String text = o == null ? null : o.toString();
            if (equals(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAny(String s, String... texts) {
        for (String text : texts) {
            if (equals(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAny(String s, Collection<?> texts) {
        if (texts != null) {
            for (Object o : texts) {
                String text = o == null ? null : o.toString();
                if (equals(s, text))
                    return true;
            }
        }

        return false;
    }

    public static boolean equalsAnyText(String s, Object... texts) {
        for (Object o : texts) {
            String text = o == null ? null : o.toString();
            if (equalsText(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAnyText(String s, String... texts) {
        for (String text : texts) {
            if (equalsText(s, text))
                return true;
        }

        return false;
    }

    public static boolean equalsAnyText(String s, Collection<?> texts) {
        if (texts != null) {
            for (Object o : texts) {
                String text = o == null ? null : o.toString();
                if (equalsText(s, text))
                    return true;
            }
        }

        return false;
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    public static String uuid() {
        String ret = UUID.randomUUID().toString();
        return ret.replaceAll("-", "");
    }

    /**
     * 拼接REST形式的url，eg: <br>
     * linkUrl("http://127.0.0.1/", "/1") -> http://127.0.0.1/1<br>
     * linkUrl("http://127.0.0.1", "1") -> http://127.0.0.1/1<br>
     * 
     * @param urls
     * @return
     */
    public static String linkUrl(String... urls) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0, len = urls.length; i < len; i++) {
            String s = urls[i];
            if (s == null)
                s = "";

            if (i < len - 1) {
                while (s.endsWith("/")) {
                    s = s.substring(0, s.length() - 1);
                }
            }

            if (i > 0) {
                while (s.startsWith("/")) {
                    s = s.substring(1);
                }
            }

            if (ret.length() > 0 && s.length() > 0)
                ret.append("/");
            ret.append(s);
        }
        return ret.toString();
    }

    // 1、正则表达式
    public static boolean isNumeric1(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    // 为整数
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            ("异常：\"" + str + "\"不是数字/整数...");
            return false;
        }
    }
    /**
     * 校验身份证号是否合法
     * @param cardNumber
     * @return
     */
    public static boolean isCardNumber(String cardNumber){
        String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(cardNumber);
        return (m.matches());
    }
    /**
     * 得到UTF-8字符串
     * @param str
     * @return
     */
    public static String getUTF8String(String str) {
    	try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
}
