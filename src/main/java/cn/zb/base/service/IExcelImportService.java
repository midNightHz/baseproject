package cn.zb.base.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.constants.RepetitionStrategy;
import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.ExcelImportFailData;
import cn.zb.base.model.ExcelImportResultModel;
import cn.zb.utils.ExcelUtil;

/**
 * 
 * @ClassName: IExcelImportService
 * @Description:excel 导入接口 只适用于当表导入的情况，如果多表导入，请
 * @author: 陈军
 * @date: 2019年1月21日 上午9:46:29
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IExcelImportService<T extends BaseEntity<ID>, ID extends Serializable> extends BaseService<T, ID> {

	/**
	 * 
	 * @Title: toEntity @Description: excel列表转换为对象 @author:陈军 @date 2019年1月21日
	 * 上午9:53:08 @param datas @return @throws Exception List<T> @throws
	 */
	default List<T> toEntity(List<Map<String, String>> datas) throws Exception {
		if (datas == null || datas.size() == 0) {
			return new ArrayList<>();
		}
		Class<T> tclass = entityClass();
		List<T> list = new ArrayList<>();
		for (Map<String, String> map : datas) {
			try {
				list.add(JSON.parseObject(JSONObject.toJSON(map).toString(), tclass));
			} catch (Exception e) {
				list.add(null);
			}
		}
		return list;
	};

	/**
	 * 
	 * @Title: repetitionStrategy @Description:重复策略 @author:陈军 @date 2019年1月21日
	 * 上午10:01:37 @return RepetitionStrategy @throws
	 */
	default RepetitionStrategy repetitionStrategy() {
		return RepetitionStrategy.cover;
	}

	/**
	 * 
	 * @Title: importDatas @Description: 导入的主要业务逻辑 @author:陈军 @date 2019年1月21日
	 * 上午10:19:08 @param file @param callContext @return @throws Exception
	 * ExcelImportResultModel<T> @throws
	 */
	default ExcelImportResultModel<T> importDatas(MultipartFile file, CallContext callContext) throws Exception {

		return importDatas(file.getInputStream(), callContext);

	};

	/**
	 * @throws Exception @throws IOException @throws InvalidFormatException
	 * 
	 * @Title: importDatas @Description: 导入的业务逻辑，以流的方式导入 @author:陈军 @date 2019年1月21日
	 * 上午10:56:28 @param is @param callContext @return @throws Exception
	 * ExcelImportResultModel<T> @throws
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	default ExcelImportResultModel<T> importDatas(InputStream is, CallContext callContext)
			throws InvalidFormatException, IOException, Exception {
		// 读取数据
		List<Map<String, String>> datas = ExcelUtil.readDatas(is, startIndex(), getFieldNames(), sheet());

		if (datas == null || datas.size() == 0) {
			throw new Exception("read datas error can't read any datas;");
		}
		// 对象的实例化
		List<T> objs = toEntity(datas);
		// 获取重复策略
		RepetitionStrategy startegy = repetitionStrategy();

		ExcelImportResultModel<T> result = new ExcelImportResultModel<>();
		result.setTotal(objs.size());
		for (int i = 0; i < objs.size(); i++) {

			T t = objs.get(i);
			if (t == null) {
				result.addFail();
				result.addFailData(new ExcelImportFailData<T>(t, i + 1, "当前对象为空"));
			}
			// 校验是否重复
			T origin = objReplation(t);
			// 重复的情况
			if (origin != null) {
				// 不同的重复策略实现调用不同的保存策略
				switch (startegy) {
				// 忽略的策略
				case neglect:
					result.addFail();
					result.addFailData(new ExcelImportFailData<T>(t, i + 1, "当前对象重复,已忽略"));
					continue;
				// 抛出异常的策略
				case exception:
					result.addFail();
					result.addFailData(new ExcelImportFailData<T>(t, i + 1, "当前对象重复"));
					throw new Exception(i + 1 + "行记录重复,+重复的记录:" + JSONObject.toJSONString(origin));
					// 覆盖的策略
				case cover:
					try {
						if (!checkModifyAuth(t, origin, callContext)) {
							result.addFail();
							result.addFailData(new ExcelImportFailData<T>(t, i + 1, "没有修改的权限"));
							continue;
						}
						t.setId(origin.getId());
						flushSave(origin, callContext);
						result.addSucess();

					} catch (Exception e) {

						result.addFail();
						result.addFailData(new ExcelImportFailData<T>(t, i + 1, e.getMessage()));
					}
					break;

				default:
					break;
				}
			}
			// 不重复的情况下直接保存
			try {
				if (!checkInsertAuth(t, callContext)) {
					result.addFail();
					result.addFailData(new ExcelImportFailData<T>(t, i + 1, "没有新增的权限"));
					continue;
				}
				getJpaRepository().save(t);
				result.addSucess();

			} catch (Throwable e) {

				result.addFail();
				result.addFailData(new ExcelImportFailData<T>(t, i + 1, e.getMessage()));

			}

		}
		return result;

	}

	/**
	 * 
	 * @Title: getFieldNames @Description: 获取读取文件列和实体类中字段的对应关系 @author:陈军 @date
	 * 2019年1月21日 上午10:46:31 @return @throws Exception List<String> @throws
	 */
	List<String> getFieldNames() throws Exception;

	/**
	 * 
	 * @Title: startIndex @Description: 开始读取记录的行 @author:陈军 @date 2019年1月21日
	 * 上午10:48:23 @return int @throws
	 */
	default int startIndex() {
		return 1;
	}

	/**
	 * 
	 * @Title: sheet @Description: 读取指定的 sheet @author:陈军 @date 2019年1月21日
	 * 上午11:32:02 @return int @throws
	 */
	default int sheet() {
		return 0;
	}

	/**
	 * 
	 * @Title: objReplation @Description:
	 * 对象是否重复，不重复则返回空，如果重复则从数据库查询对象 @author:陈军 @date 2019年1月21日 下午4:07:29 @param
	 * t @return T @throws
	 */
	default T objReplation(T t) {
		return null;
	}

}
