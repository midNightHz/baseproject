package cn.zb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName: AppConfig
 * @Description:应用配置类--
 * @author: 陈军
 * @date: 2018年12月27日 下午4:45:20
 * @see cn.zb.config.ApplicationProperties
 * @Copyright: 2018 www.zb-tech.com Inc. All rights reserved.
 *
 */
@Service
@Deprecated
public class AppConfig {

	@Autowired
	ApplicationProperties applicationProperties;

	public Boolean isCross() {
		return applicationProperties.getCrossable();
	}

	public Boolean getNeedLogin() {

		Boolean needLogin = applicationProperties.getNeedLogin();
		return needLogin == null || needLogin;
	}

	public String getImgRootPath() {
		return applicationProperties.getImgRootPath();
	}

	public String getCorpImgPath() {
		return applicationProperties.getCorpImgPath();
	}

	public String getGoodsImgPath() {
		return applicationProperties.getGoodsImgPath();
	}

	public String getAdImgPath() {
		return applicationProperties.getAdImgPath();
	}

	public String getCacheMode() {
		return applicationProperties.getCacheMode();
	}

	public String getLocalUrL() {
		return applicationProperties.getLocalUrl();
	}

	public String getLoginUrl() {
		return applicationProperties.getLoginUrl();
	}

	public boolean getgoodsSubLimit() {
		return applicationProperties.getGoodsSubLimit() != null && applicationProperties.getGoodsSubLimit();
	}

	public int getGoodsSubmitCount() {
		return applicationProperties.getGoodsSubmitCount();
	}

	public boolean getSynAddBaseGoods() {
		return applicationProperties.getSynAddBaseGoods();
	}

	public String getGoodsRepetitionFields() {
		return applicationProperties.getGoodsRepetitionFields();
	}

	public String getCloudUrl() {
		return applicationProperties.getCloudUrl();
	}

	public String getAppKey() {
		return applicationProperties.getAppKey();
	}

	public String getAppSercret() {
		return applicationProperties.getAppSercret();
	}

}
