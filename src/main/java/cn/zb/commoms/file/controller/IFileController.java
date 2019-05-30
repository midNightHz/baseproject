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
    default Object uploadImg(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception{

       
            if (file == null) {

                return toFailResult("获取文件失败");
            }

            

            CallContext callContext = getCallContext(request);
            if (!uploadAuth(callContext)) {
                return toFailResult("你没有上传的权限");
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


           return toSucessResult(response);

        

    }

}
