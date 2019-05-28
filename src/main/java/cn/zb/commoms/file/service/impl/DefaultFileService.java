package cn.zb.commoms.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.zb.base.controller.CallContext;
import cn.zb.commoms.file.model.FileUploadVo;
import cn.zb.config.ApplicationProperties;
import cn.zb.utils.FileUtils;
import cn.zb.utils.ImageUtils;

@Service
public class DefaultFileService extends AbstractFileService {

    @Autowired
    private ApplicationProperties applicaionProperties;

    @Override
    public FileUploadVo uploadFile(MultipartFile file, String urlPath, CallContext callContext, String contentType)
            throws Exception {

        InputStream is = file.getInputStream();

        String fileName = file.getOriginalFilename();
        FileUtils.saveUploadFile(is, applicaionProperties.getImgRootPath() + urlPath + fileName);
        String src = applicaionProperties.getLocalUrl() + urlPath + fileName;
        return new FileUploadVo(src, fileName);
    }

    @Override
    public FileUploadVo uploadPic(MultipartFile file, String urlPath, CallContext callContext, String contentType)
            throws Exception {

        InputStream is = file.getInputStream();
        String originalName = file.getOriginalFilename();
        // 是否重命名
        boolean rename = applicaionProperties.isImageRename();

        if (rename) {
            originalName = FileUtils.rename(originalName);
        }

        boolean imageCopression = applicaionProperties.isImageCompression();

        String fileName = applicaionProperties.getImgRootPath() + urlPath + originalName;

        if (imageCopression) {

            BufferedImage image = ImageIO.read(is);

            int imageWidth = applicaionProperties.getImageDefaultSize();

            double quality = applicaionProperties.getImageCompressionQuality();

            ImageUtils.savePic(image, imageWidth, fileName, quality);

        } else {

            FileUtils.saveUploadFile(is, fileName);
        }

        String src = applicaionProperties.getLocalUrl() + urlPath + originalName;

        return new FileUploadVo(src, originalName);

    }

}
