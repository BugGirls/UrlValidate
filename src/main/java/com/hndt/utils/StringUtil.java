package com.hndt.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串转换 工具类
 *
 * @author Hystar
 * @date 2018/10/11 0011
 */
public class StringUtil {

    /**
     * 将1,2,3,4 格式的字符串转换成[1,2,3,4] 类型的集合
     *
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {
        List<String> strList = splitToListStr(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }

    /**
     * 将1,2,3,4 格式的字符串转换成["1","2","3","4"] 类型的集合
     *
     * @param str
     * @return
     */
    public static List<String> splitToListStr(String str) {
        if (StringUtils.isNotBlank(str)) {
            return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        } else {
            return Lists.newArrayList();
        }
    }
}
