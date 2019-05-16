package cn.zb.utils;

/**
 * 
 * @ClassName:  MobilePhoneUtils   
 * @Description:电话号码处理工具  
 * @author: 陈军
 * @date:   2019年5月8日 下午2:06:55   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class PhoneCheckUtils {

    // 手机号码正则
    private static final String MOBILE_PHONE_REG = "^[0]{0,1}1[34578]\\d{9}$";

    /**
     * 
     * @Title: mobileValidity   
     * @Description: TODO(这里用一句话描述这个方法的作用)  
     * @author:陈军
     * @date 2019年5月9日 上午9:45:07 
     * @param telephone      
     * void      
     * @throws
     */
    public static boolean mobileValidity(String telephone) {

        Assert.isEmpty(telephone, "手机号码不能为空");

        return RegUtils.isMatch(MOBILE_PHONE_REG, telephone);

    }
    
    

}
