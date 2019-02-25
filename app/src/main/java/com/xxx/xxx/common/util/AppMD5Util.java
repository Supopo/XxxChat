package com.xxx.xxx.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class AppMD5Util {
    /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 对字符串md5加密(大写+数字)
     *
     * @param //str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */

    public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  针对单独项目
     * @return
     * @throws Exception
     */
    public static String initMD5(String pwd) throws Exception {
     String[] digital = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        //将明文经过MD5加密后变成16的字节数组---->32位的字符串（16进制）
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(pwd.getBytes("UTF-8"));
        //用来保存最终的密文
        String digest = "";
        for (byte b : bytes) {
            int tmp = b;
            if(tmp<0) {
                tmp += 256;
            }
            //经过判断后tmp一定是正整数
            int index = tmp/16;
            int index1 = tmp%16;
            digest += digital[index]+digital[index1];
        }
        return digest;
    }

    public static String finalMd5(String pwd) throws Exception {
        return initMD5(initMD5(initMD5(initMD5(pwd)+"北特科技"+"admin")+"北特科技"+"admin")+"北特科技"+"admin");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(finalMd5("123456"));
    }
}
