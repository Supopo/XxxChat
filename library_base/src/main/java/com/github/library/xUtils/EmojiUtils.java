package com.github.library.xUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 苏坡坡要吃婆婆酥 on 2018/10/11.
 */

public class EmojiUtils {
    /**
     * 把中文字符串转换为十六进制Unicode编码字符串
     * 只是将中文字符串和emoji表情转换为十六进制Unicode编码字符串
     */
    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += "\\" + Integer.toHexString(ch);

        }
        return str;
    }

    /**
     * 把中文字符串转换为十六进制Unicode编码字符串
     * 将中文、英文、数字字符串和emoji表情转换为十六进制Unicode编码字符串
     */
    public static String stringToUnicode2(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += "\\u" + Integer.toHexString(ch);
            else
                str += String.valueOf(s.charAt(i));
        }
        return str;
    }

    /**
     * 字符串转换unicode
     * 将输入的每个字符转换为unicode编码字符串
     */
    public static String stringToUnicode3(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * 把十六进制Unicode编码字符串转换为中文字符串
     * 使用正则表达式将Unicode编码字符串转换为string字符串(包括中文、英文、数字、emoji表情)
     */
    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{2,4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * unicode 转字符串
     * 将每个Unicode编码字符串取出转换为string字符串(包括中文、英文、数字、emoji表情)
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }


    /**
     * 字符串换成UTF-8
     * 将String字符串转换为UTF-8编码字符串
     * @param str
     * @return
     */
    public static String stringToUtf8(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     * utf-8换成字符串
     * 将UTF-8编码字符串转换为String字符串
     *
     * @param str
     * @return
     */
    public static String utf8ToString(String str) {
        String result = null;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



}
