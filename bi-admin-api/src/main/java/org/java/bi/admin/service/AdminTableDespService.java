package org.java.bi.admin.service;

import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.dto.TableDespAllinone;
import org.java.bi.admin.util.CamelConvert;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.dao.SysTableFieldDespMapper;
import org.java.bi.db.dao.ViewTableDefMapper;
import org.java.bi.db.dao.ViewTableFieldDefMapper;
import org.java.bi.db.domain.*;
import org.java.bi.db.service.BDicCodeService;
import org.java.bi.db.service.CommonDBService;
import org.java.bi.db.service.SysTableDespService;
import org.java.bi.db.service.SysTableFieldDespService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.java.bi.admin.util.AdminResponseCode.DICMAIN_NAME_EXIST;

@Service
public class AdminTableDespService {
    private final Log logger = LogFactory.getLog(AdminTableDespService.class);

    @Autowired
    private SysTableDespService sysTableDespService;
    @Autowired
    private SysTableFieldDespService sysTableFieldDespService;
    @Resource
    private SysTableFieldDespMapper sysTableFieldDespMapper;

    @Autowired
    private CommonDBService commonDBService;

    @Autowired
    private BDicCodeService litemallDiccodeService;

    @Autowired
    private ViewTableDefMapper viewTableDefMapper;

    @Autowired
    private ViewTableFieldDefMapper viewTableFieldDefMapper;


    public Object list( String oltTableDesp,
                       String olfFieldName,String olfFieldDesp,
                       Integer page, Integer limit, String sort) {
        List<SysTableDesp> sysTableDespList = sysTableDespService.querySelective(
                oltTableDesp,olfFieldName,olfFieldDesp, page, limit, sort);
        if(sysTableDespList==null){
            return ResponseUtil.ok();
        }
        return ResponseUtil.okList(sysTableDespList);
    }

    public Object queryAll() {
        List<SysTableDesp> sysTableDespList = sysTableDespService.queryAll();
        return ResponseUtil.okList(sysTableDespList);
    }

    private Object validate(TableDespAllinone tableDespAllinone) {
        SysTableDesp sysTableDesp = tableDespAllinone.getTableDesp();
        String name = sysTableDesp.getOltTableDesp();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.fail(401, "表描述信息不能为空");
        }
        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();
        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
            String codeName = sysTalbeField.getOlfFieldDesp();
            if (StringUtils.isEmpty(codeName)) {
                return ResponseUtil.fail(401, "表字段描述信息不能为空");
            }
            if (!StringUtils.isEmpty(sysTalbeField.getOltTableName())&&
                    StringUtils.isEmpty(sysTalbeField.getOltTableFieldLabel())) {
                String strError="字段:【"+sysTalbeField.getOlfFieldDesp()+"】关联表【"+sysTalbeField.getOltTableName()+
                        "】设置【名字段】属性";
                return ResponseUtil.fail(401, strError);
            }
        }

        return null;
    }

    /**
     * 编辑表字段描述
     * <p>
     * TODO
     * 编辑字段描述表，需要判断是否明细主键ID已经存在，如果存在，则更新信息，如果不存在，则插入数据
     */
    @Transactional
    public Object update(TableDespAllinone tableDespAllinone) {
        Object error = validate(tableDespAllinone);
        if (error != null) {
            return error;
        }
        SysTableDesp sysTableDesp=tableDespAllinone.getTableDesp();
        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();

        Integer mainId = sysTableDesp.getId();
        if(mainId==null){
            sysTableDesp.setAddBy(GetCurrentUser.getCurrentUserName());
            sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
            sysTableDespService.add(sysTableDesp);

        }else
        {
            sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
            sysTableDespService.updateById(sysTableDesp);
            //
        }

        List<ViewTableFieldDef> tableFieldDefs=getAllTableFields(sysTableDesp.getOltDbTalbeViewName());

        // 字段子表
        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
            sysTalbeField.setOltId(sysTableDesp.getId());
            sysTalbeField.setMainTableDesp(sysTableDesp.getOltTableDesp());
            //转换驼峰命名字段
            sysTalbeField.setOlfFieldTransName(CamelConvert.changeColumnToFieldName(sysTalbeField.getOlfFieldName()));

//            Integer codeId=sysTalbeField.getId();
            SysTableFieldDesp findQuery= sysTableFieldDespService.getByFieldName(mainId,sysTalbeField.getOlfFieldName());
            if(findQuery==null){
                sysTalbeField.setOltId(sysTableDesp.getId());
                sysTalbeField.setMainTableDesp(sysTableDesp.getOltTableDesp());
                sysTalbeField.setAddBy(GetCurrentUser.getCurrentUserName());
                sysTalbeField.setUpdateBy(GetCurrentUser.getCurrentUserName());
                sysTableFieldDespService.add(sysTalbeField);
            } else
            {
                Optional boolFindItem= tableFieldDefs.stream().filter(item->item.getColumnName().equals(findQuery.getOlfFieldName())).findFirst();
                if(!boolFindItem.isPresent()&&!findQuery.getOlfFieldName().equals("id")){
                    //如果没有在表定义中出现，则直接删除
                    sysTableFieldDespService.deleteById(findQuery.getId());
                    //删除显示方案中的该字段
                    String strUpdateSql="delete from sys_user_table_field_display  where olf_id="+findQuery.getId();
                    commonDBService.procedureExec(strUpdateSql);
                    //删除显示方案排序字段
                    strUpdateSql="delete from sys_user_table_field_display_order  where olf_id="+findQuery.getId();
                    commonDBService.procedureExec(strUpdateSql);
                    //删除显示方案条件字段
                    strUpdateSql="delete from sys_user_table_field_display_condition  where olf_id="+findQuery.getId();
                    commonDBService.procedureExec(strUpdateSql);
                    //删除查询方案条件字段
                    strUpdateSql="delete from sys_user_table_field_query  where olf_id="+findQuery.getId();
                    commonDBService.procedureExec(strUpdateSql);
                    continue;
                }

                findQuery.setOlfDicId(sysTalbeField.getOlfDicId());
                findQuery.setMainTableDesp(sysTalbeField.getMainTableDesp());
                findQuery.setOlfDicName(sysTalbeField.getOlfDicName());
                findQuery.setOlfFieldDesp(sysTalbeField.getOlfFieldDesp());
                findQuery.setOlfFieldTransName(sysTalbeField.getOlfFieldTransName());
                findQuery.setOlfFieldType(sysTalbeField.getOlfFieldType());
                findQuery.setOlfRemark(sysTalbeField.getOlfRemark());
                findQuery.setOrdernumber(sysTalbeField.getOrdernumber());
                findQuery.setUpdateBy(sysTalbeField.getUpdateBy());
                findQuery.setUpdateTime(LocalDateTime.now());
                findQuery.setOltTableName(sysTalbeField.getOltTableName());
                findQuery.setOltTableDesp(sysTalbeField.getOltTableDesp());
                findQuery.setOltTableFieldValue(sysTalbeField.getOltTableFieldValue());
                findQuery.setOltTableFieldLabel(sysTalbeField.getOltTableFieldLabel());
                findQuery.setOlfIfMultiValue(sysTalbeField.getOlfIfMultiValue());
                findQuery.setUpdateBy(GetCurrentUser.getCurrentUserName());
                findQuery.setDeleted(sysTalbeField.getDeleted());
                sysTableFieldDespService.updateAllFieldById(findQuery);
                //更新显示方案，查询方案中的字段属性
                //更新显示方案字段
                String strUpdateSql="update sys_user_table_field_display set olf_field_desp='"+
                        findQuery.getOlfFieldDesp()+"',olf_field_name='"+findQuery.getOlfFieldName()+"',"+
                        "olf_field_trans_name='"+findQuery.getOlfFieldTransName()+"',olf_dic_id="+findQuery.getOlfDicId()+","+
                        "olt_table_name='"+findQuery.getOltTableName()+"',olt_table_field_label='"+findQuery.getOltTableFieldLabel()+"',"+
                        "olt_table_desp='"+findQuery.getOltTableDesp()+"',deleted="+findQuery.getDeleted()+","+
                        "olt_table_field_value='"+findQuery.getOltTableFieldValue()+"',olf_if_multi_value="+findQuery.getOlfIfMultiValue()+
                        " where olf_id="+findQuery.getId();
                commonDBService.procedureExec(strUpdateSql);
                //更新显示方案排序
                strUpdateSql="update sys_user_table_field_display_order set olf_field_desp='"+
                        findQuery.getOlfFieldDesp()+"',olf_field_name='"+findQuery.getOlfFieldName()+"',deleted="+findQuery.getDeleted()+","+
                        "olf_field_trans_name='"+findQuery.getOlfFieldTransName()+"'"+
                        " where olf_id="+findQuery.getId();
                commonDBService.procedureExec(strUpdateSql);
                //更新显示方案条件
                strUpdateSql="update sys_user_table_field_display_condition set olf_field_desp='"+
                        findQuery.getOlfFieldDesp()+"',olf_field_name='"+findQuery.getOlfFieldName()+"',"+
                        "olf_field_trans_name='"+findQuery.getOlfFieldTransName()+"',olf_dic_id="+findQuery.getOlfDicId()+","+
                        "olt_table_name='"+findQuery.getOltTableName()+"',olt_table_field_label='"+findQuery.getOltTableFieldLabel()+"',"+
                        "olt_table_desp='"+findQuery.getOltTableDesp()+"',deleted="+findQuery.getDeleted()+","+
                        "olt_table_field_value='"+findQuery.getOltTableFieldValue()+"',olf_if_multi_value="+findQuery.getOlfIfMultiValue()+
                        " where olf_id="+findQuery.getId();
                commonDBService.procedureExec(strUpdateSql);
                //更新查询方案条件
                strUpdateSql="update sys_user_table_field_query set olf_field_desp='"+
                        findQuery.getOlfFieldDesp()+"',olf_field_name='"+findQuery.getOlfFieldName()+"',"+
                        "olf_field_trans_name='"+findQuery.getOlfFieldTransName()+"',olf_dic_id="+findQuery.getOlfDicId()+","+
                        "olt_table_name='"+findQuery.getOltTableName()+"',olt_table_field_label='"+findQuery.getOltTableFieldLabel()+"',"+
                        "olt_table_desp='"+findQuery.getOltTableDesp()+"',deleted="+findQuery.getDeleted()+","+
                        "olt_table_field_value='"+findQuery.getOltTableFieldValue()+"',olf_if_multi_value="+findQuery.getOlfIfMultiValue()+
                        " where olf_id="+findQuery.getId();
                commonDBService.procedureExec(strUpdateSql);


            }



        }

        return ResponseUtil.ok();
    }

    /**
     * 删除字典表
     * @param sysTableDesp
     * @return
     */
    @Transactional
    public Object delete(SysTableDesp sysTableDesp) {
        Integer id = sysTableDesp.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        sysTableDespService.deleteById(id);
        sysTableFieldDespService.deleteByMainId(id);

        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(TableDespAllinone tableDespAllinone) {
        Object error = validate(tableDespAllinone);
        if (error != null) {
            return error;
        }

        SysTableDesp sysTableDesp = tableDespAllinone.getTableDesp();
        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();

        String name = sysTableDesp.getOltTableDesp();
        if (sysTableDespService.checkExistByName(name)) {
            return ResponseUtil.fail(DICMAIN_NAME_EXIST, "表描述名称已经存在");
        }
        //保存描述主表
        sysTableDesp.setAddBy(GetCurrentUser.getCurrentUserName());
        sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
        sysTableDespService.add(sysTableDesp);
        // 保存描述子表
        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
            sysTalbeField.setOltId(sysTableDesp.getId());
            sysTalbeField.setMainTableDesp(sysTableDesp.getOltTableDesp());
            //转换驼峰命名字段
            sysTalbeField.setOlfFieldTransName(CamelConvert.changeColumnToFieldName(sysTalbeField.getOlfFieldName()));
            sysTalbeField.setAddBy(GetCurrentUser.getCurrentUserName());
            sysTalbeField.setUpdateBy(GetCurrentUser.getCurrentUserName());
            sysTableFieldDespService.add(sysTalbeField);
        }
        return ResponseUtil.ok();
    }

    public Object getTableDespAndFields(Integer id) {
        SysTableDesp sysTableDesp = sysTableDespService.findById(id);
        List<SysTableFieldDesp> sysTalbeFields = sysTableFieldDespService.queryByMainId(id);

        Map<String, Object> data = new HashMap<>();
        data.put("tableDesp", sysTableDesp);
        data.put("fieldsDesp", sysTalbeFields);

        return ResponseUtil.ok(data);
    }

    public Object getTableDespById(Integer id) {
        SysTableDesp sysTableDesp = sysTableDespService.findById(id);
        return ResponseUtil.ok(sysTableDesp);
    }

    public List<ViewTableDef> getAllTable() {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        criteria.andTableTypeEqualTo("BASE TABLE");
        //所用表中应该只包含那些含有主键ID的表才可以做为其它表中的字典表,否则无法获取
        String strSql=" select distinct table_name from view_table_field_def where column_name='id' ";
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List<Map<String, String>> result=commonDBService.procedureDaoList(param);
        List<String> includeNameList=result.stream().map(x -> x.get("table_name")).collect(Collectors.toList());
        if(includeNameList!=null&&includeNameList.size()>0){
            criteria.andTableNameIn(includeNameList);
        }
        return viewTableDefMapper.selectByExample(example);
    }

    public List<ViewTableDef> getTableAndViewNotInclude() {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        //从字典表中取到所有不可编辑表名称 ，以减少系统可能发生问题的概率
        List<BDicCode> dicCodes= litemallDiccodeService.findByDicmainIdOrDicmainName(null,"系统_不可编辑表",null);
        if(dicCodes!=null&&dicCodes.size()>0){
            List<String> tableNameList=dicCodes.stream().map(BDicCode::getName).collect(Collectors.toList());
            criteria.andTableNameNotIn(tableNameList);
        }
        return viewTableDefMapper.selectByExample(example);
    }
    public ViewTableDef getAllTableAndView(String tableName) {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        criteria.andTableNameEqualTo(tableName);
        return viewTableDefMapper.selectOneByExample(example);
    }
    public ViewTableDef getTableByComment(String tableComment) {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        criteria.andTableCommentEqualTo(tableComment);
        return viewTableDefMapper.selectOneByExample(example);
    }

    public Boolean checkIfTableExists(String tableName) {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        criteria.andTableNameEqualTo(tableName);
        return viewTableDefMapper.countByExample(example) != 0;
    }

    public Boolean checkIfTableFieldExists(String tableName,String fieldName) {
        ViewTableFieldDefExample example=new ViewTableFieldDefExample();
        ViewTableFieldDefExample.Criteria criteria=example.createCriteria();
        criteria.andTableNameEqualTo(tableName);
        criteria.andColumnNameEqualTo(fieldName);
        return viewTableFieldDefMapper.countByExample(example) != 0;
    }

    public List<ViewTableDef> getAllTable(String tableName,String tableComment,
        String columnName,String columnComment,
        Integer page, Integer limit, String sort) {
        ViewTableDefExample example=new ViewTableDefExample();
        ViewTableDefExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(tableName)){
            criteria.andTableNameLike("%"+tableName+"%");
        }
        if(!StringUtils.isEmpty(tableComment)){
            criteria.andTableCommentLike("%"+tableComment+"%");
        }
        if(!StringUtils.isEmpty(columnName)||!StringUtils.isEmpty(columnComment)){
            ViewTableFieldDefExample fieldExample=new ViewTableFieldDefExample();
            ViewTableFieldDefExample.Criteria fieldCriteria=fieldExample.createCriteria();
            if(!StringUtils.isEmpty(columnName)){
                fieldCriteria.andColumnNameEqualTo(columnName);
            }
            if(!StringUtils.isEmpty(columnComment)){
                fieldCriteria.andColumnCommentLike("%"+columnComment+"%");
            }
            criteria.andDeletedEqualTo(0);
            List<ViewTableFieldDef> sysTableFieldDespList= viewTableFieldDefMapper.selectByExample(fieldExample);
            List<String> mainTableNameList=sysTableFieldDespList.stream().map(ViewTableFieldDef::getTableName).distinct().collect(Collectors.toList());
            if(mainTableNameList!=null&&mainTableNameList.size()>0){
                criteria.andTableNameIn(mainTableNameList);
            } else
            {
                return null;
            }
        }

        //从字典表中取到所有不可编辑表名称 ，以减少系统可能发生问题的概率
        List<BDicCode> dicCodes= litemallDiccodeService.findByDicmainIdOrDicmainName(null,"系统_不可编辑表",null);
        if(dicCodes!=null&&dicCodes.size()>0){
            List<String> tableNameList=dicCodes.stream().map(BDicCode::getName).collect(Collectors.toList());
            criteria.andTableNameNotIn(tableNameList);
        }
        criteria.andTableTypeEqualTo("BASE TABLE");
        example.setOrderByClause("table_comment desc");
//        if(!StringUtils.isEmpty(sort)){
//            example.setOrderByClause(sort);
//        }
        PageHelper.startPage(page,limit);
        List<ViewTableDef> tableList= viewTableDefMapper.selectByExample(example);
        return tableList;
    }


    public List<ViewTableFieldDef> getAllTableField(String tableName,String tableComment,
                                          String columnName,String columnComment,
        List<String> haveExistsFields, Integer page, Integer limit, String sort) {

        ViewTableFieldDefExample example=new ViewTableFieldDefExample();
        ViewTableFieldDefExample.Criteria criteria=example.createCriteria();

        if(!StringUtils.isEmpty(tableName)){
            criteria.andTableNameEqualTo(tableName);
        }
        if(!StringUtils.isEmpty(tableComment)){
            criteria.andTableCommentLike("%"+tableComment+"%");
        }
        if(!StringUtils.isEmpty(columnName)){
            criteria.andColumnNameEqualTo(columnName);
        }
        if(!StringUtils.isEmpty(columnComment)){
            criteria.andColumnCommentLike("%"+columnComment+"%");
        }
        if(haveExistsFields!=null&&haveExistsFields.size()>0){
           criteria.andColumnNameNotIn(haveExistsFields);
        }
        criteria.andDeletedEqualTo(0);

        //去掉审计字段
        criteria.andColumnNameNotEqualTo("id");
        //2020-05-28修改，日期也可以显示在要填加的列中
//        criteria.andColumnNameNotEqualTo("add_time");
//        criteria.andColumnNameNotEqualTo("add_by");
//        criteria.andColumnNameNotEqualTo("update_time");
//        criteria.andColumnNameNotEqualTo("update_by");
        criteria.andColumnNameNotEqualTo("deleted");
        //不包括流程引擎字段
        criteria.andTableNameNotLike("%act_%");
        criteria.andTableTypeEqualTo("BASE TABLE");
        //只包含用户自定义表
        //从字典表中取到所有不可编辑表名称 ，以减少系统可能发生问题的概率
        List<BDicCode> dicCodes= litemallDiccodeService.findByDicmainIdOrDicmainName(null,"系统_不可编辑表",null);
        if(dicCodes!=null&&dicCodes.size()>0){
            List<String> tableNameList=dicCodes.stream().map(BDicCode::getName).collect(Collectors.toList());
            criteria.andTableNameNotIn(tableNameList);
        }

        example.setOrderByClause("table_name,column_name");

        PageHelper.startPage(page,limit);
        List<ViewTableFieldDef> viewTableFieldDefList=  viewTableFieldDefMapper.selectByExample(example);

        return viewTableFieldDefList;
    }

    public List<ViewTableFieldDef> getAllTableFields(String strTableOrViewName) {
        ViewTableFieldDefExample example=new ViewTableFieldDefExample();
        ViewTableFieldDefExample.Criteria criteria=example.createCriteria();

        List<String> notInNameList=new ArrayList<>();
//        notInNameList.add("add_time");
//        notInNameList.add("add_by");
//        notInNameList.add("update_time");
//        notInNameList.add("update_by");
        notInNameList.add("deleted");
//        notInNameList.add("dept_id");
//        notInNameList.add("dept_name");
        //主键ID要暴露到前台
        //notInNameList.add("id");

        criteria.andColumnNameNotIn(notInNameList);

        if(!StringUtils.isEmpty(strTableOrViewName)){
            criteria.andTableNameEqualTo(strTableOrViewName);
        }
        List<ViewTableFieldDef> tableFieldList= viewTableFieldDefMapper.selectByExample(example);
        return tableFieldList;
    }

    public List<ViewTableFieldDef> getAllTableFieldsNotIncludeShenJiField(String strTableOrViewName) {
        ViewTableFieldDefExample example=new ViewTableFieldDefExample();
        ViewTableFieldDefExample.Criteria criteria=example.createCriteria();

        List<String> notInNameList=new ArrayList<>();
        notInNameList.add("id");
        notInNameList.add("add_time");
        notInNameList.add("add_by");
        notInNameList.add("add_by_name");
        notInNameList.add("update_time");
        notInNameList.add("update_by");
        notInNameList.add("update_by_name");
        notInNameList.add("deleted");
        notInNameList.add("dept_id");
        notInNameList.add("dept_name");
        //主键ID要暴露到前台
//        notInNameList.add("id");

        criteria.andColumnNameNotIn(notInNameList);

        if(!StringUtils.isEmpty(strTableOrViewName)){
            criteria.andTableNameEqualTo(strTableOrViewName);
        }
        List<ViewTableFieldDef> tableFieldList= viewTableFieldDefMapper.selectByExample(example);
        return tableFieldList;
    }

}
