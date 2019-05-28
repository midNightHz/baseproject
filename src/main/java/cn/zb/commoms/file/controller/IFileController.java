package cn.zb.commoms.file.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import cn.zb.base.controller.CallContext;
import cn.zb.base.controller.CommonController;
import cn.zb.base.controller.ServiceController;
import cn.zb.commoms.file.factory.FileServiceFactory;
import cn.zb.commoms.file.model.FileUploadVo;
import cn.zb.commoms.file.service.IFileService;
import cn.zb.config.AppConfig;
import cn.zb.utils.BeanFactory;

/**
 * 
 * @ClassName: IFileController
 * @Description:文件的上传的基本接口
 * @auth: 陈军
 * @date: 2018年12月27日 下午2:23:08
 * 
 * @Copyright: 2018 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IFileController extends CommonController {

    /**
     * 
     * @Title: getFileService   
     * @Description: 获取文件上传下载的服务层
     * @author:陈军
     * @date 2018年12月27日 下午2:36:59 
     * @param: @return      
     * @return: IFileService      
     * @throws
     */
    default IFileService getFileService() {

        IFileService fileService = FileServiceFactory.getFileService();
        return fileService;

    }

    /**
     * 
     * @Title: getUploadPath   
     * @Description: 获取当前文件上传的存放路径 
     * @author:陈军
     * @date 2018年12月27日 下午2:37:32 
     * @param: @return      
     * @return: String      
     * @throws
     */
    default String getUploadPath() {
        AppConfig config = BeanFactory.getBean(AppConfig.class);
        String imgRoot = config.getImgRootPath();
        if (StringUtils.isBlank(imgRoot)) {
            return DEFAULT_PATH;
        }
        return imgRoot.endsWith("/") || imgRoot.endsWith("\\") ? imgRoot : imgRoot + "/";
    }

    /**
     * 
     * @Title: uploadAuth   
     * @Description: 是否允许上传文件  
     * @author:陈军
     * @date 2018年12月27日 下午2:38:27 
     * @param: @param callContext
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    boolean uploadAuth(CallContext callContext);

    String DEFAULT_PATH = "./";

    /**
     * 
     * @Title: uploadImg   
     * @Description: 图片上传的业务逻辑  
     * @author:陈军
     * @date 2018年12月27日 下午2:39:31 
     * @param request
     * @param response
     * @param file    上传的文件  
     * void      
     * @throws
     */
    @PostMapping("imgupload")
    default void uploadImg(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {

        ServiceController serviceController = getServiceController();
        try {
            if (file == null) {

                serviceController.writeFailDataJsonToClient(response, "上传失败，当前文件为空");
                return;
            }

            

            CallContext callContext = serviceController.getCallContext(request);
            if (!uploadAuth(callContext)) {
                serviceController.writeFailDataJsonToClient(response, "你没有上传图片的权限");
                return;
            }

            String imagePath = getUploadPath();
            if (imagePath == null) {
                imagePath = DEFAULT_PATH;
            }

            if (!(imagePath.endsWith("/") || imagePath.endsWith("\\"))) {
                imagePath = imagePath + "/";
            }

            IFileService fileService = getFileService();

            FileUploadVo result = fileService.uploadPic(file, imagePath, callContext,request.getContentType());


            serviceController.writeSuccessJsonToClient(response, result);

        } catch (Exception e) {
            getLogger().error("上传图片失败：{}", e.getMessage());
            serviceController.writeFailDataJsonToClient(response, "上传失败");
        }

    }

}
