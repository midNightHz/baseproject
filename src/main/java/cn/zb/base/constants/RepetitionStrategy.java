package cn.zb.base.constants;

/**
 * 
 * @ClassName:  RepetitionStrategy   
 * @Description:导入时重复策略   
 * @author: 陈军
 * @date:   2019年1月21日 上午9:56:38   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public enum RepetitionStrategy {
    /**
     * 重复策略 --忽略,
     */
    neglect("忽略", 1),
    /**
     * 重复策略--抛出异常并回滚
     */
    exception("抛出异常", 2),
    /**
     * 重复策略 直接覆盖
     */

    cover("覆盖", 3);

    private final String desc;

    private final int status;

    private RepetitionStrategy(String desc, int status) {
        this.desc = desc;
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public int getStatus() {
        return status;
    }

}
