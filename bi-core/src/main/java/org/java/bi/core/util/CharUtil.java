package org.java.bi.core.util;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CharUtil {

    public static String getRandomString(Integer num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String objectConverToString(Object object) {
         if(object==null){
             return "";
         } else
         {
             return object.toString();
         }
    }
    public static Double objectConverToDouble(Object object) {
        if(object==null){
            return null;
        } else
        {
            Double bonusValue= Double.parseDouble(object.toString());

            return bonusValue;
        }
    }
    public static Integer objectConverToIntegerNull(Object object) {

        if(object==null){
            return null;
        } if(StringUtils.isEmpty(object)){
            return null;
        } else
        {
            Integer result= Integer.parseInt(object.toString());

            return result;
        }
    }
    public static Integer objectConverToInteger(Object object) {

        if(object==null){
            return 0;
        } else
        {
            Integer result= Integer.parseInt(object.toString());

            return result;
        }
    }
    public static Boolean objectConverToBoolean(Object object) {
        if(object==null){
            return null;
        } else
        {
            Boolean result=(Boolean) object;

            return result;
        }
    }
    public static Double objectConverToDoubleDefault0(Object object) {
        if(object==null){
            return Double.parseDouble("0") ;
        } else
        {
            Double bonusValue= Double.parseDouble(object.toString());

            return bonusValue;
        }
    }
    public static BigDecimal objectConverToBigdecimalDefault0(Object object) {
        if(object==null){
            return BigDecimal.valueOf(Double.parseDouble("0"));
        } else
        {
            Double bonusValue= Double.parseDouble(object.toString());

            return BigDecimal.valueOf(bonusValue);
        }
    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param jsonArray
     * @return
     */
    public static  String JSONArrayToString(JSONArray jsonArray) {
        String jsonString = jsonArray.toString();
        jsonString= jsonString.replace("[","")
                .replace("]","")
                .replace("\"","'");
        String[] array=jsonString.split(",");//去重
        jsonString=Arrays.stream(array).distinct().collect(Collectors.toList()).toString();
        return jsonString.replace("[","").replace("]","");
    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param jsonArray
     * @return
     */
    public static  String JSONArrayToString_ValueInteger(JSONArray jsonArray) {
        String jsonString = jsonArray.toString();
        jsonString= jsonString.replace("[","")
                .replace("]","")
                .replace("\"","")
                .replace(" ","")
                .replace("'","");
        String[] array=jsonString.split(",");//去重
        jsonString=Arrays.stream(array).distinct().collect(Collectors.toList()).toString();
        return jsonString.replace("[","").replace("]","").replace(" ","");
    }

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
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param strList
     * @return
     */
    public static String ListStringToString(List<String> strList) {
        String strResult="";
        for(String item :strList){
            strResult+="'"+item.toString()+"',";
        }
        if(!strResult.equals("")){
            return strResult.substring(0,strResult.length()-1);
        }
        return strResult;

    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param strList
     * @return
     */
    public static String ListObjectToString(List<Object> strList) {
        String strResult="";
        for(Object item :strList){
            strResult+="'"+item.toString()+"',";
        }
        if(!strResult.equals("")){
            return strResult.substring(0,strResult.length()-1);
        }
        return strResult;

    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param strList
     * @return
     */
    public static  List<String> ListObjectToListString(List<Object> strList) {
        if(strList==null||strList.size()==0){
            return null;
        }
        List<String> strResult=new ArrayList<>();

        for(Object item :strList){
            strResult.add(item.toString());
        }

        return strResult;

    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param strList
     * @return
     */
    public static String[] ListStringToArray(List<String> strList) {
        if(strList==null||strList.size()==0){
            return null;
        }
        String[] strs1=strList.toArray(new String[strList.size()]);

        return strs1;
    }

    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param intList
     * @return
     */
    public static Integer[] ListIntegerToArray(List<Integer> intList) {
        if(intList==null||intList.size()==0){
            return null;
        }
        Integer[] ints1=intList.toArray(new Integer[intList.size()]);

        return ints1;
    }



    /**
     * 只处理简单类型,将JSON数组转成以逗号分隔的String
     * @param jsonArray
     * @return
     */
    public static List<Integer> JSONArrayToListInteger(JSONArray jsonArray) {
        String jsonString = jsonArray.toString();
        jsonString= jsonString.replace("[","").replace("]","");
        String[] array=jsonString.split(",");
        LinkedHashSet<Object> temp = new LinkedHashSet<>();
        for(int i=0;i<array.length;i++){
          temp.add(array[i]);
        }
        Object[] noRepeat= temp.toArray();
        List<Integer> result=new ArrayList<>();
        for(int ii=0;ii<noRepeat.length;ii++)
        {
            result.add(Integer.parseInt(noRepeat[ii].toString()));
        }

        return result;
    }

    /**
     * 只处理简单类型,将字符串数组转成整型列表
     * @param strArray
     * @return
     */
    public static List<Integer> StringArrayToListInteger(String[] strArray) {

        LinkedHashSet<Object> temp = new LinkedHashSet<>();
        for(int i=0;i<strArray.length;i++){
            temp.add(strArray[i]);
        }
        Object[] noRepeat= temp.toArray();
        List<Integer> result=new ArrayList<>();
        for(int ii=0;ii<noRepeat.length;ii++)
        {
            result.add(Integer.parseInt(noRepeat[ii].toString()));
        }

        return result;
    }
}
