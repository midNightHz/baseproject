package cn.zb.exception;

/**
 * 
 * @ClassName:  OperNeedAuditException   
 * @Description:操作需要进行审核   
 * @author: 陈军
 * @date:   2019年3月12日 下午3:51:49   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class OperNeedAuditException extends Exception {

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */
    private static final long serialVersionUID = -7712605221106419212L;

    public OperNeedAuditException() {
        super();
    }

    public OperNeedAuditException(String message) {
        super(message);
    }

    public OperNeedAuditException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperNeedAuditException(Throwable cause) {
        super(cause);
    }
}
