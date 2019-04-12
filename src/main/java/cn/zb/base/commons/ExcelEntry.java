package cn.zb.base.commons;

/**
 * 
 * @ClassName:  ExcelEntry   
 * @Description:   用来维护字段名和字段描述的映射关系
 * @author: 陈军
 * @date:   2019年1月16日 下午1:58:39   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class ExcelEntry {
    /**
     * 字段名
     */
    protected String fieldName;
    /**
     * 字段描述
     */
    protected String fieldDesc;

    public ExcelEntry() {

    }

    public ExcelEntry(String fieldName, String fieldDesc) {
        super();
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

}
