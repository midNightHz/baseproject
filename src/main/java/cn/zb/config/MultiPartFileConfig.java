package cn.zb.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @ClassName:  MultiPartFileConfig   
 * @Description:文件上传配置  
 * @author: 陈军
 * @date:   2018年12月27日 下午4:43:33   
 *     
 * @Copyright: 2018 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Configuration
public class MultiPartFileConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件最大限制
        factory.setMaxFileSize(1024L * 1024 * 100);
        return factory.createMultipartConfig();
    }

}
