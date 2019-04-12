package cn.zb.syn.constants;

/**
 * 同步接口常量
 * 
 * @author chen
 *
 */
public interface SynConstants {

	/**
	 * 公司同步上传接口
	 */
	String CORPORATION_SYN_URL = "/zhongbao/mid/receiveStore?token=TOKEN";
	/**
	 * 用户同步上传接口
	 */
	String PERSON_SYN_URL = "/zhongbao/mid/receivePerson?token=TOKEN";
	/**
	 * 商品同步接口
	 */
	String GOODS_SYN_URL = "/zhongbao/mid/receiveGoods?token=TOKEN";
	/**
	 * 订单同步接口
	 */
	String ORDER_SYN_URL = "/zhongbao/mid/receiveSell?token=TOKEN";
	/**
	 * 订单详情上传接口
	 */
	String ORDER_ITEM_SYN_URL = "/zhongbao/mid/receiveSellItems?token=TOKEN";
	/**
	 * 用户列表接口
	 */
	String PERSON_LIST_URL = "";
	/**
	 * 库存更新接口
	 */
	String INVENTORY_UPDATE_URL = "/zhongbao/mid/receiveStock?token=TOKEN";
	/**
	 * 库存进度请求
	 */
	String INVENTORY_UPDATE_RATE = "/zhongbao/mid/queryRate?token=TOKEN&id=ID";

	/**
	 * 获取token接口
	 */
	String GET_TOKEN_URL = "/zhongbao/mid/getToken?zbId=APPID&zbSecret=APPSECRET";

	/**
	 * 会员同步接口
	 */

	String CUSTOMER_SYN_URL = "/zhongbao/mid/receiveCustomer?token=TOKEN";

	/**
	 * 会员信息的查询
	 */

	String CUSTOMER_GET = "/zhongbao/mid/queryCustomer?token=TOKEN&cardNo=CARDNO";

	/**
	 * 卡券的核销接口
	 */

	String TICKET_USE = "/zhongbao/mid/ticket/use?token=TOKEN";

	/**
	 * 卡券的取消接口
	 */

	String TICKET_CANCEL_USE = "/zhongbao/mid/ticket/cancelUse?token=TOKEN";

	/**
	 * 订单下载
	 */
	String BILL_DOWN = "/zhongbao/mid/downloadSells?token=TOKEN&date=DATE";
	
	
	String SYN_ORDER_STATUS="/zhongbao/mid/updateSendFlag?token=TOKEN";

	/**
	 * 同步成功
	 */
	short SYN_SUCCESS = 1;

	/**
	 * 同步失败
	 */
	short SYN_FAILED = 0;

}
