package cn.zb.mall.news.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zb.base.controller.CallContext;
import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.base.repository.PageableRepository;
import cn.zb.mall.news.entity.News;
import cn.zb.mall.news.model.NewsModel;
import cn.zb.mall.news.query.NewsQuery;
import cn.zb.mall.news.repository.NewsJpaRespostiory;
import cn.zb.mall.news.repository.NewsMybatisRepository;
import cn.zb.mall.news.service.INewsService;

@Service
public class NewsService implements INewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private NewsJpaRespostiory newsJpaRepository;

    @Autowired
    private NewsMybatisRepository newsMybatisRepository;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public BaseJpaRepository<News, Integer> getJpaRepository() {
        return newsJpaRepository;
    }

    @Override
    public PageableRepository<NewsModel, Integer, NewsQuery> getPageableRepository() {
        return newsMybatisRepository;
    }

    @Override
    public void initInsert(News t, CallContext callContext) throws Exception {
        t.setCreater(callContext.getUserId());
        t.setNewstime(new Date());
        INewsService.super.initInsert(t, callContext);
    }

}
