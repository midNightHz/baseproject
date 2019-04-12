package cn.zb.syn.log.repository;

import org.apache.ibatis.annotations.Mapper;

import cn.zb.base.repository.PageableRepository;
import cn.zb.syn.log.model.LogSynModel;
import cn.zb.syn.log.query.LogSynQuery;
@Mapper
public interface LogSynMybatisRepository extends PageableRepository<LogSynModel, Integer, LogSynQuery> {

}
