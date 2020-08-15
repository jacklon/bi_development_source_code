package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.admin.vo.BCorpInfoVo;
import org.java.bi.core.util.CharUtil;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.BCorpInfo;
import org.java.bi.db.service.BCorpInfoService;
import org.java.bi.db.service.CommonDBService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/admin/bCorpInfo")
@Validated
@Api(description = "后台管理/系统管理/组织机构:/admin/bCorpInfo")
public class BCorpInfoController {
    private final Log logger = LogFactory.getLog(BCorpInfoController.class);

    @Autowired
    private BCorpInfoService bCorpInfoService;

    @Autowired
    private CommonDBService commonDBService;



//    @RequiresPermissions("admin:bCorpInfo:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="查询")
    @GetMapping("/list")
    public Object list(Integer id, String name) {
        //分组获取各部门流程数据
        String strSql="select flow_dept_id as flowDeptId, count(*) as count from flow_start_main " +
                "where deleted=0 group by flow_dept_id";
        List<Map<String, Object>> groupFlowStartMain= commonDBService.procedureDaoList(strSql);

        List<BCorpInfoVo> bCorpInfoVoList = new ArrayList<>();
        List<BCorpInfo> bCorpInfoList=bCorpInfoService.list(id,name);
        List<BCorpInfoVo> result = new ArrayList<>();
        for(BCorpInfo bCorpInfo:bCorpInfoList){
            BCorpInfoVo temp=new BCorpInfoVo();
            BeanUtils.copyProperties(bCorpInfo,temp);
            bCorpInfoVoList.add(temp);
        }

        // 1、获取第一级节点
        for (BCorpInfoVo bCorpInfoVo : bCorpInfoVoList) {
//            if(0 == bCorpInfoVo.getPid()) {
                result.add(bCorpInfoVo);
//            }
        }
        // 2、递归获取子节点
        for (BCorpInfoVo parent : result) {
            parent = recursiveTree(parent, bCorpInfoVoList,groupFlowStartMain);
        }

        return ResponseUtil.okList(result);
    }

    @GetMapping("/listAll")
    public Object listAll(Integer id, String name) {

        //分组获取各部门流程数据
        String strSql="select flow_dept_id as flowDeptId, count(*) as count from flow_start_main " +
                "where deleted=0 group by flow_dept_id";
        List<Map<String, Object>> groupFlowStartMain= commonDBService.procedureDaoList(strSql);

        List<BCorpInfoVo> bCorpInfoVoList = new ArrayList<>();
        List<BCorpInfo> bCorpInfoList=bCorpInfoService.list(id,name);
        List<BCorpInfoVo> result = new ArrayList<>();
        for(BCorpInfo bCorpInfo:bCorpInfoList){
            BCorpInfoVo temp=new BCorpInfoVo();
            BeanUtils.copyProperties(bCorpInfo,temp);
            bCorpInfoVoList.add(temp);
        }

        // 1、获取第一级节点
        for (BCorpInfoVo bCorpInfoVo : bCorpInfoVoList) {
            if(0 == bCorpInfoVo.getPid()) {
                Optional<Object> findResult= groupFlowStartMain.stream().filter((item)->item.get("flowDeptId").
                        equals(bCorpInfoVo.getId())).findFirst().map(x -> x.get("count"));
                if(findResult.isPresent()){
                    bCorpInfoVo.setFlowDataCount(CharUtil.objectConverToInteger(findResult.get()));
                }
                result.add(bCorpInfoVo);
            }
        }
        // 2、递归获取子节点
        for (BCorpInfoVo parent : result) {
            parent = recursiveTree(parent, bCorpInfoVoList,groupFlowStartMain);

        }

        return ResponseUtil.okList(result);
    }

    private BCorpInfoVo recursiveTree(BCorpInfoVo parent, List<BCorpInfoVo> list,List<Map<String, Object>> groupFlowStartMain) {
        for (BCorpInfoVo bCorpInfoVo : list) {
            if(parent.getId().equals(bCorpInfoVo.getPid()) ) {
                bCorpInfoVo = recursiveTree(bCorpInfoVo, list,groupFlowStartMain);
                BCorpInfoVo temp=bCorpInfoVo;
                Optional<Object> findResult= groupFlowStartMain.stream().filter((item)->item.get("flowDeptId").
                        equals(temp.getId())).findFirst().map(x -> x.get("count"));
                if(findResult.isPresent()){
                    bCorpInfoVo.setFlowDataCount(CharUtil.objectConverToInteger(findResult.get()));
                }

                if(parent.getChildren()==null){
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(bCorpInfoVo);
            }
        }
        return parent;
    }

    private Object validate(BCorpInfo bCorpInfo) {
        String name = bCorpInfo.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.fail(502,"分类名称不能为空");
        }
        if(bCorpInfoService.checkExistByKeyAndName(bCorpInfo.getId(),bCorpInfo.getName())){
            return ResponseUtil.fail(502,"分类名称已经存在");
        }
        return null;
    }

//    @RequiresPermissions("admin:bCorpInfo:create")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="添加")
    @PostMapping("/create")
    public Object create(@RequestBody BCorpInfo bCorpInfo) {
        Object error = validate(bCorpInfo);
        if (error != null) {
            return error;
        }
        bCorpInfo.setAddBy(GetCurrentUser.getCurrentUserName());
        bCorpInfo.setUpdateBy(GetCurrentUser.getCurrentUserName());
        bCorpInfoService.add(bCorpInfo);
        return ResponseUtil.ok(bCorpInfo);
    }

//    @RequiresPermissions("admin:bCorpInfo:read")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        BCorpInfo bCorpInfo = bCorpInfoService.findById(id);
        return ResponseUtil.ok(bCorpInfo);
    }

//    @RequiresPermissions("admin:bCorpInfo:update")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="编辑")
    @PostMapping("/update")
    public Object update(@RequestBody BCorpInfo bCorpInfo) {
        Object error = validate(bCorpInfo);
        if (error != null) {
            return error;
        }
        bCorpInfo.setUpdateBy(GetCurrentUser.getCurrentUserName());
        if (bCorpInfoService.updateById(bCorpInfo) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        //更新操作员所属部门表
        String strSql="update litemall_admin set dept_name='"+bCorpInfo.getName()+"' where dept_id="+
                bCorpInfo.getId();
        commonDBService.procedureExec(strSql);

        //更新用户数据表
        strSql="select  table_name  from view_table_field_def where column_name='dept_id' or " +
                "column_name='dept_name' group by table_name having count(*)=2";
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List<Map<String, Object>> resultTables=commonDBService.procedureDaoList(param);
        if(resultTables!=null||resultTables.size()>0) {
            for (Map<String, Object> item : resultTables) {
                if(!StringUtils.isEmpty(item.get("table_name"))){
                   String strTableName=item.get("table_name").toString();
                   strSql="update `"+strTableName+"` set dept_name='"+bCorpInfo.getName()+"' where dept_id="+
                            bCorpInfo.getId();
                   System.out.println(strSql);
                   commonDBService.procedureExec(strSql);
                }

            }
        }

        return ResponseUtil.ok();
    }

//    @RequiresPermissions("admin:bCorpInfo:delete")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody BCorpInfo bCorpInfo) {
        Integer id = bCorpInfo.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        List<Integer> bCorpList= bCorpInfoService.recursiveChildDeptById(bCorpInfo.getId());

        //判断是否该部门已经发起过流程数据
        //有可能用户新增了数据，但没有发起流程，则这种情况也允许用户删除部门
        String strSql="select count(*) as count from flow_start_main where deleted=0 and flow_dept_id in ("+CharUtil.ListInterToString(bCorpList)+")";
        Object result= commonDBService.procedureDaoListFirstValue(strSql,"count");
        if(result!=null|| CharUtil.objectConverToInteger(result)>0){
            return ResponseUtil.fail(502,"部门【"+bCorpInfo.getName()+"】及子部门已经有流程数据录入，请先迁移数据到其它部门，再尝试删除");
        }

        bCorpInfoService.deleteById(id);
        return ResponseUtil.ok();
    }


//    @RequiresPermissions("admin:bCorpInfo:deletelist")
//    @RequiresPermissionsDesc(menu={"系统管理" , "组织机构"}, button="层级删除")
    @PostMapping("/deletelist")
    public Object deletelist(@RequestBody List<Integer> menuIds) {

        if (menuIds== null||menuIds.size()==0) {
            return ResponseUtil.fail(502,"是删除的菜单项不能为空");
        }

        bCorpInfoService.deleteByIdList(menuIds);
        return ResponseUtil.ok();
    }
    @GetMapping("/dataTransNewDept")
    public Object read(@NotNull Integer sourceDeptId,@NotNull Integer targetDeptId) {
        //将人员信息由原部门更新至新部门
        BCorpInfo sourceDept=bCorpInfoService.findById(sourceDeptId);
        BCorpInfo targetDept=bCorpInfoService.findById(targetDeptId);
        String strSql=" update litemall_admin a,b_corp_info b set a.dept_id="+targetDeptId+",a.dept_name=b.name," +
                " a.dept_id_string=b.dept_id_string where a.dept_id="+sourceDeptId+" and b.id="+targetDeptId;
        commonDBService.procedureExec(strSql);
        //将流程信息中原部门更新至新部门
        strSql=" update flow_start_main a,b_corp_info b set a.flow_dept_id="+targetDeptId+",a.flow_dept_path=b.name " +
                " where a.flow_dept_id="+sourceDeptId+" and b.id="+targetDeptId;
        commonDBService.procedureExec(strSql);
        //  将业务数据原部门更新至新部门
        //更新用户数据表
        strSql="select  table_name  from view_table_field_def where column_name='dept_id' or " +
                "column_name='dept_name' group by table_name having count(*)=2";
        Map<String, Object> param=new HashMap<>();
        param.put("sqlS",strSql);
        List<Map<String, Object>> resultTables=commonDBService.procedureDaoList(param);
        if(resultTables!=null||resultTables.size()>0) {
            for (Map<String, Object> item : resultTables) {
                if(!StringUtils.isEmpty(item.get("table_name"))){
                    String strTableName=item.get("table_name").toString();
                    strSql=" update `"+strTableName+"` a,b_corp_info b set a.dept_id="+targetDeptId+",a.dept_name=b.name " +
                            "  where a.dept_id="+sourceDeptId+" and b.id="+targetDeptId;
                    System.out.println(strSql);
                    commonDBService.procedureExec(strSql);
                }

            }
        }
        return ResponseUtil.ok();
    }



}
