package cn.zb.utils;

import java.util.Date;

public class IdUtils {

    public static String getDateId(int random) throws Exception {
        Date now = DateUtil.now();
        StringBuilder sb = new StringBuilder(DateUtil.formartDate(now, "yyyyMMddHHmmss"));
        for (int i = 0; i < random; i++) {
            sb.append(((int) (Math.random() * 10)));
        }
        return sb.toString();
    }

}
