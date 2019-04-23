package cn.zb.mall.news.query;

import cn.zb.base.controller.CallContext;
import cn.zb.base.query.BaseQuery;
import cn.zb.page.Pageable;

public class NewsQuery extends BaseQuery {
    
    private String title;
    
    private Integer newsType;

    @Override
    public void init(Pageable pageable, CallContext callContext) {
        super.init(pageable);
    }

    public String getTitle() {
        return title;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }
    
    
    

}
