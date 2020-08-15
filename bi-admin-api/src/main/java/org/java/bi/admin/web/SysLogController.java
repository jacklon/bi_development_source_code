package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.core.util.ResponseUtil;
import org.java.bi.db.domain.SysLog;
import org.java.bi.db.service.SysLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/log")
@Validated
@Api(description = "后台管理/系统管理/操作日志:/admin/log")
public class SysLogController {
    private final Log logger = LogFactory.getLog(SysLogController.class);

    @Autowired
    private SysLogService logService;

//    @RequiresPermissions("admin:log:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "操作日志"}, button="查询")
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<SysLog> logList = logService.querySelective(name, page, limit, sort);
        return ResponseUtil.okList(logList);
    }
}
