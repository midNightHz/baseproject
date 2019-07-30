package cn.zb.base.model;

/**
 * 对ID进行加密,解密
 * 
 * @author chen
 *
 * @param <ID>
 */
public interface Encryptable {

	String getDataKey();

	void setDataKey(String dataKey);

	/**
	 * 加密id
	 */
	void encryptId();

	/**
	 * ID解密
	 */
	void decryptId();
}
