package cn.zb.base.entity;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.repository.BasicMybatisRepository;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.ClassUtils;
import cn.zb.utils.ThreadFactory;

/**
 * 
 * @ClassName:  EntityUtil   
 * @Description:实体类的工具   
 * @author: 陈军
 * @date:   2019年2月14日 下午2:02:40   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Component
public class EntityUtil {

    private static Logger logger = LoggerFactory.getLogger(EntityUtil.class);

    /**
     * 维护数据库表-实体类映射关系
     */
    @SuppressWarnings("rawtypes")
    private static final Map<String, Class<? extends BaseEntity>> TABLE_CLASS_MAPPER = new ConcurrentHashMap<>();

    /**
     * 维护实体类-主键映射关系
     */
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends BaseEntity>, String> ENTITY_ID_MAP = new ConcurrentHashMap<>();

    private static BasicMybatisRepository respository;

    @Autowired
    private static BasicMybatisRepository basicMybatisRepository() {
        if (respository == null) {
            respository = BeanFactory.getBean(BasicMybatisRepository.class);
        }
        return respository;
    }

    /**
     * 这里维护者所有实体类ID的最大值
     */
    public static final Map<String, AtomicLong> MAX_IDS = new ConcurrentHashMap<>();

    static {
        // 扫描素有实体类，并维护ID 实体类 表名的映射关系
        Runnable r = new Runnable() {
            @SuppressWarnings({ "rawtypes" })
            @Override
            public void run() {
                Reflections r = new Reflections();
                // 获取所有实现BaseEntity的实体类
                Set<Class<? extends BaseEntity>> cs = r.getSubTypesOf(BaseEntity.class);

                Iterator<Class<? extends BaseEntity>> it = cs.iterator();

                while (it.hasNext()) {

                    Class<? extends BaseEntity> c = it.next();
                    // 如果是实体类的情况
                    if (c.getAnnotation(Entity.class) != null) {

                        String idName = getIdName(c);
                        // 维护id名
                        if (idName != null)
                            ENTITY_ID_MAP.put(c, idName);
                        String tableName = getTableName(c);
                        // 维护类名
                        if (tableName != null) {
                            TABLE_CLASS_MAPPER.put(tableName, c);
                        }
                        // 维护最大的id值
                        MAX_IDS.put(tableName, new AtomicLong(0L));
                    }
                }
                // debug日志
                // if (logger.isDebugEnabled()) {
                logger.debug(JSONObject.toJSONString(ENTITY_ID_MAP));
                logger.debug(JSONObject.toJSONString(TABLE_CLASS_MAPPER));
                // }
                logger.info("bean scan finished");
            }

        };
        ThreadFactory.excute(r);
    }

    /**
     * 
     * @Title: getTableName   
     * @Description: 获取实体类的表名--基于JPA实现  
     * @author:陈军
     * @date 2019年2月14日 下午2:03:38 
     * @param c
     * @return      
     * String      
     * @throws
     */
    public static String getTableName(Class<?> c) {
        try {

            Entity entityAnnotation = c.getAnnotation(Entity.class);
            if (entityAnnotation == null) {
                return null;
            }
            Table table = c.getAnnotation(Table.class);
            // JPA默认的数据库命名规则
            if (table == null || StringUtils.isEmpty(table.name())) {
                String className = c.getSimpleName();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < className.length() - 1; i++) {
                    char a = className.charAt(i);
                    char b = className.charAt(i + 1);
                    sb.append(a);
                    if (a >= 'a' && a <= 'z' && b > 'A' && b < 'Z') {
                        sb.append("_");
                    }

                }
                sb.append(className.charAt(className.length() - 1));

                return sb.toString().toUpperCase();
            }

            return table.name().toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static Class<? extends BaseEntity> getTableEntity(String tableName) {
        return TABLE_CLASS_MAPPER.get(tableName);
    }

    @SuppressWarnings("rawtypes")
    public static String GetEntityIdFieldName(Class<? extends BaseEntity> cl) {
        return ENTITY_ID_MAP.get(cl);
    }

    /**
     * 
     * @Title: getIdName   
     * @Description: 获取实体类的主键/联合主键 
     * @author:陈军
     * @date 2019年2月14日 下午2:04:28 
     * @param c
     * @return      
     * String      
     * @throws
     */
    private static String getIdName(Class<?> c) {

        List<Field> fs = ClassUtils.getFields(c);
        for (int i = 0; i < fs.size(); i++) {
            Field f = fs.get(i);
            Id id = f.getAnnotation(Id.class);
            if (id != null) {
                return f.getName();
            }
            // 联合主键的情况下
            EmbeddedId embeddedId = f.getAnnotation(EmbeddedId.class);
            if (embeddedId != null) {
                return f.getName();
            }

        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Deprecated
    /**
     * 
     * @Title: getMaxId   
     * @Description: 该方法有问题，如果要获取实体类的最大Id值，使用cn.zb.base.entity.EntityUtil.setMaxId(BaseEntity<?>)方法
     * @author:陈军
     * @date 2019年3月20日 上午9:48:04 
     * @param cl
     * @return      
     * long      
     * @throws
     */
    public static long getMaxId(Class cl) {
        return getMaxId(getTableName(cl), GetEntityIdFieldName(cl));
    }

    /**
     * 
     * @Title: getMaxId   
     * @Description:获取实体类的最大id值  
     * @author:陈军
     * @date 2019年2月14日 下午2:04:51 
     * @param tableName
     * @param idName
     * @return      
     * long      
     * @throws
     */
    private static long getMaxId(String tableName, String idName) {

        // 获取服务器中的最大id值
        Long maxId = basicMybatisRepository().getMaxId(tableName, idName);

        if (maxId == null) {
            maxId = 0L;
        }
        // 获取服务器中维护的最大id值
        AtomicLong maxAtomic = EntityUtil.MAX_IDS.get(tableName);

        if (maxAtomic == null) {

            maxAtomic = new AtomicLong(maxId);

            EntityUtil.MAX_IDS.put(tableName, maxAtomic);

        }
        // 比较服务器中维护的id与数据库中查询的id
        if (maxId > maxAtomic.get()) {
            maxAtomic.set(maxId);
        }
        // ID +1后返回
        return maxAtomic.addAndGet(1);
    }

    public static void setMaxId(BaseEntity<?> object) throws Exception {
        if (object.getId() == null) {

            @SuppressWarnings("rawtypes")
            Class<? extends BaseEntity> cl = object.getClass();

            Field f = ClassUtils.getField(cl, EntityUtil.GetEntityIdFieldName(cl));

            if (f.getAnnotation(GeneratedValue.class) == null) {
                Class<?> idClass = f.getType();
                if (Number.class.isAssignableFrom(idClass)) {
                    String dbIdName = null;
                    Column column = f.getAnnotation(Column.class);
                    // 获取数据库中Id的字段名
                    if (column != null && StringUtils.isNotBlank(column.name())) {
                        dbIdName = column.name();
                    } else {
                        // 没有设置字段名的时候需要按规则生成字段名
                        StringBuilder sb = new StringBuilder();
                        String idFieldName = f.getName();
                        for (int i = 0; i < idFieldName.length() - 1; i++) {
                            char a = idFieldName.charAt(i);
                            char b = idFieldName.charAt(i + 1);
                            sb.append(a);
                            if (a >= 'a' && a <= 'z' && b > 'A' && b < 'Z') {
                                sb.append("_");
                            }
                        }

                        sb.append(idFieldName.charAt(idFieldName.length() - 1));
                        dbIdName = sb.toString();
                    }
                    // 获取当前最大的id
                    Long maxId = EntityUtil.getMaxId(getTableName(cl), dbIdName);
                    // 字段类型是int类型时
                    if (idClass.isAssignableFrom(Integer.class))
                        object.setField(f, maxId.intValue());
                    // 字段类型时long类型时
                    else if (idClass.isAssignableFrom(Long.class))
                        object.setField(f, maxId);

                    else if (idClass.isAssignableFrom(Short.class))
                        object.setField(f, maxId.shortValue());

                    else
                        throw new Exception("ID自增错误,id的字段类型错误,ID的类型只允许使用int long 和short");
                }
            }
        }
    }
    
    /**
     * 
     * @Title: getIdField   
     * @Description: 获取id的字段 
     * @author:陈军
     * @date 2019年3月29日 上午11:19:04 
     * @param cl
     * @return      
     * Field      
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static Field getIdField(Class<?> cl) {

        if (!BaseEntity.class.isAssignableFrom(cl)) {
            throw new RuntimeException("error class type");
        }

        @SuppressWarnings("rawtypes")
        String IdFieldName = GetEntityIdFieldName((Class<? extends BaseEntity>) cl);

        return ClassUtils.getField(cl, IdFieldName);

    }
    
    
}
