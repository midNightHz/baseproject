package cn.zb.base.controller;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import cn.zb.base.result.JsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component

@Deprecated
public class ErrorHandler implements HandlerExceptionResolver, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private final FastJsonJsonView jsonView = new FastJsonJsonView();

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        logger.error("系统异常", ex);

        jsonView.setExtractValueFromSingleKeyModel(true);

        JsonResult<Object> result = JsonResult.getFailJsonResult(ex.getMessage());
        result.setErrmsg("系统繁忙");

        if (ex instanceof MsgException) {
            msgResult((MsgException) ex, result);
        }

        return new ModelAndView(jsonView).addObject(result);
    }

    private void msgResult(MsgException ex, JsonResult<Object> result) {
        result.setCode(ex.getCode());
        result.setErrmsg(ex.getMessage());

        result.setMsg(null);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
