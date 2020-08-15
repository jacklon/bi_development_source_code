package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.bi.admin.dto.BDicMainAllinone;
import org.java.bi.admin.annotation.RequiresPermissionsDesc;
import org.java.bi.admin.service.AdminDicmainService;
import org.java.bi.db.domain.BDicMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/dicmain")
@Validated
@Api(description = "后台管理/代码字典/系统代码:/admin/dicmain")
public class BDicmainController {
    private final Log logger = LogFactory.getLog(BDicmainController.class);

    @Autowired
    private AdminDicmainService adminDicmainService;

    /**
     * 查询字典主表
     *
     * @param name
     * @param page
     * @param limit
     * @param sort

     * @return
     */
//    @RequiresPermissions("admin:dicmain:list")
//    @RequiresPermissionsDesc(menu = {"基础数据", "代码主表"}, button = "查询主表")
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        return adminDicmainService.list(name, page, limit, sort);
    }

    /**
     * 编辑字典主表
     *
     * @param BDicMainAllinone
     * @return
     */
    @RequiresPermissions("admin:dicmain:update")
    @RequiresPermissionsDesc(menu = {"代码字典", "系统代码"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody BDicMainAllinone BDicMainAllinone) {
        return adminDicmainService.update(BDicMainAllinone);
    }

    /**
     * 删除字典主表
     *
     * @param litemallDicMain
     * @return
     */
    @RequiresPermissions("admin:dicmain:delete")
    @RequiresPermissionsDesc(menu = {"代码字典", "系统代码"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody BDicMain litemallDicMain) {
        return adminDicmainService.delete(litemallDicMain);
    }

    /**
     * 添加字典表
     *
     * @param BDicMainAllinone
     * @return
     */
    @RequiresPermissions("admin:dicmain:create")
    @RequiresPermissionsDesc(menu = {"代码字典", "系统代码"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody BDicMainAllinone BDicMainAllinone) {
        return adminDicmainService.create(BDicMainAllinone);
    }

    /**
     * 字典详情
     *
     * @param id
     * @return
     */
//    @RequiresPermissions("admin:dicmain:read")
//    @RequiresPermissionsDesc(menu = {"代码字典", "系统代码"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminDicmainService.detail(id);

    }


    /**
     * 获取所有可用的字典表
     * @return
     */
//    @RequiresPermissions("admin:dicmain:queryall")
//    @RequiresPermissionsDesc(menu = {"代码字典", "系统代码"}, button = "获取全部")
    @GetMapping("/queryall")
    public Object queryAllDicList() {
        return adminDicmainService.queryAll();

    }

}
