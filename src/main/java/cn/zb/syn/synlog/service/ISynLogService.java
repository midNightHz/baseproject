package cn.zb.syn.synlog.service;

import cn.zb.base.service.BaseService;
import cn.zb.base.service.PageableService;
import cn.zb.syn.synlog.entity.LogSyn;
import cn.zb.syn.synlog.model.LogSynModel;
import cn.zb.syn.synlog.query.LogSynQuery;

public interface ISynLogService
		extends BaseService<LogSyn, Integer>, PageableService<LogSynModel, Integer, LogSynQuery> {

}
