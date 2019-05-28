package cn.zb.commoms.file.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zb.commoms.file.service.IFileService;
import cn.zb.commoms.file.service.impl.DefaultFileService;
import cn.zb.config.ApplicationProperties;
import cn.zb.utils.BeanFactory;

public class FileServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceFactory.class);

    public static ApplicationProperties applicationProperties;

    public static IFileService DEFAULT_SERVICE;

    public static IFileService getFileService() {
        if (applicationProperties == null) {
            synchronized (FileServiceFactory.class) {
                init();
            }
        }
        try {
            String className = applicationProperties.getFileUploadService();

            Class<?> cl = Class.forName(className);

            return (IFileService) BeanFactory.getBean(cl);

        } catch (Exception e) {
            logger.warn("获取文件上传服务类异常：{}", e.getMessage());
            return DEFAULT_SERVICE;
        }

    }

    public synchronized static void init() {

        applicationProperties = BeanFactory.getBean(ApplicationProperties.class);

        DEFAULT_SERVICE = BeanFactory.getBean(DefaultFileService.class);

    }
}
