package cn.zb.bpm.service;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;

import cn.zb.base.controller.CallContext;

public interface IActivityService {

	String acticityId();

	// 发起任务
	void startTask(CallContext callContext);

	// 任何审核
	void processTask(DelegateExecution execution, CallContext callContext) throws Exception;

	// 获取任务列表

	List<Task> getTasks(CallContext callContext);

	// 完成任务
	void complateTask(boolean isComplate, String taskId, CallContext callContext);
}
