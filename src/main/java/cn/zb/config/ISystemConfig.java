package cn.zb.config;

public interface ISystemConfig {
	
	boolean getBoolean(String paramId, boolean defaultValue);

	int getInt(String paramId, int defaultValue);

	String getString(String paramId);

}
