package cn.zb.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.zb.page.PageRequest;
import cn.zb.page.Pageable;

/**
 * 允许从controller获取分页参数
 * 
 * @author chen
 *
 */
public interface PageableController {

	int DEFAULT_PAGE_SIZE = 20;

	int DEFAULT_PAGE_NO = 1;

	int DEFAULT_TOTAL_COUNT = 0;

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	default Pageable getPageable(HttpServletRequest request) {


		String pageSize = request.getParameter("pagesize");
		if (StringUtils.isEmpty(pageSize)) {
			pageSize = DEFAULT_PAGE_SIZE + "";
		}

		String pageNo = request.getParameter("pageindex");

		if (StringUtils.isEmpty(pageNo)) {
			pageNo = DEFAULT_PAGE_NO + "";
		}

		String totalCount = request.getParameter("totalCount");
		if (StringUtils.isEmpty(totalCount))
			totalCount = DEFAULT_TOTAL_COUNT + "";

		return new PageRequest(new Integer(pageNo), new Integer(pageSize), new Long(totalCount));
	}

}
