package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.java.bi.admin.util.PermissionUtil;
import org.java.bi.admin.service.LogHelper;
import org.java.bi.admin.util.AdminResponseCode;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.admin.util.Permission;
import org.java.bi.admin.vo.MenuClassVo;
import org.java.bi.core.util.IpUtil;
import org.java.bi.core.util.JacksonUtil;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysBiUser;
import org.java.bi.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/auth")
@Validated
@Api(description = "后台管理/登陆授权:/admin/auth")
public class AuthController {
    private final Log logger = LogFactory.getLog(AuthController.class);

    @Autowired
    private SysBiUserService adminService;
    @Autowired
    private SysBiRoleService roleService;

    @Autowired
    private LogHelper logHelper;

    @Autowired
    private SysRoleMenuService rolemenuService;

    @Autowired
    private CommonDBService commonDBService;

    @Autowired
    private SysBiMenuService sysBiMenuService;
    @Autowired
    private SysBiMenuController sysBiMenuController;

    /*
     *  { username : value, password : value }
     */
    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException uae) {
            logHelper.logAuthFail("登录", "用户帐号或密码不正确");
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            logHelper.logAuthFail("登录", "认证失败");
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "认证失败");
        }

        currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();

        if(admin.getStopFlag()!=null&&admin.getStopFlag().equals(true)){
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_ACCOUNT, "该帐户已经停用");
        }

        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(LocalDateTime.now());
        admin.setUpdateBy(GetCurrentUser.getCurrentUserName());
        adminService.updateById(admin);

        logHelper.logAuthSucceed("登录");
        return ResponseUtil.ok(currentUser.getSession().getId());
    }

    /*
     *
     */
    @RequiresAuthentication
    @PostMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();

        logHelper.logAuthSucceed("退出");
        currentUser.logout();
        return ResponseUtil.ok();
    }


    @RequiresAuthentication
    @GetMapping("/info")
    public Object info() {
        Subject currentUser = SecurityUtils.getSubject();
        SysBiUser admin = (SysBiUser) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", admin.getId());
        data.put("deptId", admin.getDeptId());
        data.put("deptIdString", admin.getDeptIdString());
        data.put("deptName", admin.getDeptName());
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        //如果是超级管理员，则拥有所有的角色
        Boolean ifAdminRole =false;

        if(admin.getUsername().equals("AdminSys")){
            data.put("ifAdminRole", true);
        }

        Integer[] roleIds = admin.getRoleIds();
        if(roleIds!=null&&!admin.getUsername().equals("AdminSys")) {
            Set<String> roles = roleService.queryByIds(roleIds);
            data.put("roles", roles);
            //如果是超级管理员，则拥有所有菜单角色
            //如果是超级管理员，则拥有所有菜单角色
            //判断角色中是否包含系统管理员
            ifAdminRole = roleService.judgeIfInclueAdminRole(Arrays.asList(roleIds));
            data.put("ifAdminRole", ifAdminRole);
        }
        if (ifAdminRole||admin.getUsername().equals("AdminSys")) {
            //加载所有菜单
            List<MenuClassVo> menus= sysBiMenuController.getMenuList(null,null);
            data.put("menus", menus);
        } else {
            List<Map<String, Object>> thirdLevelMenus = rolemenuService.getMenuRoleViewBySql(Arrays.asList(roleIds), null, null, null, null);
            List<Object> menuIdList = thirdLevelMenus.stream().map(item -> item.get("menuId")).collect(Collectors.toList());
            if (menuIdList == null || menuIdList.size() <= 0) {
                data.put("menus", null);
            } else {
                List<Integer> idList = new ArrayList<>();
                menuIdList.forEach(item -> {
                    idList.add(Integer.parseInt(item.toString()));
                });
                List<MenuClassVo> menus = sysBiMenuController.getMenuListByIdList(idList);
                data.put("menus", menus);
            }
        }
        return ResponseUtil.ok(data);
    }

    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> systemPermissionsMap = null;

    private Collection<String> toAPI(Set<String> permissions) {
        if (systemPermissionsMap == null) {
            systemPermissionsMap = new HashMap<>();
            final String basicPackage = "org.java.bi.admin";
            List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                systemPermissionsMap.put(perm, api);
            }
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissions) {
            String api = systemPermissionsMap.get(perm);
            apis.add(api);

            if (perm.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
//                return systemPermissionsMap.values();

            }
        }
        return apis;
    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }
}
