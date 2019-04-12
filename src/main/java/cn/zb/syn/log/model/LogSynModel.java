package cn.zb.syn.log.model;

import cn.zb.base.model.BaseModel;
import cn.zb.syn.log.entity.LogSyn;

public class LogSynModel extends LogSyn implements BaseModel<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1659791336032395036L;
	
	private String resultStr;
	
	private String task;

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

}
