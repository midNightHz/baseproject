package cn.zb.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * 数字类操作工具类
 * 
 * @author TangGang 2015.4.24
 * 
 */
public class NumberUtils {
    /** Reusable Long constant for zero. */
    public static final Long LONG_ZERO = new Long(0L);
    /** Reusable Long constant for one. */
    public static final Long LONG_ONE = new Long(1L);
    /** Reusable Long constant for minus one. */
    public static final Long LONG_MINUS_ONE = new Long(-1L);
    /** Reusable Integer constant for zero. */
    public static final Integer INTEGER_ZERO = new Integer(0);
    /** Reusable Integer constant for one. */
    public static final Integer INTEGER_ONE = new Integer(1);
    /** Reusable Integer constant for minus one. */
    public static final Integer INTEGER_MINUS_ONE = new Integer(-1);
    /** Reusable Short constant for zero. */
    public static final Short SHORT_ZERO = new Short((short) 0);
    /** Reusable Short constant for one. */
    public static final Short SHORT_ONE = new Short((short) 1);
    /** Reusable Short constant for minus one. */
    public static final Short SHORT_MINUS_ONE = new Short((short) -1);
    /** Reusable Byte constant for zero. */
    public static final Byte BYTE_ZERO = new Byte((byte) 0);
    /** Reusable Byte constant for one. */
    public static final Byte BYTE_ONE = new Byte((byte) 1);
    /** Reusable Byte constant for minus one. */
    public static final Byte BYTE_MINUS_ONE = new Byte((byte) -1);
    /** Reusable Double constant for zero. */
    public static final Double DOUBLE_ZERO = new Double(0.0d);
    /** Reusable Double constant for one. */
    public static final Double DOUBLE_ONE = new Double(1.0d);
    /** Reusable Double constant for minus one. */
    public static final Double DOUBLE_MINUS_ONE = new Double(-1.0d);
    /** Reusable Float constant for zero. */
    public static final Float FLOAT_ZERO = new Float(0.0f);
    /** Reusable Float constant for one. */
    public static final Float FLOAT_ONE = new Float(1.0f);
    /** Reusable Float constant for minus one. */
    public static final Float FLOAT_MINUS_ONE = new Float(-1.0f);

    public static final Pattern PATTERN = Pattern.compile("0|([-]?[1-9][0-9]*)");

    /**
    * 默认计算比例精度
    */
    public static final int DEFAULT_PRECISION = 2;

    /**
     * @param n
     *            需要判断的数字
     * @return 如果指定的数字为null则返回true
     */
    public static boolean isNull(Number n) {
        return n == null;
    }

    /**
     * @param n
     *            需要判断的数字
     * @return 如果指定的数字为null或为0则返回true
     */
    public static boolean isNullOrZero(Number n) {
        boolean result = isNull(n);

        return result || n.equals(0);
    }

    /**
     * 将指定数四舍五入到指定小数位数
     * 
     * @param data
     *            原始数据
     * @param digits
     *            需要保留的小数据位数
     * @return 四舍五入到指定小数位数的数字
     */
    public static double round(double data, int digits) {
        BigDecimal bd = new BigDecimal(data);

        return bd.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断两个数字是否相等
     * 
     * @param n1
     * @param n2
     * @return 相等返回true,否则false
     * @author TangGang 2015-6-9
     */
    public static boolean equals(Number n1, Number n2) {
        if (n1 == null && n2 == null) {
            return true;
        } else if (n1 != null && n2 == null) {
            return false;
        } else if (n1 == null && n2 != null) {
            return false;
        } else {
            return n1.equals(n2);
        }
    }

    public static boolean equals(Short n1, int n2) {
        if (n1 == null) {
            return false;
        }

        return n1.intValue() == n2;
    }

    public static boolean isNumeric(String str) {

        return org.apache.commons.lang3.math.NumberUtils.isCreatable(str);
    }

    public static String formatNumber(Number number, Integer length) {
        String str = String.format("%0".concat(String.valueOf(length)).concat("d"), number);
        return str;
    }

    /**
     * 将元转换成分
     * 
     * @param fee
     * @return
     */
    public static int formatFee2Fen(double fee) {
        return formatFee2Fen(String.valueOf(fee));
    }

    /**
     * 将元转换成分
     * 
     * @param fee
     * @return
     */
    public static int formatFee2Fen(String fee) {
        if (org.apache.commons.lang3.StringUtils.isBlank(fee) || !isNumeric(fee))
            return 0;

        BigDecimal bd = new BigDecimal(fee);
        return bd.multiply(new BigDecimal("100")).intValue();
    }

    /**
     * 将分转换成元
     * 
     * @param fen
     * @return
     */
    public static double formatFen2Yuan(int fen) {
        return fen / 100.00;
    }

    /**
     * 将分转换成元
     * 
     * @param fen
     * @return
     */
    public static double formatFen2Yuan(String fen) {
        if (org.apache.commons.lang3.StringUtils.isBlank(fen) || !isNumeric(fen))
            return 0.0;

        return Integer.valueOf(fen) / 100.00;
    }

    public static boolean isInt(String str) {
        return PATTERN.matcher(str).matches();
    }

    public static boolean isLong(String str) {
        return PATTERN.matcher(str).matches();
    }

    /**
     * 
     * @Title: computeGrowthRadio   
     * @Description: 计算增长率，返回格式 100.00%   计算逻辑 (a-b)/b
     * @author:陈军
     * @date 2019年5月16日 上午10:15:10 
     * @param a
     * @param b
     * @param precision
     * @return      
     * String      
     * @throws
     */
    public static Double computeGrowthRadio(Number a, Number b) {
        if (b == null || b.doubleValue() == 0.0) {
            return null;
        }
        if (a == null) {
            a = 0.0;
        }
        return computeRatio0(a.doubleValue() - b.doubleValue(), b.doubleValue());
    }

    /**
     * 
     * @Title: computeRatio   
     * @Description:计算两个数值的比例 计算逻辑 a/b  
     * @author:陈军
     * @date 2019年5月16日 上午10:17:02 
     * @param a
     * @param b
     * @param precision
     * @return      
     * String      
     * @throws
     */
    private static Double computeRatio0(Number a, Number b) {

        double result = a.doubleValue() / b.doubleValue();

        return result;
    }

    public static Double computeRatio(Number a, Number b) {
        if (b == null || b.doubleValue() == 0.0) {
            return null;
        }
        if (a == null) {
            a = 0.0;
        }
        return computeRatio0(a, b);

    }

    /**
     * 
     * @Title: formartPercent   
     * @Description: 数字格式化成百分比  
     * @author:陈军
     * @date 2019年5月16日 上午10:41:13 
     * @param number
     * @param precision
     * @return      
     * String      
     * @throws
     */
    public static String formartPercent(Number number, int precision) {
        StringBuilder sb = new StringBuilder("0.");
        for (int i = 0; i < precision; i++) {
            sb.append("0");

        }
        sb.append("%");

        return new DecimalFormat(sb.toString()).format(number);
    }

}
