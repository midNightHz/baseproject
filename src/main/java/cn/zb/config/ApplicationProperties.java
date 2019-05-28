package cn.zb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

	/**
	 * 系统配置的服务类 --允许其他来源的配置 ，实现类要实现ISystemConfig接口
	 * 
	 * @see cn.zb.config.ISystemConfig
	 */
	private String configServiceClass;

	/**
	 * 是否允许跨域
	 */
	private Boolean crossable = false;
	/**
	 * 本地图片的路径
	 */
	private String localUrl;
	/**
	 * 系统是否需要登录
	 */
	private Boolean needLogin;
	/**
	 * 图片的存放地址
	 */
	private String imgRootPath;
	/**
	 * 企业图片的存放地址
	 */
	private String corpImgPath;
	/**
	 * 商品图片的存放地址
	 */
	private String goodsImgPath;
	/**
	 * 广告图片的存放地址
	 */
	private String adImgPath;
	/**
	 * 自定义缓存的模式
	 */
	private String cacheMode = "delayed";
	/**
	 * 登录页面地址
	 */
	private String loginUrl;
	/**
	 * 订单提交时是否对商品的数量进行限制
	 */
	private Boolean goodsSubLimit;
	/**
	 * 订单提交时商品数量的限制值
	 */
	private int goodsSubmitCount = 100;
	/**
	 * 同步时是否对新增基础商品信息
	 */
	private Boolean synAddBaseGoods;
	/**
	 * 商品排重字段
	 */
	private String goodsRepetitionFields;

	private String cloudUrl;

	private String appKey;

	private String appSercret;

	/**
	 * 图像压缩质量
	 */

	private double imageCompressionQuality = 1.0;

	/**
	 * 默认压缩后图片尺寸
	 */
	private Integer imageDefaultSize = 1980;
	/**
	 * 上传图片是否进行压缩
	 */
	private boolean imageCompression = true;

	private String fileUploadService = "cn.zb.commoms.file.service.impl.DefaultFileService";

	private String ipDbFile = "/ipipfree.ipdb";

	private boolean imageRename = true;

	public Boolean getCrossable() {
		return crossable;
	}

	public void setCrossable(Boolean crossable) {
		this.crossable = crossable;
	}

	public String getLocalUrl() {
		return localUrl;
	}

	public Boolean getNeedLogin() {
		return needLogin;
	}

	public String getImgRootPath() {
		return imgRootPath;
	}

	public String getCorpImgPath() {
		return corpImgPath;
	}

	public String getGoodsImgPath() {
		return goodsImgPath;
	}

	public String getAdImgPath() {
		return adImgPath;
	}

	public String getCacheMode() {
		return cacheMode;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public Boolean getGoodsSubLimit() {
		return goodsSubLimit;
	}

	public int getGoodsSubmitCount() {
		return goodsSubmitCount;
	}

	public Boolean getSynAddBaseGoods() {
		return synAddBaseGoods;
	}

	public String getGoodsRepetitionFields() {
		return goodsRepetitionFields;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}

	public void setNeedLogin(Boolean needLogin) {
		this.needLogin = needLogin;
	}

	public void setImgRootPath(String imgRootPath) {
		this.imgRootPath = imgRootPath;
	}

	public void setCorpImgPath(String corpImgPath) {
		this.corpImgPath = corpImgPath;
	}

	public void setGoodsImgPath(String goodsImgPath) {
		this.goodsImgPath = goodsImgPath;
	}

	public void setAdImgPath(String adImgPath) {
		this.adImgPath = adImgPath;
	}

	public void setCacheMode(String cacheMode) {
		this.cacheMode = cacheMode;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setGoodsSubLimit(Boolean goodsSubLimit) {
		this.goodsSubLimit = goodsSubLimit;
	}

	public void setGoodsSubmitCount(int goodsSubmitCount) {
		this.goodsSubmitCount = goodsSubmitCount;
	}

	public void setSynAddBaseGoods(Boolean synAddBaseGoods) {
		this.synAddBaseGoods = synAddBaseGoods;
	}

	public void setGoodsRepetitionFields(String goodsRepetitionFields) {
		this.goodsRepetitionFields = goodsRepetitionFields;
	}

	public String getConfigServiceClass() {
		return configServiceClass;
	}

	public void setConfigServiceClass(String configServiceClass) {
		this.configServiceClass = configServiceClass;
	}

	public String getCloudUrl() {
		return cloudUrl;
	}

	public void setCloudUrl(String cloudUrl) {
		this.cloudUrl = cloudUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSercret() {
		return appSercret;
	}

	public void setAppSercret(String appSercret) {
		this.appSercret = appSercret;
	}

	public String getIpDbFile() {
		return ipDbFile;
	}

	public void setIpDbFile(String ipDbFile) {
		this.ipDbFile = ipDbFile;
	}

	public double getImageCompressionQuality() {
		return imageCompressionQuality;
	}

	public void setImageCompressionQuality(double imageCompressionQuality) {
		this.imageCompressionQuality = imageCompressionQuality;
	}

	public Integer getImageDefaultSize() {
		return imageDefaultSize;
	}

	public void setImageDefaultSize(Integer imageDefaultSize) {
		this.imageDefaultSize = imageDefaultSize;
	}

	public boolean isImageCompression() {
		return imageCompression;
	}

	public void setImageCompression(boolean imageCompression) {
		this.imageCompression = imageCompression;
	}

	public String getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(String fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public boolean isImageRename() {
		return imageRename;
	}

	public void setImageRename(boolean imageRename) {
		this.imageRename = imageRename;
	}

}
