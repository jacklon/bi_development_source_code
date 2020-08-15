package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.util.PermissionUtil;
import org.java.bi.admin.util.AdminResponseCode;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.admin.util.Permission;
import org.java.bi.admin.vo.PermVo;
import org.java.bi.core.util.JacksonUtil;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.SysBiRole;
import org.java.bi.db.domain.SysBiUser;
import org.java.bi.db.service.SysBiRoleService;
import org.java.bi.db.service.SysBiUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/role")
@Validated
@Api(description = "后台管理/系统管理/角色管理:/admin/region")
public class SysBiRoleController {
    private final Log logger = LogFactory.getLog(SysBiRoleController.class);

    @Autowired
    private SysBiRoleService roleService;

    @Autowired
    private SysBiUserService adminService;


//    @RequiresPermissions("admin:role:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="角色查询")
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<SysBiRole> roleList = roleService.querySelective(name, page, limit, sort);
        return ResponseUtil.okList(roleList);
    }

    @GetMapping("/options")
    public Object options(){
        List<SysBiRole> roleList = roleService.queryAll();

        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (SysBiRole role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }

        return ResponseUtil.okList(options);
    }

//    @RequiresPermissions("admin:role:read")
//    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="角色详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        SysBiRole role = roleService.findById(id);
        return ResponseUtil.ok(role);
    }


    private Object validate(SysBiRole role) {
        String name = role.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

//    @RequiresPermissions("admin:role:create")
//    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="角色添加")
    @PostMapping("/create")
    public Object create(@RequestBody SysBiRole role) {
        Object error = validate(role);
        if (error != null) {
            return error;
        }

        if (roleService.checkExist(role.getName())){
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "角色已经存在");
        }
        role.setAddBy(GetCurrentUser.getCurrentUserName());
        role.setUpdateBy(GetCurrentUser.getCurrentUserName());
        roleService.add(role);

        return ResponseUtil.ok(role);
    }

//    @RequiresPermissions("admin:role:update")
//    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="角色编辑")
    @PostMapping("/update")
    public Object update(@RequestBody SysBiRole role) {
        Object error = validate(role);
        if (error != null) {
            return error;
        }
        role.setUpdateBy(GetCurrentUser.getCurrentUserName());
        roleService.updateById(role);

        return ResponseUtil.ok();
    }

//    @RequiresPermissions("admin:role:delete")
//    @RequiresPermissionsDesc(menu={"系统管理" , "角色管理"}, button="角色删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody SysBiRole role) {
        Integer id = role.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        // 如果当前角色所对应管理员仍存在，则拒绝删除角色。
        List<SysBiUser> adminList = adminService.all();
        for(SysBiUser admin : adminList){
            Integer[] roleIds = admin.getRoleIds();
            for(Integer roleId : roleIds){
                if(id.equals(roleId)){
                    return ResponseUtil.fail(AdminResponseCode.ROLE_USER_EXIST, "当前角色存在管理员，不能删除");
                }
            }
        }

        roleService.deleteById(id);
        role.setDeleted(true);

        return ResponseUtil.ok();
    }


    @Autowired
    private ApplicationContext context;
    private List<PermVo> systemPermissions = null;
    private Set<String> systemPermissionsString = null;

    private List<PermVo> getSystemPermissions(){
        final String basicPackage = "org.java.bi.admin";
        if(systemPermissions == null){
            List<Permission> permissions = PermissionUtil.listPermission(context, basicPackage);
            systemPermissions = PermissionUtil.listPermVo(permissions);
            systemPermissionsString = PermissionUtil.listPermissionString(permissions);
        }
        return systemPermissions;
    }


}
