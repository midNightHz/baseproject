package cn.zb.config;

import org.apache.commons.lang.StringUtils;


/**
 * IsystemConfig的抽象实现类
 * @author chen
 * @see cn.zb.config.ISystemConfig
 *
 */
public abstract class AbstarctSystemConfig implements ISystemConfig {

	@Override
	public boolean booleanConfig(String param, boolean def) {
		Boolean config = booleanConfig(param);
		return config == null ? def : config;
	}

	@Override
	public Boolean booleanConfig(String param) {
		String value = strConfig(param, "");
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new Boolean(value);
	}

	@Override
	public int intConfig(String param, int def) {
		Integer config = intConfig(param);
		return config == null ? def : config;
	}

	@Override
	public Integer intConfig(String param) {
		String value = strConfig(param, "");
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new Integer(value);
	}

	@Override
	public Double doubleConfig(String param) {
		String value = strConfig(param, "");
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new Double(value);
	}

	@Override
	public double doubleConfig(String param, double def) {
		Double config = doubleConfig(param);
		return config == null ? def : config;
	}

	@Override
	public short shortConfig(String param, short def) {
	Short	config = shortConfig(param);
		return config == null ? def : config;
	}

	@Override
	public Short shortConfig(String param) {
		String value = strConfig(param, "");
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new Short(value);
	}

}
