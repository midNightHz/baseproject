package cn.zb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

/**
 * 跨域请求过滤器
 * 
 * @author bee
 *
 */
@Order(1)
@WebFilter(urlPatterns = "/*")
public class CrossFilter extends BaseFilter implements Filter {
    private static Boolean isCross;

    @Override
    public void destroy() {

    }

    private boolean isCross() {
        if (isCross == null) {
            isCross = getAppConfig().isCross();
            if (isCross == null) {
                isCross = false;
            }
        }
        return isCross;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (isCross()) {
            
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
           response.setHeader("Access-Control-Max-Age", "999");
            response.setHeader("Access-Control-Allow-Headers",
                    "Authorization,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("XDomainRequestAllowed", "1");

        }
        
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

}
