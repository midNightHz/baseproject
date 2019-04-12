package cn.zb.base.controller;

public class MsgException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2167686356151931443L;
	
	private Integer code;

    public Integer getCode() {
        return code;
    }

    public MsgException(String message) {
        super(message);
        this.code = 1001;
    }

    public MsgException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
