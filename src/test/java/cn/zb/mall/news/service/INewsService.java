package cn.zb.mall.news.service;

import cn.zb.base.service.BaseService;
import cn.zb.base.service.PageableService;
import cn.zb.mall.news.entity.News;
import cn.zb.mall.news.model.NewsModel;
import cn.zb.mall.news.query.NewsQuery;

public interface INewsService extends BaseService<News, Integer>, PageableService<NewsModel, Integer, NewsQuery> {

}
