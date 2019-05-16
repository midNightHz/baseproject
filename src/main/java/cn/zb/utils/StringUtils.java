package cn.zb.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.*;

public class StringUtils {

    public static final String DEFAULT_SEPARATOR_CHARS = ";";

    public static List<Integer> splitToInt(String str) throws Exception {
        return splitToInt(str, DEFAULT_SEPARATOR_CHARS);
    }

    public static List<Integer> splitToInt(String str, String reg) throws Exception {
        List<Integer> list = new ArrayList<>();
        String[] strs = split(str, ";");
        if (strs == null) {
            return list;
        }
        for (String s : strs) {
            list.add(new Integer(s));
        }
        return list;
    }

    public static Set<String> toSet(String... list) {
        if (list == null||list.length==0) {
            return null;
        }
        Set<String> set = new HashSet<>();

        Arrays.asList(list).forEach(s -> set.add(s));

        return set;
    }

}
