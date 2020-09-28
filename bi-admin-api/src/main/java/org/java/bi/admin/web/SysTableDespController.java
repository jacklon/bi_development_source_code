//package org.java.bi.admin.web;
//
//import io.swagger.annotations.Api;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.java.bi.admin.dto.TableDespAllinone;
//import org.java.bi.admin.annotation.RequiresPermissionsDesc;
////import org.java.bi.admin.service.AdminTableDespService;
//import org.java.bi.core.util.ResponseUtil;
//
//import org.java.bi.db.domain.SysTableDesp;
//import org.java.bi.db.domain.SysTableFieldDesp;
//import org.java.bi.db.service.SysTableFieldDespService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin/tableDesp")
//@Validated
//@Api(description = "后台管理/基础数据/表描述管理:/admin/tableDesp")
//public class SysTableDespController {
//    private final Log logger = LogFactory.getLog(SysTableDespController.class);
//
//    @Autowired
//    private AdminTableDespService adminTableDespService;
//
//    @Autowired
//    private SysTableFieldDespService sysTableFieldDespService;
//
//
//
//    /**
//     * 查询维护表主表 需要关联可编辑列表
//     *
//     * @return
//     */
////    @RequiresPermissions("admin:tableDesp:list")
////    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="查询分页")
//    @GetMapping("/list")
//    public Object list(String dbTableOrViewName,String chineseDesc,
//                       String fieldName,String fieldChineseDesp,
//                       @RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer limit,
//                       @RequestParam(defaultValue = "update_time desc") String sort
//                       ) {
//        return adminTableDespService.list(dbTableOrViewName,chineseDesc,fieldName,fieldChineseDesp, page, limit, sort);
//    }
//
//
//    /**
//     * 获取所有的表描述列表
//     * @return
//     */
////    @RequiresPermissions("admin:tableDesp:queryall")
////    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="查询全部")
//    @GetMapping("/queryall")
//    public Object queryAll() {
//        return adminTableDespService.queryAll();
//    }
//
//    /**
//     * 编辑维护表主表
//     *
//     * @param tableDespAllinone
//     * @return
//     */
//    @RequiresPermissions("admin:tableDesp:update")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="编辑")
//    @PostMapping("/update")
//    public Object update(@RequestBody TableDespAllinone tableDespAllinone) {
//        return adminTableDespService.update(tableDespAllinone);
//    }
//
//    /**
//     * 删除维护表主表
//     *
//     * @param sysTableDesp
//     * @return
//     */
//    @RequiresPermissions("admin:tableDesp:delete")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="删除")
//    @PostMapping("/delete")
//    public Object delete(@RequestBody SysTableDesp sysTableDesp) {
//        return adminTableDespService.delete(sysTableDesp);
//    }
//
//    /**
//     * 添加维护表
//     *
//     * @param tableDespAllinone
//     * @return
//     */
//    @RequiresPermissions("admin:tableDesp:create")
//    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="添加")
//    @PostMapping("/create")
//    public Object create(@RequestBody TableDespAllinone tableDespAllinone) {
//        return adminTableDespService.create(tableDespAllinone);
//    }
//
//    /**
//     * 读取表整个结构详情
//     *
//     * @param id
//     * @return
//     */
////    @RequiresPermissions("admin:tableDesp:read")
////    @RequiresPermissionsDesc(menu={"代码字典" , "库表绑定"}, button="详情")
//    @GetMapping("/read")
//    public Object read(@NotNull Integer id) {
//        return adminTableDespService.getTableDespAndFields(id);
//
//    }
//
//    /**
//     * 读取表本身详情
//     * @param id
//     * @return
//     */
//    @GetMapping("/detail")
//    public Object detail(@NotNull Integer id) {
//        return adminTableDespService.getTableDespById(id);
//
//    }
//
//    /**
//     * 查询所有表信息,包括所有表
//     * @return
//     */
//    @GetMapping("/tablelistAll")
//    public Object getTableListAll() {
//        List sysTalbeDesps= adminTableDespService.getAllTable();
//        return ResponseUtil.okList(sysTalbeDesps);
//    }
//
//    /**
//     * 查询某个表或视图的字段信息,如果已经存在，则更新，不删除
//     * @return
//     */
//
//    @GetMapping("/tablefieldlist")
//    public Object getTableFieldList(@NotNull String tableOrViewName) {
//        List viewTableFieldDefList= adminTableDespService.getAllTableFields(tableOrViewName);
//        return ResponseUtil.okList(viewTableFieldDefList);
//    }
//
//    @PostMapping("/updateTableField")
//    public Object updateTableField(@RequestBody SysTableFieldDesp sysTableFieldDesp) {
//        return sysTableFieldDespService.updateById(sysTableFieldDesp);
//    }
//
//}
