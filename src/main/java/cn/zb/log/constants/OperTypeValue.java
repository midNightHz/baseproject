package cn.zb.log.constants;

/**
 * 
 * @ClassName:  OperTypeValue   
 * @Description:  
 * @author: 陈军
 * @date:   2019年1月4日 上午9:48:42   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public enum OperTypeValue {

    DELETE(3, "删除"),

    INSERT(1, "新增"),

    UPDATE(2, "修改"),

    SOFTDELETE(4, "废弃"),

    SELECT(5, "查询");

    private final Integer operType;

    private final String memo;

    private OperTypeValue(Integer operType, String memo) {
        this.memo = memo;
        this.operType = operType;
    }

    public static OperTypeValue getType(int type) {

        switch (type) {
        case 1:
            return INSERT;
        case 2:
            return OperTypeValue.UPDATE;
        case 3:
            return DELETE;
        case 4:
            return OperTypeValue.SOFTDELETE;
        default:
            return null;
        }

    }

    public Integer getOperType() {
        return operType;
    }

    public String getMemo() {
        return memo;
    }

}
