package cn.zb.base.controller;

import java.io.InputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.ExcelImportResultModel;
import cn.zb.base.service.IExcelImportService;
import cn.zb.utils.FileUtils;

public interface IExcelImportController<S extends IExcelImportService<T, ID>, T extends BaseEntity<ID>, ID extends Serializable>
        extends CommonController {

    S getExcelImportService();

    @PostMapping("importdata")
    default void importDatas(HttpServletRequest request, HttpServletResponse response,
            @RequestParam() MultipartFile file) {
        try {

            InputStream is = file.getInputStream();

            CallContext callContext = getServiceController().getCallContext(request);
            // 文件保存在本地备份
            String fileName = file.getOriginalFilename();
            fileName = "../importExcels/" + fileName.substring(0, fileName.lastIndexOf("."))
                    + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
            FileUtils.saveUploadFile(is, fileName);

            S s = getExcelImportService();
            // 导入
            ExcelImportResultModel<T> result = s.importDatas(is, callContext);

            getServiceController().writeSuccessJsonToClient(response, result);

        } catch (Exception e) {

            getLogger().error("导入文件失败:{}", e.getMessage());

            getServiceController().writeFailDataJsonToClient(response, "导入失败");
        }

    }
}
