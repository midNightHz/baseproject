package cn.zb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: RegUtils
 * @Description:正则工具
 * @author: 陈军
 * @date: 2019年2月18日 上午9:50:30
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public class RegUtils {

	/**
	 * 手机号码正则表达式
	 */
	public static final String MOBILE_PHONE_REG = "^[0]{0,1}1[34578]\\d{9}$";
	/**
	 * 数字的正则表达式
	 */
	public static final String NUMBER_REG = "^[0-9]*$";

	/**
	 * 数字和英文大小写的正则表达式
	 */
	public static final String NUMBER_OR_LETTER = "^[A-Za-z0-9]+$";
	/**
	 * Email 正则表达式
	 */
	public static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	/**
	 * 座机电话正则表达式
	 */
	public static final String TELPHONE_REG = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";

	/**
	 * 18位身份证正则表达式
	 */
	public static final String ID_CARD_18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

	/**
	 * 15位身份证号码表达式
	 */
	public static final String ID_CARD_15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";

	/**
	 * 邮政编码 正则
	 */
	public static final String POSTCODE_REG = "[1-9]\\d{5}(?!\\d)";

	/**
	 * 腾讯QQ正则
	 */
	public static final String TENCENT_QQ_REG = "[1-9][0-9]{4,}";

	/**
	 * 汉字的正则表达式
	 */
	public static final String CHINESE_CHARACTERS_REG = "^[\\u4e00-\\u9fa5]{0,}$";

	/**
	 * 
	 * @Title: isMatch @Description: 用正则来匹配字符串，验证字符串是否合规 @author:陈军 @date 2019年2月18日
	 *         上午9:50:49 @param reg @param content @return boolean @throws
	 */
	public static boolean isMatch(String reg, String content) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}

	public static String getNumberAndLetterReg(int min, int max) {

		if (min < max || min < 1) {
			throw new RuntimeException("正则表达式异常");
		}

		return "^[A-Za-z0-9]{" + min + "," + max + "}$";
	}

	public static void main(String[] args) {
		// String reg = "^[0-9]*[1-9][0-9]*/[0-9]*[1-9][0-9]*$";
		String content = "7676516@QQ.COM";
		System.out.println(content);
		System.out.println(isMatch(EMAIL_REG, content));

	}

}