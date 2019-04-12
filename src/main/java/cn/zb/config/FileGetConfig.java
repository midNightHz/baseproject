package cn.zb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @ClassName:  FileGetConfig   
 * @Description:本地图片下载映射配置  
 * @author: 陈军
 * @date:   2019年1月28日 上午9:02:49   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Configuration
public class FileGetConfig extends WebMvcConfigurerAdapter {
    /**
     *
     * @Title: 文件上传路径映射
     * @Description:储存在 /img/** 路径下的文件映射到:d\\img\\
     * 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:d\\img\\");
        super.addResourceHandlers(registry);
    }
}
