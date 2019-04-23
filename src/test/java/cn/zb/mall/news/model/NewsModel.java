package cn.zb.mall.news.model;

import cn.zb.base.model.BaseModel;
import cn.zb.mall.news.entity.News;
/**
 * 
 * @ClassName:  NewsModel   
 * @Description:新闻VO对象  
 * @author: 陈军
 * @date:   2019年4月17日 下午12:36:22   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class NewsModel extends News implements BaseModel<Integer>{
    
    private String newsTypeName;
    
    private String createrName;
    
    
    private String qualityType;

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */  
    private static final long serialVersionUID = 995421844363768668L;

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getQualityType() {
        return qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = qualityType;
    }
    
    
}
