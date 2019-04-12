package cn.zb.base.model;

/**
 * 
 * @ClassName:  ExcelImportFailData   
 * @Description:excel导入失败数据  
 * @author: 陈军
 * @date:   2019年1月22日 上午11:08:08   
 *   
 * @param <T>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class ExcelImportFailData<T> {

    /**
     * 导入数据     
     */
    T data;
    /**
     * 数据的索引行
     */
    int index;
    /**
     * 失败原因
     */
    String reson;

    public ExcelImportFailData() {
    }

    public ExcelImportFailData(T data, int index, String reson) {
        super();
        this.data = data;
        this.index = index;
        this.reson = reson;
    }

    public T getData() {
        return data;
    }

    public int getIndex() {
        return index;
    }

    public String getReson() {
        return reson;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

}
