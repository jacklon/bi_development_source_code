package org.java.bi.db.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysTableFieldDespMapper;
import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.domain.SysTableFieldDesp;
import org.java.bi.db.domain.SysTableFieldDespExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysTableFieldDespService {
    @Resource
    private SysTableFieldDespMapper sysTableFieldDespMapper;

    @Autowired
    private SysTableDespService sysTableDespService;

    @Autowired
    private CommonDBService commonDBService;

    @Autowired
    private BDicCodeService litemallDiccodeService;

    @Autowired
    private SysBiMenuService litemallMenuService;


    public List<SysTableFieldDesp> queryIndex(){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        example.or().andDeletedEqualTo(false);
        return sysTableFieldDespMapper.selectByExample(example);
    }

    /**
     * 模糊查找
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @return
     */
    public List<SysTableFieldDesp> querySelective(String name, Integer page,Integer limit,String sort){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        SysTableFieldDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andOlfFieldNameLike("%"+name+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        } else{
            example.setOrderByClause("ordernumber");
        }
        PageHelper.startPage(page,limit);
        return sysTableFieldDespMapper.selectByExample(example);
    }


    /**
     * 精确查找
     * @param oltId
     * @param oltTableDesp
     * @param olfFieldDesp
     * @param sort
     * @return
     */
    public List<SysTableFieldDesp> findByMainIdOrMainName(Integer oltId, String oltTableDesp, String olfFieldDesp, String sort){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        SysTableFieldDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(oltId)){
            criteria.andOltIdEqualTo(oltId);
        }
        if(!StringUtils.isEmpty(oltTableDesp)){
            criteria.andOltTableDespEqualTo(oltTableDesp);
        }
        if(!StringUtils.isEmpty(olfFieldDesp)){
            criteria.andOlfFieldNameEqualTo(olfFieldDesp);
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else
        {
            example.setOrderByClause("ordernumber");
        }

        return sysTableFieldDespMapper.selectByExample(example);
    }

    public int updateById(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        return sysTableFieldDespMapper.updateByPrimaryKeySelective(sysTableFieldDesp);
    }

    public int updateAllFieldById(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        return sysTableFieldDespMapper.updateByPrimaryKey(sysTableFieldDesp);
    }

    public void deleteById(Integer id){
        sysTableFieldDespMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByMainId(Integer mainId){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andOltIdEqualTo(mainId);
        sysTableFieldDespMapper.deleteByExample(example);
//        sysTableFieldDespMapper.logicalDeleteByExample(example);
    }

    public void add(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setAddTime(LocalDateTime.now());
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        sysTableFieldDespMapper.insertSelective(sysTableFieldDesp);
    }


    public SysTableFieldDesp findById(Integer id){
        return sysTableFieldDespMapper.selectByPrimaryKey(id);
    }

    public List<SysTableFieldDesp> queryByMainId(Integer oltId) {
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andOltIdEqualTo(oltId).andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber,add_time");
        return sysTableFieldDespMapper.selectByExample(example);
    }

    public Map<String, Object> getTableFieldDesp(Integer sysTableDespId, String fieldName) {

        Map<String, Object> result=new HashMap<>();

        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andOltIdEqualTo(sysTableDespId).andOlfFieldNameEqualTo(fieldName).andDeletedEqualTo(false);

        SysTableFieldDesp sysTableFieldDesp= sysTableFieldDespMapper.selectOneByExample(example);

        result.put("sysTableFieldDesp",sysTableFieldDesp);

        //绑定字典表取值，或者从字典表，或者从其它表，将其结果进行绑定
        if(!StringUtils.isEmpty(sysTableFieldDesp.getOlfDicId())){
            List<BDicCode> dicCodes=  litemallDiccodeService.queryByMainId(sysTableFieldDesp.getOlfDicId());
            result.put("dicCodes",dicCodes);

        } else if(!StringUtils.isEmpty(sysTableFieldDesp.getOltTableName()))
        {
            //加载关联第三方表字段
            String strSql="select "+sysTableFieldDesp.getOltTableFieldValue()+" as id,"+
                    sysTableFieldDesp.getOltTableFieldLabel()+" as name from "+
                    sysTableFieldDesp.getOltTableName()+" where deleted=0";
            Map<String, Object> param=new HashMap<>();
            param.put("sqlS",strSql);
            List codeList= commonDBService.procedureDaoList(param);
            List<BDicCode> dicCodes=new ArrayList<>();
            if (codeList != null && codeList.size() > 0) {
                for (int ii = 0; ii < codeList.size(); ii++) {
                    Map<String, Object> mapArray = (Map<String, Object>) codeList.get(ii);
                    Integer dataId=Integer.parseInt(mapArray.get("id").toString()) ;
                    String dataName=mapArray.get("name").toString();
                    BDicCode dicCode=new BDicCode();
                    dicCode.setId(dataId);
                    dicCode.setName(dataName);
                    dicCodes.add(dicCode);
                }
            }
            result.put("dicCodes",dicCodes);

        }
        return result;
    }

    public boolean checkExistByFieldName(Integer oltId,String olfFieldName){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andOltIdEqualTo(oltId).andOlfFieldNameEqualTo(olfFieldName);
        return sysTableFieldDespMapper.countByExample(example) != 0;
    }

    public  SysTableFieldDesp getByFieldName(Integer oltId,String olfFieldName){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andOltIdEqualTo(oltId).andOlfFieldNameEqualTo(olfFieldName).andDeletedEqualTo(false);
        return sysTableFieldDespMapper.selectOneByExample(example);
    }


    /**
     * 根据传入的表Id及Json数据值转换成相应的备注JSON
     * @param tableId
     * @param fieldsData
     * @return
     */
    public String getOperationLogRemark(Integer tableId,String strOperation,Integer id,JSONObject fieldsData){
        SysTableDesp sysTableDesp= sysTableDespService.findById(tableId);
        List<SysTableFieldDesp> dbFields= queryByMainId(tableId);
        List<Integer> olfDicIdList=dbFields.stream().map(SysTableFieldDesp::getOlfDicId).collect(Collectors.toList());
        List<BDicCode> dicCodeList= litemallDiccodeService.queryByMainIdList(olfDicIdList);
        String strSelectSql=getSelectFieldsByTableId(sysTableDesp,dbFields,id);

        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSelectSql);
        List resultList= commonDBService.procedureDaoList(param);
//        Integer count=(Integer) param.get("total");
        Map<String, Object> updateFields=fieldsData.getInnerMap();

        String strResult="";

        //使用entry遍历键值对
        for (Map.Entry<String,Object> entry:updateFields.entrySet()
        ) {
            Optional<SysTableFieldDesp> findFieldDesp= dbFields.stream().filter(item->item.getOlfFieldTransName().equals(entry.getKey())).findFirst();

            //先判断是否已经在表记录中存在，如果不存在，则插入，如果存在，则判断值是否有变化
            Optional fieldDesp= dbFields.stream().filter(item->item.getOlfFieldTransName().equals(entry.getKey())).map(SysTableFieldDesp::getOlfFieldDesp).distinct().findFirst();
            if(fieldDesp.isPresent()){
                String value="";
                //根据entry.getValue值，从字典表中转换成名称
                if(findFieldDesp.get().getOlfDicId()!=null&&!StringUtils.isEmpty(entry.getValue())){
                    Optional<BDicCode> dicCode= dicCodeList.stream().filter(item->item.getMainid().equals(findFieldDesp.get().getOlfDicId())&&item.getId().equals(entry.getValue())).findFirst();
                    if(dicCode.isPresent()){
                        value=dicCode.get().getName();
                    }
                } else if(!StringUtils.isEmpty(findFieldDesp.get().getOltTableName())&&!StringUtils.isEmpty(entry.getValue())) {
                    String tableName = findFieldDesp.get().getOltTableName();
                    String labelName = findFieldDesp.get().getOltTableFieldLabel();
                    if(!entry.getValue().toString().equals("[]")) {
                        //如果是多值，则entry.getValue是多一个多值数组
                        String strSql = "select " + labelName + " from " + tableName;
                        if((entry.getValue() instanceof JSONArray)){
                           String strInList= commonDBService.jsonToInteger((JSONArray)entry.getValue());
                            strSql+= " where id in (" + strInList+")";
                        } else
                        {
                            strSql += " where id=" + entry.getValue().toString();
                        }
                        Object fieldValue = commonDBService.procedureDaoListFirstValue(strSql, labelName);
                        if (fieldValue != null) {
                            value = fieldValue.toString();
                        }
                    }
                } else
                {
                    value=entry.getValue().toString();
                }

                if(strOperation.equals("insert")){
                    strResult+=fieldDesp.get()+":"+value+",";
                } else
                {
                    String strDbValue="";
                    //判断值是否发生了变化
                    if(resultList.get(0)!=null){
                        HashMap<String,String> dataResult= ((HashMap) resultList.get(0));
                        Object dbValue=dataResult.get(entry.getKey());
                        if(dbValue!=null){
                            strDbValue=dbValue.toString();
                        }
                    }
                    if(!strDbValue.equals(entry.getValue())&&!entry.getKey().equals("id")){
                        strResult+=fieldDesp.get()+":"+value+",";
                    }
                }
            }
        }
        if(!strResult.equals("")){
            strResult=strResult.substring(0,strResult.length()-1);
        }

        return strResult;
    }
    public String getSelectFieldsByTableId(SysTableDesp sysTableDesp,List<SysTableFieldDesp> dbFields,Integer id) {
        String tableName = sysTableDesp.getOltDbTalbeViewName();
        String selectSentence = "select ";
        for (SysTableFieldDesp sysTableFieldDesp : dbFields) {
            selectSentence += sysTableFieldDesp.getOlfFieldName() + ",";
        }
        if (!selectSentence.equals("select ")) {
            selectSentence = selectSentence.substring(0, selectSentence.length() - 1);
        }
        selectSentence += " from " + tableName + " where 1=1 ";
        if (!StringUtils.isEmpty(id)) {

            selectSentence += " and id=" + id;
        }
        return selectSentence;
    }

    public String getSelectFieldsByTableId(Integer tableId,Integer id) {
        SysTableDesp sysTableDesp = sysTableDespService.findById(tableId);
        String tableName = sysTableDesp.getOltDbTalbeViewName();
        List<SysTableFieldDesp> dbFields = queryByMainId(tableId);
        String selectSentence = "select ";
        for (SysTableFieldDesp sysTableFieldDesp : dbFields) {
            selectSentence += sysTableFieldDesp.getOlfFieldName() + ",";
        }
        if (!selectSentence.equals("select ")) {
            selectSentence = selectSentence.substring(0, selectSentence.length() - 1);
        }
        selectSentence += " from " + tableName + " where 1=1 ";
        if (!StringUtils.isEmpty(id)) {

            selectSentence += " and id=" + id;
        }
        return selectSentence;
    }


    private SysTableFieldDesp getSysTableFieldDesp(Integer oltId,String mainTableDesp,String olfFieldName,String olfFieldTransName,
        String olfFieldDesp,String olfFieldType,Integer olfDicId,String olfDicName,String oltTableName,
        String oltTableDesp,String oltTableFieldLabel,String oltTableFieldValue,String olfRemark,Integer ordernumber){
        SysTableFieldDesp flowStartNameFiled=new SysTableFieldDesp();
        flowStartNameFiled.setOltId(oltId);
        flowStartNameFiled.setMainTableDesp(mainTableDesp);
        flowStartNameFiled.setOlfFieldName(olfFieldName);
        flowStartNameFiled.setOlfFieldTransName(olfFieldTransName);
        flowStartNameFiled.setOlfFieldDesp(olfFieldDesp);
        flowStartNameFiled.setOlfFieldType(olfFieldType);
        flowStartNameFiled.setOlfDicId(olfDicId);
        flowStartNameFiled.setOlfDicName(olfDicName);
        flowStartNameFiled.setOltTableName(oltTableName);
        flowStartNameFiled.setOltTableDesp(oltTableDesp);
        flowStartNameFiled.setOltTableFieldLabel(oltTableFieldLabel);
        flowStartNameFiled.setOltTableFieldValue(oltTableFieldValue);
        flowStartNameFiled.setOlfIfMultiValue(true);
        flowStartNameFiled.setOlfRemark(olfRemark);
        flowStartNameFiled.setOrdernumber(ordernumber);
        return flowStartNameFiled;
    }


}
