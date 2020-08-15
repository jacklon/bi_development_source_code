package org.java.bi.db.util;


import java.util.*;

/**
 * Created by HJYIN on 14-11-26.
 */
public class CommonUtil {
    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param intList
     * @return
     */
    public static String ListInterToString(List<Integer> intList) {
        String strResult="";
        for(Integer item :intList){
            strResult+=item.toString()+",";
        }
        if(!strResult.equals("")){
            return strResult.substring(0,strResult.length()-1);
        }
        return strResult;

    }
    /**
     * 将整数列表对象转换成以逗号分隔的字符串，用于数据库中的in检索
     * @param integerList
     * @return
     */

    public static String listToInteger(List<Integer> integerList) {
        if (integerList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer integer : integerList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(integer);
        }
        return result.toString();
    }

    public static String listToInteger(Integer[] integerList) {
        if (integerList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer integer : integerList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(integer);
        }
        return result.toString();
    }

    /**
     * 将整数列表对象转换成以逗号分隔的字符串，用于数据库中的in检索
     * @param stringList
     * @return
     */

    public static String listStringToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String str : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append("'"+str+"'");
        }
        return result.toString();
    }


}
