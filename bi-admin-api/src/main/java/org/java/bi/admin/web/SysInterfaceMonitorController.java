package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysInterfaceMonitor;
import org.java.bi.db.service.SysInterfaceMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/interfacemonitor")
@Validated
@Api(description = "后台管理/消息管理/接口消息监控:/admin/interfacemonitor")
public class SysInterfaceMonitorController {
    private final Log logger = LogFactory.getLog(SysInterfaceMonitorController.class);

    @Autowired
    private SysInterfaceMonitorService interfaceMonitorService;

//    @RequiresPermissions("admin:interfacemonitor:list")
//    @RequiresPermissionsDesc(menu={"消息管理","接口消息监控"},button = "查询")
    @GetMapping("list")
    public Object List( String logSourceCode,String logDirectionCode,String logTypeCode,String logContent,
                         Integer errorFlag,String startDate,String endDate,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ){
        List<SysInterfaceMonitor> homeIconList=interfaceMonitorService.querySelective(
                logSourceCode,logDirectionCode,logTypeCode,logContent,errorFlag,startDate,endDate,page,limit,sort);
        return ResponseUtil.okList(homeIconList);
    }

//    @RequiresPermissions("admin:interfacemonitor:read")
//    @RequiresPermissionsDesc(menu={"消息管理","接口消息监控"},button = "详情")
    @GetMapping("read")
    public Object read(@NotNull Integer id){
        SysInterfaceMonitor interfaceMonitor=interfaceMonitorService.findById(id);
        return ResponseUtil.ok(interfaceMonitor);
    }

}
