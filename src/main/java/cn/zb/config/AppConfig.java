package cn.zb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName: AppConfig
 * @Description:应用配置类
 * @author: 陈军
 * @date: 2018年12月27日 下午4:45:20
 * 
 * @Copyright: 2018 www.zb-tech.com Inc. All rights reserved.
 *
 */
@Service("appConfig")
@PropertySource("classpath:appConfig.properties")
public class AppConfig {
	/**
	 * 是否允许跨域全局变量
	 */
	@Value("${crossable}")
	private Boolean crossable;

	public Boolean isCross() {
		return crossable;
	}

	/**
	 * 系统是否需要登录，针对不同的环境来配置
	 */
	@Value("${sys.needlogin}")
	private Boolean needLogin;

	public Boolean getNeedLogin() {
		return needLogin == null || needLogin;
	}

	/**
	 * 图片保存的根目录，要与配置的图片映射的本地路径一致
	 */
	@Value("${img.rootpath}")
	private String imgRootPath;

	public String getImgRootPath() {
		return imgRootPath;
	}

	/**
	 * 公司新闻保存子目录
	 */
	@Value("${img.corp.path}")
	private String corpImgPath;

	public String getCorpImgPath() {
		return corpImgPath;
	}

	@Value("${img.good.path}")
	private String goodsImgPath;

	public String getGoodsImgPath() {
		return goodsImgPath;
	}

	@Value("${cache.mode}")
	private String cacheMode;

	public String getCacheMode() {
		return cacheMode;
	}
	@Value("${application.syn.appkey}")
	private String appKey;

	public String getAppKey() {
		return appKey;
	}
	@Value("${application.syn.appsercret}")
	private String appSercret;

	public String getAppSercret() {
		return appSercret;
	}
	@Value("${cloud.url}")
	private String cloudUrl;
	
	public String getCloudUrl() {
		return cloudUrl;
	}
	
	

}
