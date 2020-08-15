package org.java.bi.admin.web;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysOperationLog;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.service.SysOperationLogService;
import org.java.bi.db.service.SysTableDespService;
import org.java.bi.db.service.SysTableFieldDespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/sysOperationLog")
@Validated
@Api(description = "系统管理/数据操作日志:/admin/sysOperationLog")
public class SysOperationLogController {
    private final Log logger = LogFactory.getLog(SysOperationLogController.class);

    @Autowired
    private SysOperationLogService sysOperationLogService;


    @Autowired
    private SysTableDespService sysTableDespService;

    @Autowired
    private SysTableFieldDespService sysTableFieldDespService;



//    @RequiresPermissions("admin:sysOperationLog:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "数据操作日志"}, button="查询")
    @GetMapping("/list")
    public Object list(String flowInstanceId, String logRemark,
                       String addBy,String startOpTime,String endOpTime,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<SysOperationLog> sysOperationLog = sysOperationLogService.querySelective(
                flowInstanceId,logRemark,addBy,startOpTime,endOpTime,pageNum, pageSize);
        return ResponseUtil.okList(sysOperationLog);
    }

}
