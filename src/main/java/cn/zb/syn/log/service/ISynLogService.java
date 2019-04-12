package cn.zb.syn.log.service;

import cn.zb.base.service.BaseService;
import cn.zb.base.service.PageableService;
import cn.zb.syn.log.entity.LogSyn;
import cn.zb.syn.log.model.LogSynModel;
import cn.zb.syn.log.query.LogSynQuery;

public interface ISynLogService
		extends BaseService<LogSyn, Integer>, PageableService<LogSynModel, Integer, LogSynQuery> {

}
