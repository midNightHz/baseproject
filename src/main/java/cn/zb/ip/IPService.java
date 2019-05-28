package cn.zb.ip;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import cn.zb.config.ApplicationProperties;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.ObjectUtils;
import cn.zb.utils.ipdb.City;

public class IPService {

    private final static String LOCAL_URL_IPV6 = "0:0:0:0:0:0:0:1";
    private static City city;

    private static void init() {

        try {

            if (city == null) {
                synchronized (IPService.class) {

                    ApplicationProperties propeties = BeanFactory.getBean(ApplicationProperties.class);
                    String fileName = propeties.getIpDbFile();
                    InputStream is = IPService.class.getResourceAsStream(fileName);
                    city = new City(is);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getCity(String ip) {

        if (city == null) {
            init();
        }
        try {
            if (ObjectUtils.equals(ip, LOCAL_URL_IPV6))
                ip = "127.0.0.1";
            return Arrays.toString(city.find(ip, "CN"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
