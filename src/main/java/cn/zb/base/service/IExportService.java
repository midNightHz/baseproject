package cn.zb.base.service;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.zb.base.commons.ExcelEntry;
import cn.zb.base.controller.CallContext;
import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;
import cn.zb.page.PageRequest;
import cn.zb.utils.ClassUtils;

/**
 * 
 * @ClassName:  IExportService   
 * @Description:用于导出excel服务层  
 * @author: 陈军
 * @date:   2019年1月16日 下午2:04:55   
 *   
 * @param <T>
 * @param <ID>
 * @param <Q>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IExportService<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery>
        extends IBaseMybatisService<T, ID, Q> {

    /**
     * @throws Exception 
     * 
     * @Title: export   
     * @Description: 导出的业务逻辑  
     * @author:陈军
     * @date 2019年1月16日 下午2:12:57 
     * @param q
     * @param os
     * @param callContext      
     * void      
     * @throws
     */
    default void export(Q q, OutputStream os, CallContext callContext) throws Exception {

        q.init(new PageRequest(), callContext);
        // 查询所有符合条件的对象
        List<T> list = getBaseMybatisRepository().findAll(q);

        ExcelWriter writer = ExcelUtil.getWriter();

        List<ExcelEntry> entrys = getExcelEntrys(q, callContext);
        // 添加标题行
        if (containIndex()) {
            writer.addHeaderAlias("序列", "序列");
        }

        entrys.forEach(e -> {

            writer.addHeaderAlias(e.getFieldName(), e.getFieldDesc());

        });
        // 写入数据
        writer.write(getRows(list, entrys));

        if (autoSizeColumn()) {
            if (containIndex()) {
                writer.autoSizeColumn(0);
                for (int i = 0; i < entrys.size(); i++) {
                    writer.autoSizeColumn(i + 1);
                }
            } else {
                for (int i = 0; i < entrys.size(); i++) {
                    writer.autoSizeColumn(i + 1);
                }
            }
        }
        writer.flush(os);

        writer.close();

    }

    /**
     * 
     * @Title: getExcelEntrys   
     * @Description:导出的字段
     * @author:陈军
     * @date 2019年2月25日 下午3:47:49 
     * @param q
     * @param callContext
     * @return      
     * List<ExcelEntry>      
     * @throws
     */
    List<ExcelEntry> getExcelEntrys(Q q, CallContext callContext);
    
    
    

    /**
     * 
     * @Title: getRows   
     * @Description:数据的格式化，将对象转换为hutool工具中能接受的对象 ,Map最好使用LinkedHashMap,
     *              使用其他实现类的时候会导致字段显示的序列与期望中不一样
     * @author:陈军
     * @date 2019年1月22日 上午10:15:10 
     * @param list
     * @param entrys
     * @return
     * @throws Exception      
     * List<Map<String,Object>>      
     * @throws
     */
    default List<Map<String, Object>> getRows(List<T> list, List<ExcelEntry> entrys) throws Exception {

        List<Field> fields = new ArrayList<>();

        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        Class<?> cl = null;

        // 获取当前示例的类名
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (t != null) {
                cl = t.getClass();
                break;
            }
        }

        if (cl == null) {
            return new ArrayList<>();
        }
        // 获取所有需要写出的字段名
        for (ExcelEntry entry : entrys) {
            Field f = ClassUtils.getField(cl, entry.getFieldName());
            fields.add(f);
        }

        List<Map<String, Object>> resultList = new ArrayList<>();
        // 读取对象集合中的数据
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Map<String, Object> entityMap = new LinkedHashMap<>();
            if (containIndex()) {
                entityMap.put("序列", i + 1);
            }

            for (Field f : fields) {
                entityMap.put(f.getName(), t.getField(f));
            }
            resultList.add(entityMap);
        }

        return resultList;
    }

    /**
     * 
     * @Title: containIndex   
     * @Description: 是否包含序列 
     * @author:陈军
     * @date 2019年1月16日 下午2:13:44 
     * @return      
     * boolean      
     * @throws
     */
    default boolean containIndex() {
        return false;
    }

    /**
     * 
     * @Title: autoSizeColumn   
     * @Description:列宽度自动 
     * @author:陈军
     * @date 2019年2月25日 下午3:48:45 
     * @return      
     * boolean      
     * @throws
     */
    default boolean autoSizeColumn() {
        return true;
    }

    /**
     * 
     * @Title: exportFileName   
     * @Description: 导出的文件名 
     * @author:陈军
     * @date 2019年2月25日 下午3:49:12 
     * @param q
     * @param callContext
     * @return      
     * String      
     * @throws
     */
    default String exportFileName(Q q, CallContext callContext) {
        return "export.xls";
    }

}
