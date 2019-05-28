package cn.zb.commoms.file.service;

import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.zb.base.controller.CallContext;
import cn.zb.commoms.file.model.FileUploadVo;

/**
 * 文件上传下载相关服务类
 * 
 * @author chen
 *
 */
public interface IFileService {

   /**
    * 
    * @Title: uploadFile   
    * @Description: We  
    * @author:陈军
    * @date 2019年5月22日 上午8:29:17 
    * @param file
    * @param urlPath
    * @param callContext
    * @param contentType
    * @return
    * @throws Exception      
    * PicUpdateVo      
    * @throws
    */
    FileUploadVo uploadFile(MultipartFile file, String urlPath, CallContext callContext,String contentType) throws Exception;
    /**
     * 
     * @Title: uploadPic   
     * @Description: 图片上传的业务  
     * @author:陈军
     * @date 2019年5月22日 上午9:22:24 
     * @param file //上传的图片文件
     * @param urlPath //图片保存的路径
     * @param callContext
     * @param contentType //图片的contentType
     * @return
     * @throws Exception      
     * FileUploadVo      
     * @throws
     */
    FileUploadVo uploadPic(MultipartFile file, String urlPath, CallContext callContext,String contentType) throws Exception;

    /**
     * 
     * @Title: downPic   
     * @Description: 图片下载，带缩放功能
     * @author:陈军
     * @date 2019年4月23日 上午8:40:25 
     * @param fileName
     * @param size 缩放后的尺寸
     * @param out
     * @throws Exception      
     * void      
     * @throws
     */
    void downPic(String fileName, Integer size, OutputStream out,Double q) throws Exception;

    /**
     * 
     * @Title: downPic   
     * @Description: 图片下载
     * @author:陈军
     * @date 2019年4月23日 上午8:40:50 
     * @param fileName
     * @param out
     * @throws Exception      
     * void      
     * @throws
     */
    void downPic(String fileName, OutputStream out) throws Exception;
}
