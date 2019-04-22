package cn.zb.base.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;
import cn.zb.base.service.IExportService;

public interface IExportController<Q extends BaseQuery, T extends BaseModel<ID>, ID extends Serializable>
		extends CommonController {

	IExportService<T, ID, Q> getExportService();

	String getErrorPage();

	/**
	 * 
	 * @Title: downLoad @Description: 导出 @author:陈军 @date 2019年2月14日
	 *         下午5:12:17 @param request @param response void @throws
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("export")
	default void downLoad(HttpServletRequest request, HttpServletResponse response) {
		try {
			Class<?> queryClass = getExportService().queryClass();
			if (queryClass == null) {
				getServiceController().writeFailDataJsonToClient(response, "获取参数异常");
			}
			// 实例化查询对象
			Q q = (Q) getServiceController().fromInputParams(request, queryClass);
			if (q == null) {
				// 查询对象为空的情况
				q = (Q) queryClass.newInstance();
			}
			downLoad(request, response, q);
		} catch (Exception e) {
			getServiceController().writeFailDataJsonToClient(response, "导出异常");
		}
	}

	/**
	 * 
	 * @Title: downLoad @Description: 导出excel的具体业务逻辑 @author:陈军 @date 2019年2月14日
	 *         下午5:12:39 @param request @param response @param q void @throws
	 */
	default void downLoad(HttpServletRequest request, HttpServletResponse response, Q q) {

		OutputStream os = null;
		try {

			CallContext callContext = getServiceController().getCallContext(request);
			// DEBUG日志
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("querys:" + JSONObject.toJSONString(q));
				getLogger().debug("callContext:" + JSONObject.toJSONString(callContext));
			}
			// 导出时需要修改contentType和附件名
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=export.xls");

			os = response.getOutputStream();
			getExportService().export(q, os, callContext);
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().error("导出异常 msg:{}", e.getMessage());
			try {
				response.sendRedirect(getErrorPage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
