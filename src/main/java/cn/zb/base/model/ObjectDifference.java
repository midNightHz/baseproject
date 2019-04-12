package cn.zb.base.model;

/**
 * 
 * @ClassName:  ObjectDifference   
 * @Description:对象的差异   
 * @author: 陈军
 * @date:   2019年1月4日 下午4:42:53   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class ObjectDifference {
    // 字段名
    private String field;
    
    private String fieldName;
    // 被比较对象字段
    private Object source;
    // 比较对象字段
    private Object target;

    public ObjectDifference() {

    }

    public ObjectDifference(String field, Object source, Object target,String fieldName) {
        super();
        this.field = field;
        this.source = source;
        this.target = target;
        this.setFieldName(fieldName);
    }

    public String getField() {
        return field;
    }

    public Object getSource() {
        return source;
    }

    public Object getTarget() {
        return target;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return fieldName + "从" + source + "修改成" + target;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
