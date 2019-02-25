package com.xxx.xxx.common.util;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 把各个参数按字典排序转MD5码
 */

public class Sign {

    public static String createSign(Map<String, String> params) {
        return createSign(params, false);
    }

    public static String createSign(Map<String, String> params, boolean encode) {
        try {
            Set<String> keysSet = params.keySet();
            Object[] keys = keysSet.toArray();
            Arrays.sort(keys);
            StringBuffer temp = new StringBuffer();
            for (Object key : keys) {
                temp.append(key);
                Object value = params.get(key);
                String valueString = "";
                if (null != value) {
                    valueString = String.valueOf(value);
                }
                if (encode) {
                    temp.append(URLEncoder.encode(valueString, "UTF-8"));
                } else {
                    temp.append(valueString);
                }
            }
            temp.append("appkey" + "66e7ed6a86cb7f1d");
            return AppMD5Util.initMD5(temp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
