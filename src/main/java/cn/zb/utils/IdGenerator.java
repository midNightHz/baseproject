package cn.zb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
public class IdGenerator {

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成验证码
	 * @return
	 */
	public static int generateSmsVerificationCode() {
		return 1 + (int) (Math.random() * ((999999 - 1) + 1));
	}
	
	
	/**
	 * 订单编号
	 * @return
	 */
	public static String getOrderId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 要转换的时间格式
		String checkDateFrom = sdf.format(date);
		return checkDateFrom+System.currentTimeMillis();
	}
	
	
	/**
	 * 商品订单编号
	 * @return
	 */
	public static String getOrderGoodsId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 要转换的时间格式
		String checkDateFrom = "C"+sdf.format(date);
		return checkDateFrom+System.currentTimeMillis();
	}
	
	public static String newId() {
		long time = System.currentTimeMillis();
		String id = Long.toHexString(time) + uuid();
		return id.replaceAll("-", "").substring(0, 32);
	}
	
	
	
	/**
	 * 用户
	 * @return
	 */
	public static String getUserId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 要转换的时间格式
		String checkDateFrom = "YH"+sdf.format(date);
		return checkDateFrom+System.currentTimeMillis();
	}
	
	public static String getBid() {
		return null;
	}
}