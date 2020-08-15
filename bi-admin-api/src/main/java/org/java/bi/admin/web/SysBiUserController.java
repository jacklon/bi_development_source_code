package org.java.bi.admin.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.java.bi.admin.dto.SysBiUserAllinone;
import org.java.bi.admin.service.LogHelper;
import org.java.bi.admin.util.AdminResponseCode;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.CharUtil;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.core.util.bcrypt.BCryptPasswordEncoder;

import org.java.bi.db.domain.SysBiUser;
import org.java.bi.db.domain.SysUserFieldPriv;
import org.java.bi.db.service.BCorpInfoService;
import org.java.bi.db.service.BDicCodeService;
import org.java.bi.db.service.SysBiUserService;
import org.java.bi.db.service.SysUserFieldPrivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/admin")
@Validated
@Api(description = "后台管理/系统管理/管理员管理:/admin/admin")
public class SysBiUserController {
    private final Log logger = LogFactory.getLog(SysBiUserController.class);

    @Autowired
    private SysBiUserService adminService;
    @Autowired
    private LogHelper logHelper;

    @Autowired
    private BCorpInfoService bCorpInfoService;

    @Autowired
    private SysUserFieldPrivService adminFieldPrivService;

    @Autowired
    private BDicCodeService dicCodeService;

//    @RequiresPermissions("admin:admin:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员管理"}, button="查询")
    @GetMapping("/list")
    public Object list(String username,
                       String telphone,
                       Boolean stopFlag,
                       @RequestParam(required = false)List<Integer[]> roleIds,
                       @RequestParam(required = false)List<Integer> deptIds,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
//        List<SysBiUser> adminList = adminService.querySelective(username,stopFlag,roleIds,deptIds, page, limit, sort);

        Map<String,Object> result=adminService.querySelective(username,telphone,stopFlag,roleIds,deptIds, page, limit, sort);
        List dataList=(List)result.get("data");
        Integer count=(Integer)result.get("total");
        return ResponseUtil.okList(dataList,count,page, limit);

//        return ResponseUtil.okList(adminList);
    }

    @GetMapping("/queryall")
    public Object queryall() {
        List<SysBiUser> adminList = adminService.queryall();
        return ResponseUtil.okList(adminList);
    }

    @GetMapping("/queryallByDeptId")
    public Object queryallByDeptId(Integer deptId) {
        List<SysBiUser> adminList = adminService.queryallByDeptId(deptId);
        return ResponseUtil.okList(adminList);
    }

    private Object validate(SysBiUser admin) {
        String name = admin.getUsername();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.fail(502,"管理员名称不能为空");
        }
//        if (!RegexUtil.isUsername(name)) {
//            return ResponseUtil.fail(ADMIN_INVALID_NAME, "管理员名称不符合规定");
//        }
//        String password = admin.getPassword();
//        if (StringUtils.isEmpty(password) || password.length() < 6) {
//            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
//        }
        return null;
    }

    private Object validateAdminFieldPriv(SysUserFieldPriv admin) {
        Integer userId = admin.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return ResponseUtil.fail(502,"管理员名称不能为空");
        }
        Integer sysTableDespId = admin.getSysTableDespId();
        if (StringUtils.isEmpty(sysTableDespId)) {
            return ResponseUtil.fail(502,"表描述不能为空");
        }
        String fieldName = admin.getFieldName();
        if (StringUtils.isEmpty(fieldName)) {
            return ResponseUtil.fail(502,"表字段名不能为空");
        }
        String fieldOper = admin.getFieldOper();
        if (StringUtils.isEmpty(fieldOper)) {
            return ResponseUtil.fail(502,"操作符名不能为空");
        }
        String fieldValue = admin.getFieldValue();
        if (StringUtils.isEmpty(fieldValue)) {
            return ResponseUtil.fail(502,"表字段值不能为空");
        }

        return null;
    }


//    @RequiresPermissions("admin:admin:create")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员管理"}, button="添加")
    @PostMapping("/create")
    public Object create(@RequestBody SysBiUserAllinone sysBiUserAllinone) {
        SysBiUser admin= sysBiUserAllinone.getAdmin();
        Object error = validate(admin);
        if (error != null) {
            return error;
        }
        String username = admin.getUsername();
        List<SysBiUser> adminList = adminService.findAdmin(username);
        if (adminList.size() > 0) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_NAME_EXIST, "管理员已经存在");
        }
        String rawPassword = admin.getPassword();
        //设置一个默认密码
        if(StringUtils.isEmpty(rawPassword)){
            rawPassword="123456";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);

        admin.setAddBy(GetCurrentUser.getCurrentUserName());
        admin.setUpdateBy(GetCurrentUser.getCurrentUserName());
        adminService.add(admin);
        logHelper.logAuthSucceed("添加管理员", username);

        this.saveAdminFieldPriv(admin, sysBiUserAllinone.getAdminFieldPrivs());


        return ResponseUtil.ok(admin);
    }

    private void saveAdminFieldPriv(SysBiUser admin,List<SysUserFieldPriv> adminFieldPrivs){
        //填加操作用户的表字段权限
        for(SysUserFieldPriv adminFieldPriv:adminFieldPrivs) {
            adminFieldPriv.setUserId(admin.getId());
            adminFieldPriv.setUsername(admin.getUsername());
            Object error = validateAdminFieldPriv(adminFieldPriv);
            if (error != null) {
                //抛弃掉当前的这条记录
//                return error;
            }
//            Boolean ifExists = adminFieldPrivService.checkIfExists(adminFieldPriv);
            if (adminFieldPriv.getId()!=null) {
                adminFieldPriv.setUpdateBy(GetCurrentUser.getCurrentUserName());
                adminFieldPrivService.updateById(adminFieldPriv);
//            return ResponseUtil.fail(502,"已经存在相同的字段设置");
            } else {
                adminFieldPriv.setAddBy(GetCurrentUser.getCurrentUserName());
                adminFieldPriv.setUpdateBy(GetCurrentUser.getCurrentUserName());
                adminFieldPrivService.add(adminFieldPriv);
            }
        }
    }

    private Integer[] getDataDeptIds(String[] strDataDeptString){
        if(strDataDeptString==null||strDataDeptString.length==0){
            return new Integer[]{};
        }
        ArrayList al=new ArrayList();
        for(int ii=0;ii<strDataDeptString.length;ii++){
            String tempStr=strDataDeptString[ii];
            String[] arrStr=tempStr.split(",");
            if(arrStr!=null&&arrStr.length>0) {
                //每一级只取最后一个节点
                al.add(Integer.parseInt(arrStr[arrStr.length-1]));
            }
        }
        ArrayList deptIds=(ArrayList)al.stream().distinct().collect(Collectors.toList());
        List<Integer> deptIdsAndChilds = bCorpInfoService.recursiveChildDeptByIds(deptIds);
        if(deptIdsAndChilds==null||deptIdsAndChilds.size()==0){
            return null;
        }
        Integer[] idArray = new Integer[deptIdsAndChilds.size()];

        return deptIdsAndChilds.toArray(idArray);
    }

//    @RequiresPermissions("admin:admin:read")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员管理"}, button="详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        SysBiUser admin = adminService.findById(id);

        List<SysUserFieldPriv> privList= adminFieldPrivService.querySelective(id,null);

        SysBiUserAllinone sysBiUserAllinone =new SysBiUserAllinone();

        //不获取用户的密码
        admin.setPassword("");

        sysBiUserAllinone.setAdmin(admin);
        sysBiUserAllinone.setAdminFieldPrivs(privList);
        return ResponseUtil.ok(sysBiUserAllinone);
    }

//    @RequiresPermissions("admin:admin:update")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员管理"}, button="编辑")
    @PostMapping("/update")
    public Object update(@RequestBody SysBiUserAllinone sysBiUserAllinone) {
        SysBiUser admin= sysBiUserAllinone.getAdmin();
        Object error = validate(admin);
        if (error != null) {
            return error;
        }
        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }
        // 如果是修改用户，则不允许直接修改密码，如果是新建用户，则密码设置有效
        if(admin.getId()==null) {
            if (!StringUtils.isEmpty(admin.getPassword())) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode(admin.getPassword());
                admin.setPassword(encodedPassword);
            }
        }else
        {
            //将原用户密码信息取回
            SysBiUser litemallAdmin= adminService.findById(admin.getId());
            admin.setPassword(litemallAdmin.getPassword());
        }

        admin.setUpdateBy(GetCurrentUser.getCurrentUserName());
        if (adminService.updateAllById(admin) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        logHelper.logAuthSucceed("编辑管理员", admin.getUsername());

        this.saveAdminFieldPriv(admin, sysBiUserAllinone.getAdminFieldPrivs());

        return ResponseUtil.ok(admin);
    }

//    @RequiresPermissions("admin:admin:modifyPassword")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员管理"}, button="修改密码")
    @PostMapping("/modifyPassword")
    public Object modifyPassword(@RequestBody SysBiUser admin) {

        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }

        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        admin.setUpdateBy(GetCurrentUser.getCurrentUserName());
        adminService.updateById(admin);

        return ResponseUtil.ok(admin);
    }


//    @PostMapping("/delete")
//    public Object delete(@RequestBody SysBiUser admin) {
//        Integer anotherAdminId = admin.getId();
//        if (anotherAdminId == null) {
//            return ResponseUtil.badArgument();
//        }
//
//        // 管理员不能删除自身账号
//        Subject currentUser = SecurityUtils.getSubject();
//        SysBiUser currentAdmin = (SysBiUser) currentUser.getPrincipal();
//        if (currentAdmin.getId().equals(anotherAdminId)) {
//            return ResponseUtil.fail(AdminResponseCode.ADMIN_DELETE_NOT_ALLOWED, "管理员不能删除自己账号");
//        }
//
//        adminService.deleteById(anotherAdminId);
//        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
//        admin.setDeleted(true);
//        actIdService.addOrUpdateUserInfo(admin);
//        return ResponseUtil.ok();
//    }

    @GetMapping("/delete")
    public Object delete(Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        }
        SysBiUser admin=adminService.findById(id);

        // 管理员不能删除自身账号
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser currentAdmin = (SysBiUser) currentUser.getPrincipal();
        if (currentAdmin.getId().equals(id)) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_DELETE_NOT_ALLOWED, "管理员不能删除自己账号");
        }

        adminService.deleteById(id);
        logHelper.logAuthSucceed("删除管理员", admin.getUsername());
        admin.setDeleted(true);

        return ResponseUtil.ok();
    }
    @PostMapping("/changeAvatar")
    public Object changeAvatar(@RequestBody SysBiUser admin) {
        try {
            SysBiUser currentAdmin = GetCurrentUser.getCurrentUser();
//            String avatar = URLDecoder.decode(admin.getAvatar(), "UTF-8");
            currentAdmin.setAvatar(admin.getAvatar());
            currentAdmin.setUpdateBy(GetCurrentUser.getCurrentUserName());
            adminService.updateById(currentAdmin);

            return ResponseUtil.ok();
        }catch (Exception e){
            return ResponseUtil.fail(502,"后台更新失败");
        }
    }

    /**
     * 保存用户查询的流程分类及所选部门
     * @param body
     * @return
     */
    @PostMapping("/updateFlowQuery")
    public Object updateFlowQuery(@RequestBody String body) {
        try {
            //传入条件
            JSONObject formDataMap = (JSONObject) JSONObject.parse(body);

            JSONArray flowTypeIds = (JSONArray) formDataMap.get("lastFindTypeIds");
            // 部门 deptIds,选择部门数据，我们需要在流程发起的时候，由用户选择部门，默认可以根据人员所在的部门设置数据部门

            String[] strArrayFlowTypeIds=null;
            if(flowTypeIds!=null) {
                strArrayFlowTypeIds=new String[flowTypeIds.size()];
                for (int ii = 0; ii < flowTypeIds.size(); ii++) {
                    JSONArray inArray = (JSONArray) flowTypeIds.get(ii);
                    strArrayFlowTypeIds[ii] = CharUtil.JSONArrayToString_ValueInteger(inArray);
                }
            }

            SysBiUser currentAdmin = GetCurrentUser.getCurrentUser();

            currentAdmin.setUpdateBy(GetCurrentUser.getCurrentUserName());
            adminService.updateById(currentAdmin);
            return ResponseUtil.ok(currentAdmin);
        }catch (Exception e){
            return ResponseUtil.fail(502,"后台更新失败");
        }
    }




    /**
     * 获取用户的手机号
     * @param userId
     * @return
     */
    @GetMapping("/getTelPhone")
    public Object getTelPhone(Integer userId) {
        SysBiUser litemallAdmin=  adminService.findById(userId);
        return ResponseUtil.ok(CharUtil.objectConverToString(litemallAdmin.getTelphone()));
    }

    /**
     * 更新用户的手机号
     * @param body
     * @return
     */
    @PostMapping("/updateTelPhone")
    public Object updateTelPhone(@RequestBody String body) {
        JSONObject formDataMap = (JSONObject) JSONObject.parse(body);
        Integer userId =CharUtil.objectConverToInteger(formDataMap.get("userId"));
        String telphone=CharUtil.objectConverToString(formDataMap.get("telphone"));
        SysBiUser litemallAdmin=  adminService.findById(userId);
        litemallAdmin.setTelphone(telphone);
        adminService.updateById(litemallAdmin);
        return ResponseUtil.ok();
    }

}
