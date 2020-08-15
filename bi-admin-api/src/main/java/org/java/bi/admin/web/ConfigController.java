package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.java.bi.core.system.SystemConfig;
import org.java.bi.core.system.SystemInistService;
import org.java.bi.core.util.CharUtil;
import org.java.bi.core.util.JacksonUtil;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.service.CommonDBService;
import org.java.bi.db.service.SysSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/config")
@Validated
@Api(description = "后台管理/配置管理/商场配置:/admin/config")
public class ConfigController {
    private final Log logger = LogFactory.getLog(ConfigController.class);

    @Autowired
    private SysSetService systemConfigService;

    @Autowired
    private SystemInistService systemInistService;

    @Autowired
    private CommonDBService commonDBService;


    //    @RequiresPermissions("admin:config:weishang:list")
//    @RequiresPermissionsDesc(menu={"配置管理" , "首页配置"}, button="详情")
    @GetMapping("/sysParas")
    public Object listSysParas() {
        Map<String, String> data = systemConfigService.listSysParas();
        return ResponseUtil.ok(data);
    }

    //    @RequiresPermissions("admin:config:weishang:updateConfigs")
//    @RequiresPermissionsDesc(menu={"配置管理" , "首页配置"}, button="编辑")
    @PostMapping("/saveConfig")
    public Object updateSysParas(@RequestBody String body) {
        Map<String, String> data = JacksonUtil.toMap(body);
        systemConfigService.updateConfig(data);
        SystemConfig.updateConfigs(data);
        return ResponseUtil.ok();
    }



}
