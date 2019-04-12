package cn.zb.base.result;

import cn.zb.utils.NumberUtils;

public enum ResultStatus implements IStatusEnum<Integer> {
    /**
     * 登录session问题， 获取不到用户身份(-2)
     */
    PermissionDenied(-2, "权限不足", "ResultStatus.PermissionDenied"),
    /**
     * 失败(-1)
     */
    FAIL(-1, "失败", "ResultStatus.FAIL"),
    /**
     * 成功(1)
     */
    SUCCESS(1, "成功", "ResultStatus.SUCCESS"),
    /**
     * 依赖其它操作(2)
     */
    DEPENDCE(2, "依赖其它操作", "ResultStatus.DEPENDCE"),

    SHOULDLOGIN(-3, "需要操作", "ResultStatus.SHOULDLOGIN"),
    /**
     * 操作成功，进入审核
     */
    NEEDAUDIT(1001, "需要审核", "ResultStatus.NEEDAUDIT");

    private final Integer code;
    private final String defaultLabel;
    private final String resourceKey;

    private ResultStatus(Integer code, String defaultLabel, String resourceKey) {
        this.code = code;
        this.defaultLabel = defaultLabel;
        this.resourceKey = resourceKey;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDefaultLabel() {
        return defaultLabel;
    }

    @Override
    public String getResourceKey() {
        return resourceKey;
    }

    public static ResultStatus valueOf(Integer code) {
        if (NumberUtils.isNull(code)) {
            throw new NullPointerException("RefundStatus.valueOf - parameter code is null.");
        }

        ResultStatus result = null;

        for (ResultStatus status : ResultStatus.values()) {
            if (status == null) {
                continue;
            }

            if (status.getCode().equals(code)) {
                result = status;
                break;
            }
        }

        if (result == null) {
            throw new IllegalStateException("cannot find enum with code[" + code + "] in ResultStatus");
        }

        return result;
    }

}
