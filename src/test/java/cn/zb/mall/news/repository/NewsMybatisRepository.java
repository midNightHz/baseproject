package cn.zb.mall.news.repository;

import org.apache.ibatis.annotations.Mapper;

import cn.zb.base.repository.PageableRepository;
import cn.zb.mall.news.model.NewsModel;
import cn.zb.mall.news.query.NewsQuery;

@Mapper
public interface NewsMybatisRepository extends PageableRepository<NewsModel, Integer, NewsQuery> {

}
