package cn.zb.utils;

public class ObjectUtils {

    public static boolean equals(Object a, Object b) {

        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }

        return a.equals(b);

    }

}
