package cn.zb.base.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName:  ExcelImportResultModel   
 * @Description:excel导入模型   
 * @author: 陈军
 * @date:   2019年1月21日 上午10:08:19   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class ExcelImportResultModel<T> {

    /**
     * 总数
     */
    private int total;
    /**
     * 成功数
     */
    private int sucess;
    /**
     * 导入失败
     */
    private int fail;
    /**
     * 导入失败记录
     */
    private List<ExcelImportFailData<T>> failDatas;

    public int getTotal() {
        return total;
    }

    public int getSucess() {
        return sucess;
    }

    public int getFail() {
        return fail;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void addSucess() {
        this.sucess++;
    }

    public void addFail() {
        fail++;
    }

    public List<ExcelImportFailData<T>> getFailDatas() {
        return failDatas;
    }

    public void setFailDatas(List<ExcelImportFailData<T>> failDatas) {
        this.failDatas = failDatas;
    }

    public void addFailData(ExcelImportFailData<T> failData) {
        if (failDatas == null) {
            this.failDatas = new ArrayList<>();
        }
        failDatas.add(failData);
    }

}
