package cn.zb.base.result;

import java.io.Serializable;
import java.lang.reflect.Method;

import java.util.Iterator;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.Encryptable;

public class JsonResult<T> implements Serializable {

	private static final long serialVersionUID = -3458066472643731732L;

	/**
	 * 结果状态码
	 */
	private Integer code;

	/**
	 * 相关消息
	 */
	private String errmsg;

	/**
	 * 返回到移动端的数据对象
	 */
	private T msg;

	public JsonResult() {
		super();

	}

	/**
	 * @return 返回成功的结果对象
	 */
	public static <E> JsonResult<E> getSuccessJsonResult(E data) {
		JsonResult<E> result = new JsonResult<E>();
		// 加密数据
		encrypt(data);
		result.setErrcode(ResultStatus.SUCCESS);

		result.setMsg(data);

		return result;
	}

	private static <E> void encrypt(E data) {

		if (data instanceof Iterable<?>) {
			Iterable<?> iterable = (Iterable<?>) data;

			Iterator<?> iterator = iterable.iterator();
			while (iterator.hasNext()) {
				Object o = iterator.next();
				encrypt(o);
			}
			return;
		}
		if (data instanceof BaseEntity<?>) {
			if (data instanceof Encryptable) {
				Encryptable model = (Encryptable) data;
				encryptModel(model);
			}
			// 遍历对象的属性
			Class<?> c = data.getClass();
			Method[] methods = c.getDeclaredMethods();
			// Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < methods.length; i++) {
				try {
					// Field field = fields[i];
					// String fieldName = field.getName();
					// String methodname =
					// "get" + fieldName.replace(fieldName.charAt(0),
					// fieldName.toUpperCase().charAt(0));
					Method method = methods[i];
					String methodName = method.getName();
					if (!methodName.startsWith("get")) {
						continue;
					}
					Object o = method.invoke(data);
					encrypt(o);
				} catch (Exception e) {

				}

			}
		}

	}

	private static void encryptModel(Encryptable model) {
		model.encryptId();
		// model.setId(null);
	}

	/**
	 * @return 返回权限不足的结果对象
	 */
	public static <E> JsonResult<E> getPermissionDeniedJsonResult(E data) {
		JsonResult<E> result = new JsonResult<E>();

		result.setErrcode(ResultStatus.PermissionDenied);

		result.setMsg(data);

		return result;
	}

	public static <E> JsonResult<E> getJsonResult(E data, String message, Integer code) {
		JsonResult<E> result = new JsonResult<E>();

		result.setCode(code);

		result.setMsg(data);
		result.setErrmsg(message);

		return result;
	}

	/**
	 * @return 返回失败的结果对象
	 */
	public static <E> JsonResult<E> getFailJsonResult(E data) {
		JsonResult<E> result = new JsonResult<E>();

		result.setErrcode(ResultStatus.FAIL);

		result.setMsg(data);

		return result;
	}

	/**
	 * @return 返回依赖的结果对象
	 */
	public static <E> JsonResult<E> getDependJsonResult(E data) {
		JsonResult<E> result = new JsonResult<E>();

		result.setErrcode(ResultStatus.DEPENDCE);

		result.setMsg(data);

		return result;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public void setErrcode(ResultStatus resultStatus) {
		if (resultStatus == null) {
			throw new NullPointerException("setErrcode - parameter resultStatus is null.");
		}

		this.setCode(resultStatus.getCode());
		this.setErrmsg(resultStatus.getDefaultLabel());
	}

	public T getMsg() {
		return msg;
	}

	public void setMsg(T msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	// TODO 额外字段的特殊处理
}
