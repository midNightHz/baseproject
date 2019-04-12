package cn.zb.base.entity;

/**
 * 
 * @ClassName:  IExtraFieldEntity   
 * @Description:允许字段扩展的实体类  ,字段的扩展格式 {"field1":"value1","field2":"value2"}
 * @author: 陈军
 * @date:   2019年2月14日 下午1:51:02   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IExtraFieldEntity {

    /**
     * 
     * @Title: getExtraFields   
     * @Description: 获取额外字段  
     * @author:陈军
     * @date 2019年2月14日 下午3:06:08 
     * @return      
     * String      
     * @throws
     */
    String getExtraFields();

    /**
     * 
     * @Title: setExtraFields   
     * @Description: 设置额外字段  
     * @author:陈军
     * @date 2019年2月14日 下午3:06:32 
     * @param extrafields      
     * void      
     * @throws
     */
    void setExtraFields(String extrafields);

}
