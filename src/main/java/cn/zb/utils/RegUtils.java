package cn.zb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName:  RegUtils   
 * @Description:正则工具   
 * @author: 陈军
 * @date:   2019年2月18日 上午9:50:30   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class RegUtils {

    /**
     * 
     * @Title: isMatch   
     * @Description: 用正则来匹配字符串，验证字符串是否合规  
     * @author:陈军
     * @date 2019年2月18日 上午9:50:49 
     * @param reg
     * @param content
     * @return      
     * boolean      
     * @throws
     */
    public static boolean isMatch(String reg, String content) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String reg="^[0-9]*[1-9][0-9]*/[0-9]*[1-9][0-9]*$";
        String content=null;
        System.out.println(content);
        System.out.println(isMatch(reg, content));
        
    }

}
