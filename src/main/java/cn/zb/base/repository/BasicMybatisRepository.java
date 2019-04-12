package cn.zb.base.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BasicMybatisRepository {

    /**
     * 
     * @Title: getMaxId   
     * @Description: 查询表中的最大ID  
     * @author:陈军
     * @date 2019年1月22日 上午11:04:42 
     * @param tableName 表名
     * @param idFile Id名
     * @return      
     * Long      
     * @throws
     */
    Long getMaxId(@Param("tableName") String tableName, @Param("idFile") String idFile);

}
