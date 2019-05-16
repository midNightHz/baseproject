package cn.zb.utils;

import org.apache.commons.lang3.StringUtils;

public abstract class Assert {

    public static void isEmpty(String str, String message) {

        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }

    }

    public static void isNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
