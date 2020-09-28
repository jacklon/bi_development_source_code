//package org.java.bi.admin.service;
//
//import com.github.pagehelper.PageHelper;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.java.bi.admin.dto.TableDespAllinone;
//import org.java.bi.admin.util.CamelConvert;
//import org.java.bi.admin.util.GetCurrentUser;
//import org.java.bi.ck.config.ClickHouseConfigProperties;
//import org.java.bi.ck.service.CkService;
//import org.java.bi.core.util.ResponseUtil;
//import org.java.bi.db.dao.SysTableFieldDespMapper;
//import org.java.bi.db.domain.*;
//import org.java.bi.db.service.BDicCodeService;
//import org.java.bi.db.service.CommonDBService;
//import org.java.bi.db.service.SysTableDespService;
//import org.java.bi.db.service.SysTableFieldDespService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.java.bi.admin.util.AdminResponseCode.DICMAIN_NAME_EXIST;
//
//@Service
//public class AdminTableDespService {
//    private final Log logger = LogFactory.getLog(AdminTableDespService.class);
//
//    @Autowired
//    private SysTableDespService sysTableDespService;
//    @Autowired
//    private SysTableFieldDespService sysTableFieldDespService;
//    @Resource
//    private SysTableFieldDespMapper sysTableFieldDespMapper;
//
//    @Autowired
//    private CommonDBService commonDBService;
//
//    @Autowired
//    private BDicCodeService litemallDiccodeService;
//
//    @Autowired
//    private ClickHouseConfigProperties clickHouseConfigProperties;
//
//    @Autowired
//    private CkService ckService;
//
//
//
//    public Object list( String dbTableOrViewName,String chineseDesc,
//                        String fieldName,String fieldChineseDesp,
//                       Integer page, Integer limit, String sort) {
//        List<SysTableDesp> sysTableDespList = sysTableDespService.querySelective(
//                dbTableOrViewName, chineseDesc,fieldName,fieldChineseDesp, page, limit, sort);
//        if(sysTableDespList==null){
//            return ResponseUtil.ok();
//        }
//        return ResponseUtil.okList(sysTableDespList);
//    }
//
//    public Object queryAll() {
//        List<SysTableDesp> sysTableDespList = sysTableDespService.queryAll();
//        return ResponseUtil.okList(sysTableDespList);
//    }
//
//    private Object validate(TableDespAllinone tableDespAllinone) {
//        SysTableDesp sysTableDesp = tableDespAllinone.getTableDesp();
//        String name = sysTableDesp.getChineseDesc();
//        if (StringUtils.isEmpty(name)) {
//            return ResponseUtil.fail(401, "表描述信息不能为空");
//        }
//        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();
//        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
//            String codeName = sysTalbeField.getFieldChineseDesp();
//            if (StringUtils.isEmpty(codeName)) {
//                return ResponseUtil.fail(401, "表字段描述信息不能为空");
//            }
//
//        }
//
//        return null;
//    }
//
//    /**
//     * 编辑表字段描述
//     * <p>
//     * TODO
//     * 编辑字段描述表，需要判断是否明细主键ID已经存在，如果存在，则更新信息，如果不存在，则插入数据
//     */
//    @Transactional
//    public Object update(TableDespAllinone tableDespAllinone) {
//        Object error = validate(tableDespAllinone);
//        if (error != null) {
//            return error;
//        }
//        SysTableDesp sysTableDesp=tableDespAllinone.getTableDesp();
//        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();
//        sysTableDesp.setDbName(clickHouseConfigProperties.getDb());
//        Integer mainId = sysTableDesp.getId();
//        if(mainId==null){
//
//            sysTableDesp.setAddBy(GetCurrentUser.getCurrentUserName());
//            sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
//            sysTableDespService.add(sysTableDesp);
//
//        }else
//        {
//            sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
//            sysTableDespService.updateById(sysTableDesp);
//            //
//        }
//
//
//        // 字段子表
//        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
//            sysTalbeField.setStdId(sysTableDesp.getId());
//            sysTalbeField.setStdChineseDesc(sysTableDesp.getChineseDesc());
//
////            Integer codeId=sysTalbeField.getId();
//            SysTableFieldDesp findQuery= sysTableFieldDespService.getByFieldName(mainId,sysTalbeField.getFieldName());
//            if(findQuery==null){
//                sysTalbeField.setStdId(sysTableDesp.getId());
//                sysTalbeField.setStdChineseDesc(sysTableDesp.getChineseDesc());
//                sysTalbeField.setAddBy(GetCurrentUser.getCurrentUserName());
//                sysTalbeField.setUpdateBy(GetCurrentUser.getCurrentUserName());
//                sysTableFieldDespService.add(sysTalbeField);
//            } else
//            {
//
//
//                findQuery.setStdChineseDesc(sysTalbeField.getStdChineseDesc());
//                findQuery.setFieldChineseDesp(sysTalbeField.getFieldChineseDesp());
//                findQuery.setFieldType(sysTalbeField.getFieldType());
//                findQuery.setFieldRemark(sysTalbeField.getFieldRemark());
//                findQuery.setOrdernumber(sysTalbeField.getOrdernumber());
//                findQuery.setUpdateBy(sysTalbeField.getUpdateBy());
//                findQuery.setUpdateTime(LocalDateTime.now());
//                findQuery.setUpdateBy(GetCurrentUser.getCurrentUserName());
//                findQuery.setDeleted(sysTalbeField.getDeleted());
//                sysTableFieldDespService.updateAllFieldById(findQuery);
//
//
//            }
//
//
//
//        }
//
//        return ResponseUtil.ok();
//    }
//
//    /**
//     * 删除字典表
//     * @param sysTableDesp
//     * @return
//     */
//    @Transactional
//    public Object delete(SysTableDesp sysTableDesp) {
//        Integer id = sysTableDesp.getId();
//        if (id == null) {
//            return ResponseUtil.badArgument();
//        }
//
//        sysTableDespService.deleteById(id);
//        sysTableFieldDespService.deleteByMainId(id);
//
//        return ResponseUtil.ok();
//    }
//
//    @Transactional
//    public Object create(TableDespAllinone tableDespAllinone) {
//        Object error = validate(tableDespAllinone);
//        if (error != null) {
//            return error;
//        }
//
//        SysTableDesp sysTableDesp = tableDespAllinone.getTableDesp();
//        SysTableFieldDesp[] sysTalbeFields = tableDespAllinone.getFieldsDesp();
//
//        String name = sysTableDesp.getChineseDesc();
//        if (sysTableDespService.checkExistByName(name)) {
//            return ResponseUtil.fail(DICMAIN_NAME_EXIST, "表描述名称已经存在");
//        }
//        //保存描述主表
//        sysTableDesp.setDbName(clickHouseConfigProperties.getDb());
//        sysTableDesp.setAddBy(GetCurrentUser.getCurrentUserName());
//        sysTableDesp.setUpdateBy(GetCurrentUser.getCurrentUserName());
//        sysTableDespService.add(sysTableDesp);
//        // 保存描述子表
//        for (SysTableFieldDesp sysTalbeField : sysTalbeFields) {
//            sysTalbeField.setStdId(sysTableDesp.getId());
//            sysTalbeField.setStdChineseDesc(sysTableDesp.getChineseDesc());
//            //转换驼峰命名字段
//
//            sysTalbeField.setAddBy(GetCurrentUser.getCurrentUserName());
//            sysTalbeField.setUpdateBy(GetCurrentUser.getCurrentUserName());
//            sysTableFieldDespService.add(sysTalbeField);
//        }
//        return ResponseUtil.ok();
//    }
//
//    public Object getTableDespAndFields(Integer id) {
//        SysTableDesp sysTableDesp = sysTableDespService.findById(id);
//        List<SysTableFieldDesp> sysTalbeFields = sysTableFieldDespService.queryByMainId(id);
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("tableDesp", sysTableDesp);
//        data.put("fieldsDesp", sysTalbeFields);
//
//        return ResponseUtil.ok(data);
//    }
//
//    public Object getTableDespById(Integer id) {
//        SysTableDesp sysTableDesp = sysTableDespService.findById(id);
//        return ResponseUtil.ok(sysTableDesp);
//    }
//
//    public List getAllTable(){
//        String dbName= clickHouseConfigProperties.getDb();
//        String strSql="select * from system.tables t where database='"+dbName+"'";
//        List<Map<String,String>> allTables= ckService.exeSql(strSql);
//        return allTables;
//    }
//    public List getAllTableFields(String tableOrViewName){
//        String dbName= clickHouseConfigProperties.getDb();
//        String strSql="select * from `system`.columns c where database='"+dbName
//                +"' and table='"+tableOrViewName+"'";
//        List<Map<String,String>> allTables= ckService.exeSql(strSql);
//        return allTables;
//    }
//
//
//
//}
