package org.java.bi.db.service;

import com.alibaba.fastjson.JSONArray;
import org.java.bi.db.dao.CommonDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommonDBService {
    @Resource
    private CommonDBMapper commonDBMapper;

    @Autowired
    private BDicTreeCodeService diccodeService;


    public List procedureDaoList(Map<String, Object> param ){
        List result= commonDBMapper.procedureDaoList(param);
        return result;
    }

    /**
     * 不用输出Total，则直接返回结果
     * @param strSql
     * @return
     */
    public List procedureDaoList(String strSql){
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List result= commonDBMapper.procedureDaoList(param);
//        Integer count=(Integer) param.get("total");
        return result;
    }

    /**
     * 输出Total，返回Map结构
     * @param strSql
     * @return
     */
    public Map<String, Object> procedureDaoListPage(String strSql){
        Map<String, Object> result=new HashMap<>();
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List dataList= commonDBMapper.procedureDaoList(param);
        Integer count=(Integer) param.get("total");
        result.put("dataList",dataList);
        result.put("total",count);
        return result;
    }
    /**
     * 输出Total，返回Map结构
     * @param strSql
     * @return
     */
    public Map<String, Object> procedureDaoListPage(String strSql,Integer page,Integer limit){
        Map<String, Object> result=new HashMap<>();
        Map<String, Object> param=new HashMap<>();
        if(limit!=null&&limit<999999) {
            int fromIndex = (page-1) * limit;
            strSql+=  " limit " + fromIndex + "," + limit;
        }

        param.put("sqlS",strSql);
        List dataList= commonDBMapper.procedureDaoList(param);
        Integer count=(Integer) param.get("total");
        result.put("dataList",dataList);
        result.put("total",count);
        return result;
    }

    /**
     * 直接返回执行的第一个字段值
     * @param strSql
     * @param fieldName
     * @return
     */
    public Object procedureDaoListFirstValue(String strSql,String fieldName){
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List<Map<String,Object>> result= commonDBMapper.procedureDaoList(param);
        if(result!=null&&result.size()>0){
            if(result.get(0)!=null){
               return result.get(0).get(fieldName);
            }
        }
        return null;
    }


    public Integer procedureInsert(Map<String, Object> param ){
        commonDBMapper.procedureInsert(param);
        return Integer.parseInt(param.get("id").toString());
    }

    public void procedureExec(String strSql ){
          commonDBMapper.procedureExec(strSql);
    }

    /**
     * 返回前台传到后台的查询结果
     * @param dbField
     * @param operator
     * @param value
     * @return
     */
    public String getDBConvertCondition(String dbField,String operator,Object value){
        //todo
        String result="";
        //操作符取值   =(等于)  >(大于) >=(大等于) <(小于)  <=(小等于) <>(不等于)
        // like(相似于) not like(不相似) in(包含于) is null(为空) is not null(不为空)
        //如果传入的值为空或者是"",则返回空
        if((operator.equals("=")||operator.equals(">")||operator.equals(">=")||operator.equals("<")
          ||operator.equals("<=")||operator.equals("<>")||operator.equals("like")||operator.equals("not like")
          ||operator.equals("in")
        )&&(value==null||value.equals("")||value.equals("[]")))
        {
            return "";
        }

        String valueResult=value.toString();

        //判断如时输入的是多值数组，则用in方法调用
        if(valueResult.startsWith("[")&&valueResult.endsWith("]")){
            valueResult=valueResult.replace("[","").replace("]","");
            if(value.equals("")){
                return "";
            }
            String[] inStrings= valueResult.split(",");
            String orInCondition="";
            for(String valueString: inStrings) {
                orInCondition+=" find_in_set('"+valueString+"',replace(replace("+dbField+",'[',''),']','')) or ";

            }
            if(!orInCondition.equals("")){
                orInCondition=orInCondition.substring(0,orInCondition.length()-4);
                result=" and ("+orInCondition+")";
                return result;
            } else
            {
                return "";
            }

        }
        //布尔类型变成字符串，true为是,false为否，或者其它，只按普通的文本进行存储和检索
//        if(valueResult.equalsIgnoreCase("true")){
//            valueResult="1";
//        } else if(valueResult.equalsIgnoreCase("false"))
//        {
//            valueResult="0";
//        }

        //默认返回
        if(operator.equals("is null")||operator.equals("is not null")){
            result = " and ("+ dbField +" "+ operator +" or "+ dbField +"='')" ;
        } else if(operator.equals("like")||operator.equals("not like")){
            result = " and " + dbField +" "+ operator + " '%" + valueResult + "%' ";
        } else if(operator.equals("in")||operator.equals("not in")){
            //由逗号隔开
            String strIn= valueResult;
//            String[] splitStr= strIn.split(",");
            //加上引号
            strIn="'"+strIn.replaceAll(",","','")+"'";

            result = " and " + dbField +" "+ operator + " (" + strIn + ") ";
        } else
        {
            result = " and " + dbField + operator + "'" + valueResult + "' ";
        }
        return result;
    }

    public String listToInteger(List<Integer> integerList) {
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

    public String jsonToInteger(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int ii=0;ii<jsonArray.size();ii++) {
            String integerValue= jsonArray.get(ii).toString();
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(integerValue);
        }
        return result.toString();
    }

    public String arrayToInteger(Integer[] integerList) {
        if (integerList == null||integerList.length==0) {
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

    public  String convertListToString(List<String> strlist) {
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(strlist)) {
            for(int i = 0; i < strlist.size(); ++i) {
                if (i == 0) {
                    sb.append("'").append((String)strlist.get(i)).append("'");
                } else {
                    sb.append(",").append("'").append((String)strlist.get(i)).append("'");
                }
            }
        }

        return sb.toString();
    }

    public String getFindListIntegerInConditon(String dbName,List<Integer> inList){
        //获取类型及子类型ID
        String condition="";

        for(int jj=0;jj<inList.size();jj++){
            condition+=inList.get(jj)+",";
        }

        if(StringUtils.isEmpty(condition)){
            return "";
        }
        condition=condition.substring(0,condition.length()-1);
        return " and "+dbName+" in ("+condition+")";

    }

    /**
     * 以下只适合数据库存储的是单值情况
     * @param inList
     * @return
     */
    public String getFindListIntegerArrayInConditon(String dbName,List<Integer[]> inList){
        //获取类型及子类型ID
        String condition="";
        for(int ii=0;ii<inList.size();ii++){
            Integer[] intCondition=inList.get(ii);
            for(int jj=0;jj<intCondition.length;jj++){
                condition+=intCondition[jj]+",";
            }
        }
        if(StringUtils.isEmpty(condition)){
            return "";
        }
        condition=condition.substring(0,condition.length()-1);
        return " and "+dbName+" in ("+condition+")";
    }

    /**
     * 以下只适合数据库存储的是多值情况
     * @param inList
     * @return
     */
    public String getFindListIntegerArrayJsonConditon(String dbName,List<Integer[]> inList){
        //获取类型及子类型ID
        String condition="";
        for(int ii=0;ii<inList.size();ii++){
            Integer[] intCondition=inList.get(ii);
            for(int jj=0;jj<intCondition.length;jj++){
                condition+=" find_in_set('"+intCondition[jj]+"',replace( replace("+dbName+",'[',''),']','')) or ";
            }
        }
        if(StringUtils.isEmpty(condition)){
            return "";
        }
        condition=condition.substring(0,condition.length()-3);
        return " and ("+condition+")";
    }

    public String getFindListStringConditon(List<String> inList){
        //获取类型及子类型ID
        String condition="";
        for(int ii=0;ii<inList.size();ii++){
            if(ii==inList.size()-1){
                condition+=" find_in_set('"+inList.get(ii)+"',replace( replace(dept_ids,'[',''),']','')) ";
            } else
            {
                condition+=" find_in_set('"+inList.get(ii)+"',replace( replace(dept_ids,'[',''),']','')) or ";
            }
        }
        return " and ("+condition+")";
    }




}
