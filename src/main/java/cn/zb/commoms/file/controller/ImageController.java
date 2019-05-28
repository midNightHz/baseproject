package cn.zb.commoms.file.controller;

import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zb.base.controller.RestController;
import cn.zb.commoms.file.constants.FileConstants;
import cn.zb.commoms.file.factory.FileServiceFactory;
import cn.zb.commoms.file.service.IFileService;
import cn.zb.config.ApplicationProperties;
import cn.zb.utils.FileUtils;

@Controller
@RequestMapping("/image")
public class ImageController extends RestController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ApplicationProperties applicationPropeties;

    private IFileService fileService;

    private IFileService getFileService() {

        if (fileService == null) {
            synchronized (this) {
                fileService = FileServiceFactory.getFileService();
            }
        }
        return fileService;
    }

    @GetMapping("/**")
    public void getImage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) Integer w, @RequestParam(required = false) Double q) {
        String url = null;
        try {
            if (q == null || q > 1.0 || q < 0) {
                q = 1.0;
            }
            url = request.getRequestURI();
            url = URLDecoder.decode(url, "utf-8");
            int index = url.indexOf(request.getContextPath());
            url = url.substring(index + 1);
            url = url.substring(url.indexOf("/") + 1);
            url = url.substring(url.indexOf("/"));
            url = applicationPropeties.getImgRootPath() + url;
            OutputStream out = response.getOutputStream();
            String suffix = FileUtils.getFileSuffix(url);
            response.setContentType(FileConstants.getPicContentType(suffix));
            getFileService().downPic(url, w, out, q);
        } catch (Exception e) {
            logger.error("{},文件名:{}", e.getMessage(), url);
            response.setStatus(404);
        }

    }

}
