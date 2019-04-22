package cn.zb.config;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: ISystemConfig
 * @Description:系统配置表
 * @author: 陈军
 * @date: 2019年4月19日 下午3:02:36
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface ISystemConfig {

	/**
	 * 
	 * @Title: strConfig @Description: 字符串的配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:40:25 @param param 参数名 @param def 默认值 @return String @throws
	 */
	String strConfig(String param, String def);

	/**
	 * 
	 * @Title: booleanConfig @Description: 布尔型的配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:40:47 @param param 参数名 @param def 默认值 @return boolean @throws
	 */
	boolean booleanConfig(String param, boolean def);

	Boolean booleanConfig(String param);

	/**
	 * 
	 * @Title: intConfig @Description: int 类型的配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:41:19 @param param 参数名 @param def 默认值 @return int @throws
	 */
	int intConfig(String param, int def);

	Integer intConfig(String param);

	/**
	 * 
	 * @Title: doubleCofig @Description: double型的配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:41:48 @param param 参数名 @return Double @throws
	 */
	Double doubleConfig(String param);

	/**
	 * 
	 * @Title: doubleConfig @Description: double型的参数配置 @author:陈军 @date 2019年4月22日
	 * 下午12:42:08 @param param 参数名 @param def 默认值 @return double @throws
	 */

	double doubleConfig(String param, double def);

	/**
	 * 
	 * @Title: shortConfig @Description:short型的配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:42:41 @param param 参数名 @param def 默认值 @return short @throws
	 */
	short shortConfig(String param, short def);

	Short shortConfig(String param);

	/**
	 * 
	 * @Title: listConfig @Description: 配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:43:47 @param param 参数名 @param cl @return List<T> @throws
	 */
	<T> List<T> listConfig(String param, Class<T> cl);

	/**
	 * 
	 * @Title: mapConfig @Description: 配置信息 @author:陈军 @date 2019年4月22日
	 * 下午12:44:05 @param param @param cl @return Map<String,T> @throws
	 */
	<T> Map<String, T> mapConfig(String param, Class<T> cl);

}
