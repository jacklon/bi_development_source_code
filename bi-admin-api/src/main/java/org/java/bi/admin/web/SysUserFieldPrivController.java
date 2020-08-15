package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.service.LogHelper;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysUserFieldPriv;
import org.java.bi.db.service.SysUserFieldPrivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/adminFieldPriv")
@Validated
@Api(description = "后台管理/系统管理/管理员数据权限:/admin/adminFieldPriv")
public class SysUserFieldPrivController {
    private final Log logger = LogFactory.getLog(SysUserFieldPrivController.class);

    @Autowired
    private SysUserFieldPrivService adminFieldPrivService;
    @Autowired
    private LogHelper logHelper;

//    @RequiresPermissions("admin:adminFieldPriv:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员数据权限"}, button="查询")
    @GetMapping("/list")
    public Object list(Integer userId,Integer sysTableDespId ) {
        List<SysUserFieldPriv> adminFieldPrivList = adminFieldPrivService.querySelective(userId,sysTableDespId);
        return ResponseUtil.okList(adminFieldPrivList);
    }

    private Object validate(SysUserFieldPriv admin) {
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

//    @RequiresPermissions("admin:adminFieldPriv:create")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员数据权限"}, button="添加")
    @PostMapping("/create")
    public Object create(@RequestBody List<SysUserFieldPriv> adminFieldPrivList) {
        for(SysUserFieldPriv adminFieldPriv:adminFieldPrivList) {
            Object error = validate(adminFieldPriv);
            if (error != null) {
                return error;
            }
            Boolean ifExists = adminFieldPrivService.checkIfExists(adminFieldPriv);
            if (ifExists) {
//            return ResponseUtil.fail(502,"已经存在相同的字段设置");
            }
            adminFieldPriv.setAddBy(GetCurrentUser.getCurrentUserName());
            adminFieldPriv.setUpdateBy(GetCurrentUser.getCurrentUserName());
            adminFieldPrivService.add(adminFieldPriv);
        }
        return ResponseUtil.ok();
    }

//    @RequiresPermissions("admin:adminFieldPriv:read")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员数据权限"}, button="详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        SysUserFieldPriv adminFieldPriv = adminFieldPrivService.findById(id);
        return ResponseUtil.ok(adminFieldPriv);
    }


//    @RequiresPermissions("admin:adminFieldPriv:delete")
//    @RequiresPermissionsDesc(menu={"系统管理" , "管理员数据权限"}, button="删除")
    @GetMapping("/delete")
    public Object delete(Integer id){
        adminFieldPrivService.deleteById(id);
        return ResponseUtil.ok();
    }


}
