package cn.zb.syn.synlog.repository;

import org.apache.ibatis.annotations.Mapper;

import cn.zb.base.repository.PageableRepository;
import cn.zb.syn.synlog.model.LogSynModel;
import cn.zb.syn.synlog.query.LogSynQuery;
@Mapper
public interface LogSynMybatisRepository extends PageableRepository<LogSynModel, Integer, LogSynQuery> {

}
