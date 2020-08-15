package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.SysTableFieldDesp;
import org.java.bi.db.service.SysTableFieldDespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/tableField")
@Api(description = "后台管理/系统管理/表字段管理:/admin/tableField")
public class SysTableFieldDespController {
    private final Log logger = LogFactory.getLog(SysTableFieldDespController.class);

    @Autowired
    private SysTableFieldDespService sysTableFieldDespService;

    /**
     * 查询表字段明细
     * @param oltId
     * @param oltTableDesp
     * @param olfFieldDesp
     * @param sort
     * @return
     */
//    @RequiresPermissions("admin:tableField:list")
//    @RequiresPermissionsDesc(menu={"系统管理" , "表字段管理"}, button="查询分页")
    @GetMapping("/list")
    public Object list(Integer oltId, String oltTableDesp,
                       String olfFieldDesp,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<SysTableFieldDesp> sysTableFieldDesps= sysTableFieldDespService.findByMainIdOrMainName(
                oltId,oltTableDesp,olfFieldDesp,sort);
        return ResponseUtil.okList(sysTableFieldDesps);
    }

    @GetMapping("/detail")
    public Object detail(Integer id) {
        SysTableFieldDesp sysTableFieldDesp= sysTableFieldDespService.findById(id);
        return ResponseUtil.ok(sysTableFieldDesp);
    }

    @GetMapping("/getTableFieldDesp")
    public Object getTableFieldDesp(Integer sysTableDespId, String fieldName) {
        Map<String, Object> result= sysTableFieldDespService.getTableFieldDesp(sysTableDespId,fieldName);
        return ResponseUtil.ok(result);
    }



}
