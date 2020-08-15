package org.java.bi.admin.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.bi.admin.annotation.RequiresPermissionsDesc;
import org.java.bi.admin.service.AdminTableDespService;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.dao.ViewTableFieldDefMapper;
import org.java.bi.db.domain.ViewTableDef;
import org.java.bi.db.domain.ViewTableFieldDef;
import org.java.bi.db.service.CommonDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/tableModify")
@Validated
@Api(description = "后台管理/基础数据/代码字典:/admin/tableModify")
public class SysTableModifyController {
    private final Log logger = LogFactory.getLog(SysTableModifyController.class);

    @Autowired
    private AdminTableDespService adminTableDespService;

    @Resource
    private ViewTableFieldDefMapper viewTableFieldDefMapper;

    @Autowired
    private CommonDBService commonDBService;
    /**
     * 查询数据库表列表
     *
     * @param tableName
     * @param tableComment
     * @param page
     * @param limit
     * @param sort
     * @return
     */
//    @RequiresPermissions("admin:tableModify:list")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="查询主表")
    @GetMapping("/list")
    public Object list(String tableName,String tableComment,
                       String columnName,String columnComment,

                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<ViewTableDef> sysTalbeDesps= adminTableDespService.getAllTable(tableName,
                tableComment,columnName,columnComment,page,limit,sort);

        if(sysTalbeDesps==null){
            return ResponseUtil.ok();
        }
        return ResponseUtil.okList(sysTalbeDesps);
    }

    /**
     * 查询数据库字段列表
     *
     * @param tableName
     * @param tableComment
     * @param page
     * @param limit
     * @param sort

     * @return
     */
//    @RequiresPermissions("admin:tableModify:listFields")
//    @RequiresPermissionsDesc(menu={"基础数据" , "表字段定义管理"}, button="查询表字段")
    @GetMapping("/listFields")
    public Object listFields(String tableName,String tableComment,
                       String columnName,String columnComment,
                       @RequestParam(required = false)List<String> haveExistsFields,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
    ) {
        List<ViewTableFieldDef> viewTableFieldDefList= adminTableDespService.getAllTableField(tableName,
                tableComment,columnName,columnComment,haveExistsFields,page,limit,sort);

        return ResponseUtil.okList(viewTableFieldDefList);

    }

    /**
     * 创建或覆盖表
     *
     * @param tableModifyAllinone
     * @return
     */
    @RequiresPermissions("admin:tableModify:build")
    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="创建")
    @PostMapping("/build")
    public Object create(@RequestBody String tableModifyAllinone) {
        Boolean result= saveOrUpdateTable(tableModifyAllinone);
        return ResponseUtil.ok(result);

    }

    /**
     * 创建或覆盖表
     *
     * @param tableModifyAllinone
     * @return
     */
    @RequiresPermissions("admin:tableModify:UpdateTableStructure")
    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="更新")
    @PostMapping("/UpdateTableStructure")
    public Object UpdateTableStructure(@RequestBody String tableModifyAllinone) {
        String strError= getUpdateTableStructure(tableModifyAllinone);
        if(strError==null||strError.equals("")){
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail(502,strError);
        }
    }
    private String getUpdateTableStructure(String tableModifyAllinone)
    {
        String strError="";
        //解析结构
        JSONObject formDataMap = (JSONObject) JSONObject.parse(tableModifyAllinone);
        //解析主表
        String tableName=formDataMap.get("tableName").toString();

        //检查表是否存在，如果不存，早执行创建语句
        Boolean booIfExist=  adminTableDespService.checkIfTableExists(tableName);
        if(!booIfExist){
            String strSql=getBuildSql(tableModifyAllinone);
            try {
                commonDBService.procedureExec(strSql);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
                strError+="执行创建表【"+tableName+"】语句时报错，执行语句【"+strSql+"】，错误信息:"+ex.getMessage();
                return strError;
            }
        } else
        {
            //解析字段
            JSONArray jsonArray=(JSONArray)formDataMap.get("fields");

            //构建Sql字段
            for(Object object:jsonArray ){
                JSONObject jsonObject= JSONObject.parseObject(object.toString());
                Object deletedFlag=jsonObject.get("deleted");
                String strName=jsonObject.get("fieldName").toString().toLowerCase();
                String strComment=jsonObject.get("fieldComment").toString().toLowerCase();
                if(!strName.equalsIgnoreCase("id")&&
                        !strName.equalsIgnoreCase("add_by")&&
                        !strName.equalsIgnoreCase("add_by_name")&&
                        !strName.equalsIgnoreCase("update_by")&&
                        !strName.equalsIgnoreCase("update_by_name")&&
                        !strName.equalsIgnoreCase("add_time")&&
                        !strName.equalsIgnoreCase("update_time")&&
                        !strName.equalsIgnoreCase("dept_id")&&
                        !strName.equalsIgnoreCase("dept_name")&&
                        !strName.equalsIgnoreCase("deleted")
                ){
                    //判断字段是否存在，如果存在，调用修改语句，如果不存在，则调用新增语句,如果出错，将错误信息写入回告
                    Boolean booFieldIfExist=   adminTableDespService.checkIfTableFieldExists(tableName,strName);
                    String strSql="";
                    if(booFieldIfExist&&deletedFlag!=null&&deletedFlag.toString().equals("1")){
                        strSql="alter table "+tableName+" drop column "+strName ;
                        try {
                            commonDBService.procedureExec(strSql);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                            strError+="执行字段【"+strName+"】修改时报错，执行语句【"+strSql+"】，错误信息:"+ex.getMessage();
                            continue;
                        }
                        continue;
                    }
                    if(booFieldIfExist){
                        strSql="alter table "+tableName+" modify "+strName+" "+" "+getDBFieldType(jsonObject)+" "+
                                getDBFieldNotAndDefault(jsonObject)+" COMMENT '"+strComment+"'";
                        try {
                            commonDBService.procedureExec(strSql);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                            strError+="执行字段【"+strName+"】修改时报错，执行语句【"+strSql+"】，错误信息:"+ex.getMessage();
                            continue;
                        }
                    } else
                    {
                        strSql="alter table "+tableName+" add "+strName+" "+" "+getDBFieldType(jsonObject)+" "+
                                getDBFieldNotAndDefault(jsonObject)+" COMMENT '"+strComment+"'";
                        try {
                            commonDBService.procedureExec(strSql);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                            strError+="执行插入字段【"+strName+"】时报错，执行语句【"+strSql+"】，错误信息:"+ex.getMessage();
                            continue;
                        }
                    }

                }
            }
        }
        return strError;
    }

    /**
     * 获取表的创建语句
     *
     * @param tableModifyAllinone
     * @return
     */
//    @RequiresPermissions("admin:tableModify:getDdlSql")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="获取Sql")
    @PostMapping("/getDdlSql")
    public Object getDdlSql(@RequestBody String tableModifyAllinone) {
        String strSql= getBuildSql(tableModifyAllinone);
        return ResponseUtil.ok(strSql);

    }

    /**
     * 修改表名称
     * @param oldTableName
     * @param newTableName
     * @return
     */
    @GetMapping("/modifyTableName")
    public Object modifyTableName(String oldTableName,String newTableName){
        //判读表名称是否已经存在，如果存在，则不允许进行修改
        ViewTableDef viewTableDef= adminTableDespService.getAllTableAndView(newTableName);
        if(viewTableDef!=null){
           return ResponseUtil.fail(502,"该表名称已经存在，请修改成别的名称");
        }

        String strDDLSql="ALTER  TABLE "+oldTableName+" RENAME TO "+newTableName;
        commonDBService.procedureExec(strDDLSql);
        return ResponseUtil.ok();
    }


    /**
     * 修改表描述
     * @param tableName
     * @param tableComment
     * @return
     */
    @GetMapping("/modifyTableComment")
    public Object modifyTableComment(String tableName,String tableComment){
        ViewTableDef viewTableDef= adminTableDespService.getTableByComment(tableComment);
        if(viewTableDef!=null){
            return ResponseUtil.fail(502,"该表描述已经存在，请修改成别的描述");
        }

        String strDDLSql="ALTER  TABLE "+tableName+" comment '"+tableComment+"'";
        commonDBService.procedureExec(strDDLSql);
        return ResponseUtil.ok();
    }


    private String getBuildSql(String formData){
        //解析结构
        JSONObject formDataMap = (JSONObject) JSONObject.parse(formData);
        //解析主表
        String tableName=formDataMap.get("tableName").toString();
        String tableComment=formDataMap.get("tableComment").toString();
        //解析字段
        JSONArray jsonArray=(JSONArray)formDataMap.get("fields");
        String strEnter="\r\n";
        String strFieldSql="";
        //构建Sql字段
        for(Object object:jsonArray ){
            JSONObject jsonObject= JSONObject.parseObject(object.toString());
            String strName=jsonObject.get("fieldName").toString().toLowerCase();
            String strComment=jsonObject.get("fieldComment").toString().toLowerCase();
            if(!strName.equalsIgnoreCase("id")&&
                    !strName.equalsIgnoreCase("add_by")&&
                    !strName.equalsIgnoreCase("add_by_name")&&
                    !strName.equalsIgnoreCase("update_by")&&
                    !strName.equalsIgnoreCase("update_by_name")&&
                    !strName.equalsIgnoreCase("add_time")&&
                    !strName.equalsIgnoreCase("update_time")&&
                    !strName.equalsIgnoreCase("dept_id")&&
                    !strName.equalsIgnoreCase("dept_name")&&
                    !strName.equalsIgnoreCase("deleted")
            ){
                strFieldSql+="`"+strName+"` "+getDBFieldType(jsonObject)+" "+
                        getDBFieldNotAndDefault(jsonObject)+" COMMENT '"+strComment+"',"+strEnter;
            }
        }

        String strSql="";
        strSql+="CREATE TABLE `"+tableName+"` ("+strEnter;
        strSql+=strFieldSql;
        strSql+=getOtherField();
        strSql+=") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC  COMMENT='"+tableComment+"';"+strEnter;

        return strSql;
    }

    private boolean saveOrUpdateTable(String formData){
        //先删除原表
        //解析结构
        JSONObject formDataMap = (JSONObject) JSONObject.parse(formData);
        //解析主表
        String tableName=formDataMap.get("tableName").toString();
        //构建Sql语句
        String strEnter="\r\n";
        String strDeleteSql=" DROP TABLE IF EXISTS `"+tableName+"`; "+strEnter;
        commonDBService.procedureExec(strDeleteSql);

        String strSql=getBuildSql(formData);
        commonDBService.procedureExec(strSql);

        return true;
    }

    /**
     * 返回数据库字段类型
     * @param jsonObject
     * @return
     */
    private String getDBFieldType(JSONObject jsonObject){

        String strType=jsonObject.get("fieldType").toString();
        Object fieldLength= jsonObject.get("fieldLength");
        Integer strFieldLength=255;
        if(!StringUtils.isEmpty(fieldLength)) {
            strFieldLength = Integer.parseInt(fieldLength.toString());
        }
        if(strType.equals("文本型")){
            return "varchar("+strFieldLength+")";
        } else if(strType.equals("布尔型")){
            //布尔型的值存储为文字显示，如果是tinyint,则在解析时会有描述文本不一致的情况
//            return "tinyint(1)";
            return "varchar(20)";
        }else if(strType.equals("整数型")){
            return "int(11)";
        }else if(strType.equals("浮点型")){
            return "decimal(10,2)";
        }else if(strType.equals("日期型")){
            return "datetime";
        }else if(strType.equals("长文本")){
            return "text";
        }
        return "";
    }
    private String getDBFieldNotAndDefault(JSONObject jsonObject) {
        Object fieldIfNull=jsonObject.get("fieldIfNull");
        String strIfNull ="false";
        if(fieldIfNull!=null) {
             strIfNull = fieldIfNull.toString();
        }
        Object fieldDefault=jsonObject.get("fieldDefault");
        String strDefault ="";
        if(fieldDefault!=null) {
            strDefault = fieldDefault.toString();
        }
        String nullIfAllow="";
        if(strIfNull.equalsIgnoreCase("true")){
            nullIfAllow= "";
        } else
        {
            nullIfAllow=" NOT NULL ";
        }
        String strResult=nullIfAllow;
        if(!StringUtils.isEmpty(strDefault)){
            strResult+=" DEFAULT '"+strDefault+"'";
        } else
        {
            if(StringUtils.isEmpty(nullIfAllow)) {
                strResult += "DEFAULT NULL";
            }
        }
        return strResult;
    }
    /**
     * 返回主键及审计字段
     */
    private String getOtherField(){
        String strEnter="\r\n";
//        return "`add_time` datetime DEFAULT NULL COMMENT '创建时间',"+strEnter+
//               "`update_time` datetime DEFAULT NULL COMMENT '更新时间',"+strEnter+
//               "`deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除'," +strEnter+
//               "`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',"+strEnter+
//               " PRIMARY KEY (`id`) USING BTREE "+strEnter;

        return " `add_time` datetime DEFAULT NULL COMMENT '创建时间',"+strEnter+
                "`add_by` varchar(255) DEFAULT NULL COMMENT '创建人Id',"+strEnter+
                "`add_by_name` varchar(255) DEFAULT NULL COMMENT '创建人名称',"+strEnter+
                "`dept_id` int(11) DEFAULT NULL COMMENT '部门Id',"+strEnter+
                "`dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',"+strEnter+
                "`update_time` datetime DEFAULT NULL COMMENT '更新时间',"+strEnter+
                "`update_by` varchar(255) DEFAULT NULL COMMENT '最后修改人',"+strEnter+
                "`update_by_name` varchar(255) DEFAULT NULL COMMENT '最后修改人名称',"+strEnter+
                "`deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',"+strEnter+
                "`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',"+strEnter+
                " PRIMARY KEY (`id`) USING BTREE "+strEnter;
    }

    /**
     * 判断表是否存在
     *
     * @param tableName
     * @return
     */
//    @RequiresPermissions("admin:tableModify:checkTableIfExist")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="表是否存在")
    @GetMapping("/checkTableIfExist")
    public Object checkTableIfExist(String tableName) {

       Boolean booIfExist=  adminTableDespService.checkIfTableExists(tableName);
       return ResponseUtil.ok(booIfExist);
    }

    /**
     * 判断表是否有数据
     *
     * @param tableName
     * @return
     */
//    @RequiresPermissions("admin:tableModify:checkIfTableHaveData")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="表是否有数据")
    @GetMapping("/checkIfTableHaveData")
    public Object checkIfTableHaveData(String tableName) {

        String strSql="select sql_calc_found_rows * from `"+tableName+"` limit 0,20";
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List pageList= commonDBService.procedureDaoList(param);
        Integer count=(Integer) param.get("total");
        if(count>0){
            return ResponseUtil.ok(true);
        } else
        {
            return ResponseUtil.ok(false);
        }
    }

    /**
     * 表内容详情
     *
     * @param tableName
     * @return
     */
//    @RequiresPermissions("admin:tableModify:read")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="详情")
    @GetMapping("/read")
    public Object detail(@NotNull String tableName) {
        //获取表结构
        ViewTableDef tableInfo= adminTableDespService.getAllTableAndView(tableName);
        //获取所有字段 不包括审计字段和主键字段id
        List<ViewTableFieldDef> fieldsInfo= adminTableDespService.getAllTableFieldsNotIncludeShenJiField(tableName);
        //转换字段格式到前台
//        fieldName: undefined,
//        fieldComment: undefined,
//        fieldType: undefined,
//        fieldLength: undefined,
//        fieldIfNull: undefined,
//        fieldDefault: undefined,
        JSONObject result=new JSONObject();
        JSONArray jsonArray=convertField(fieldsInfo);
        result.put("table",tableInfo);
        result.put("fields",jsonArray);

        return ResponseUtil.ok(result);
    }
    private JSONArray convertField(List<ViewTableFieldDef> fieldsInfo){
        JSONArray jsonArray=new JSONArray();
        for(ViewTableFieldDef fieldDef:fieldsInfo){
            JSONObject result=new JSONObject();
            result.put("fieldName",fieldDef.getColumnName());
            result.put("fieldComment",fieldDef.getColumnComment());
            String strLeixing= fieldDef.getLeiXing();
            result.put("fieldType",strLeixing);
            result.put("fieldIfNull",fieldDef.getFieldIfNull());
            result.put("fieldLength",fieldDef.getFieldLength());
            result.put("fieldDefault",fieldDef.getFieldDefault());
            jsonArray.add(result);
        }
        return jsonArray;
    }

    /**
     * 删除某个表，在删除之前要先提醒用户，让用户来判断
     * @return
     */
    @RequiresPermissions("admin:tableModify:delete")
    @RequiresPermissionsDesc(menu={"代码字典" , "库表维护"}, button="删除")
    @GetMapping("/delete")
    public Object deleteTable(String tableName) {
        String strSql="DROP TABLE IF EXISTS `"+tableName+"`;";
        commonDBService.procedureExec(strSql);
        return ResponseUtil.ok(strSql);
    }





}
