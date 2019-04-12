package cn.zb.syn.synlog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.zb.base.controller.CallContext;
import cn.zb.base.controller.PageableController;
import cn.zb.base.controller.RestController;
import cn.zb.page.PageData;
import cn.zb.page.Pageable;
import cn.zb.syn.synlog.entity.LogSyn;
import cn.zb.syn.synlog.model.LogSynModel;
import cn.zb.syn.synlog.query.LogSynQuery;
import cn.zb.syn.synlog.service.ISynLogService;

@Controller
public class SynLogController extends RestController implements PageableController {

	@Autowired
	private ISynLogService synLogService;

	/**
	 * 日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("loglist")
	public ModelAndView logList(HttpServletRequest request, HttpServletResponse response) {
		try {
			CallContext callcontext = this.getCallContext(request);

			Pageable pageable = this.getPageable(request);

			LogSynQuery query = this.fromInputParams(request, LogSynQuery.class);

			PageData<LogSynModel> logs = synLogService.getPageData(query, pageable, callcontext);
			//对日志的字段进行调整
			initLog(logs);
			return new ModelAndView("synlog", "loglist", logs);
		} catch (Exception e) {

			return new ModelAndView("error");
		}
	}

	@RequestMapping("logdetail")
	public ModelAndView logDetail(@RequestParam() Integer id) {
		try {

			LogSyn log = synLogService.findById(id);

			return new ModelAndView("logDetail", "logdetail", log);
		} catch (Exception e) {

			return new ModelAndView("error");
		}
	}

	private void initLog(PageData<LogSynModel> logs) {

		List<LogSynModel> logList = logs.getDatas();
		logList.forEach(e -> {
			String url = e.getSynUrl();
			String str = url.substring(0, url.indexOf("?"));
			str = str.substring(str.lastIndexOf("/") + 1);
			String task = null;
			switch (str) {
			case "receiveSellItems":
				task = "销售单明细同步";
				break;
			case "receiveSell":
				task = "销售单头同步";
				break;
			case "receiveStock":
				task = "库存同步";
				break;
			case "receiveCustomer":
				task = "会员同步";
				break;
			case "receiveGoods":
				task = "商品同步";
				break;
			// receiveStore
			case "receiveStore":
				task = "门店同步";
				break;

			// receivePerson

			case "receivePerson":
				task = "门店店员同步";
				break;

			default:
				break;
			}

			e.setTask(task);
		});

	}

}
