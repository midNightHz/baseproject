package cn.zb.base.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zb.base.result.JsonResult;
import cn.zb.base.result.ResultStatus;
import cn.zb.exception.OperNeedAuditException;

@ControllerAdvice
public class ZbExceptionHandler {

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	@ResponseBody
	public JsonResult<?> handler(Exception e) {

		if (e instanceof NullPointerException) {
			return JsonResult.getJsonResult("空指针异常", "失败", ResultStatus.FAIL.getCode());
		}
		if (e instanceof OperNeedAuditException) {
			return JsonResult.getJsonResult("需审核", "成功", ResultStatus.NEEDAUDIT.getCode());

		}
		return JsonResult.getFailJsonResult(e.getMessage());

	}

}
