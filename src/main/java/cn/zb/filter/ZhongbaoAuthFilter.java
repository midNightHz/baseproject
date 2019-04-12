package cn.zb.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.mall.lisence.Lisence;
//import org.mall.lisence.LisenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSONObject;

import cn.zb.commoms.user.entity.User;

@Order(100)
@WebFilter(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "excludeUrls", value = "/zhongbao/user/login;/zhongbao/user/register") })
/**
 * 权限过滤器
 * 
 * @author chen
 *
 */
public class ZhongbaoAuthFilter extends BaseFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ZhongbaoAuthFilter.class);


    /**
     * 过滤器豁免接口
     */
    private List<String> excludeUrlPatterns = Collections.emptyList();

    private static final String EXTENSION_MAPPING_PATTERN = "*.";

    private static final String PATH_MAPPING_PATTERN = "/*";

    private final List<String> exactMatches = new ArrayList<String>();
    /**
     * 豁免的url起始
     */
    private final List<String> startsWithMatches = new ArrayList<String>();

    /**
     * 豁免的url终止
     */
    private final List<String> endsWithMatches = new ArrayList<String>();

    /**
     * 
     * @Title: setExcludeUrlPatterns   
     * @Description: 初始化豁免url 
     * @author:陈军
     * @date 2019年2月14日 下午3:03:58 
     * @param excludeUrlPatterns      
     * void      
     * @throws
     */
    public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
        this.excludeUrlPatterns = new ArrayList<String>(excludeUrlPatterns);

        this.endsWithMatches.clear();
        this.startsWithMatches.clear();
        this.exactMatches.clear();

        for (String urlPattern : excludeUrlPatterns) {
            if (urlPattern.startsWith(EXTENSION_MAPPING_PATTERN)) {
                this.endsWithMatches.add(urlPattern.substring(1, urlPattern.length()));
            } else if (urlPattern.equals(PATH_MAPPING_PATTERN)) {
                this.startsWithMatches.add("");
            } else if (urlPattern.endsWith(PATH_MAPPING_PATTERN)) {
                this.startsWithMatches.add(urlPattern.substring(0, urlPattern.length() - 1));
                this.exactMatches.add(urlPattern.substring(0, urlPattern.length() - 2));
            } else {
                if ("".equals(urlPattern)) {
                    urlPattern = "/";
                }
                this.exactMatches.add(urlPattern);
            }
        }
    }

    /**
     * 
     * @Title: matches   
     * @Description: 当前请求url匹配豁免url  
     * @author:陈军
     * @date 2019年2月14日 下午3:04:19 
     * @param requestPath
     * @return      
     * boolean      
     * @throws
     */
    private boolean matches(String requestPath) {
        for (String pattern : this.exactMatches) {
            if (pattern.equals(requestPath)) {
                return true;
            }
        }
        if (!requestPath.startsWith("/")) {
            return false;
        }
        for (String pattern : this.endsWithMatches) {
            if (requestPath.endsWith(pattern)) {
                return true;
            }
        }
        for (String pattern : this.startsWithMatches) {
            if (requestPath.startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }

    /**
     * 
     * @Title: checkLisence   
     * @Description: 许可校验 
     * @author:陈军
     * @date 2019年2月14日 下午3:04:58 
     * @throws Exception      
     * void      
     * @throws
     */
//    public static void checkLisence() throws Exception {
//        Lisence l = null;
//        try {
//            l = LisenceUtil.getLisence();
//            if (logger.isDebugEnabled()) {
//                logger.debug("软件许可：" + JSONObject.toJSONString(l));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("获取软件许可异常");
//        }
//
//        if (l == null) {
//            throw new Exception("获取软件许可异常");
//        }
//
//        if (l.isTrialVersion()) {
//            if (!VERSION.equals(l.getVersion())) {
//                throw new Exception("获取软件许可异常：错误的版本");
//            }
//            Date date = l.getValidityDate();
//            if (date == null) {
//                throw new Exception("软件已过期");
//            }
//            if (date.getTime() < System.currentTimeMillis()) {
//                throw new Exception("软件已过期");
//            }
//        }
//
//    }

    /**
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // // 获取当前登录用户
        // try {
        // checkLisence();
        // } catch (Exception e) {
        // e.printStackTrace();
        // this.setJsonResponse(response, JsonResult.getFailJsonResult(e.getMessage()));
        // return;
        // }
        try {
            if (request instanceof HttpServletRequest) {
                // 获取当前登录用户
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                String requestPath = httpRequest.getRequestURI();
                HttpSession session = httpRequest.getSession();
                User user = (User) session.getAttribute("user");

                if (logger.isDebugEnabled()) {
                    logger.debug("当前登录用户：{}", JSONObject.toJSON(user));
                }
                if (user == null) {
                    if (!getAppConfig().getNeedLogin()) {
                        user = new User();
                        user.setpLsm(1);
                        user.setRid(0);
                        request.setAttribute("user", user);
                        chain.doFilter(request, response);
                        return;
                    } else {
                        if (matches(requestPath)) {
                            chain.doFilter(request, response);
                            return;
                        } else {
                            this.setJsonResponse(response, "请登录");
                            return;
                        }
                    }
                }

            } else {
                chain.doFilter(request, response);
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            this.setJsonResponse(response, "系统出错");

        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("excludeUrls");
        excludeUrlPatterns = Arrays.asList(urls.split(";"));
        setExcludeUrlPatterns(excludeUrlPatterns);
        logger.info("authFilter init");
    }

}
