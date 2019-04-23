package cn.zb.mall.news.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zb.base.controller.BaseController;
import cn.zb.base.controller.IPageableController;
import cn.zb.base.controller.RestController;
import cn.zb.base.service.PageableService;
import cn.zb.mall.news.entity.News;
import cn.zb.mall.news.model.NewsModel;
import cn.zb.mall.news.query.NewsQuery;
import cn.zb.mall.news.service.INewsService;

@Controller
@RequestMapping("news")
public class NewsController extends RestController
        implements BaseController<INewsService, News, Integer>, IPageableController<NewsModel, Integer, NewsQuery> {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    private INewsService newsService;

    @Override
    public INewsService getService() {
        return newsService;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public PageableService<NewsModel, Integer, NewsQuery> getPageableService() {
        return newsService;
    }

}
