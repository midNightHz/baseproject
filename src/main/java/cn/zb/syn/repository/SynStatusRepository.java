package cn.zb.syn.repository;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SynStatusRepository {

	void updateSynStatus(@Param("tableName") String tableName, @Param("synStatus") Short synStatus);
	
	/**
	 * 查询最后一次更新数据时间
	 * @param tableName
	 * @param synTimeField
	 * @return
	 */
	Date getLastSynDate(@Param("tableName") String tableName,@Param("synTimeField") String synTimeField);

}
