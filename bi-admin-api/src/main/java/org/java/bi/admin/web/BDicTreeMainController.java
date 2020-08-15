package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.bi.admin.annotation.RequiresPermissionsDesc;
import org.java.bi.admin.util.AdminResponseCode;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.BDicTreeMain;
import org.java.bi.db.service.BDicTreeCodeService;
import org.java.bi.db.service.BDicTreeMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/treemain")
@Validated
@Api(description = "基础数据/树形代码:/admin/treemain")
public class BDicTreeMainController {
    private final Log logger = LogFactory.getLog(BDicTreeMainController.class);

    @Autowired
    private BDicTreeMainService dicTreeMainService;

    @Autowired
    private BDicTreeCodeService dicTreeCodeService;

    @Autowired
    private BDicTreeCodeController dicTreeCodeController;


//    @RequiresPermissions("admin:treemain:list")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码"}, button="树形代码主表查询")
    @GetMapping("/list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @RequestParam(defaultValue = "update_time desc") String sort
                       ) {
        List<BDicTreeMain> roleList = dicTreeMainService.querySelective(name, page, limit, sort);
        return ResponseUtil.okList(roleList);
    }

//    @RequiresPermissions("admin:treemain:detail")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码"}, button="树形代码子表节点")
//    @GetMapping("/detail")
//    public Object getChildren(@NotNull Integer mainId) {
//        Object dicTreeMain = dicTreeCodeController.list(mainId,null,null,null,null);
//        return dicTreeMain;
//    }

//    @RequiresPermissions("admin:treemain:read")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码"}, button="树形代码主表详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        BDicTreeMain dicTreeMain = dicTreeMainService.findById(id);
        return ResponseUtil.ok(dicTreeMain);
    }


    @RequiresPermissions("admin:treemain:create")
    @RequiresPermissionsDesc(menu={"代码字典" , "树形代码"}, button="树形代码主表添加")
    @PostMapping("/create")
    public Object create(@RequestBody BDicTreeMain dicTreeMain) {
        if (dicTreeMainService.checkExistByKeyAndName(dicTreeMain.getId(),dicTreeMain.getName())) {
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "树形代码主表名称已经存在");
        }
        dicTreeMain.setAddBy(GetCurrentUser.getCurrentUserName());
        dicTreeMain.setUpdateBy(GetCurrentUser.getCurrentUserName());
        dicTreeMainService.add(dicTreeMain);

        return ResponseUtil.ok(dicTreeMain);
    }

    @RequiresPermissions("admin:treemain:update")
    @RequiresPermissionsDesc(menu={"代码字典" , "树形代码"}, button="树形代码主表编辑")
    @PostMapping("/update")
    public Object update(@RequestBody BDicTreeMain dicTreeMain) {
        if (dicTreeMainService.checkExistByKeyAndName(dicTreeMain.getId(),dicTreeMain.getName())) {
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "树形代码主表名称已经存在");
        }
        dicTreeMain.setUpdateBy(GetCurrentUser.getCurrentUserName());
        dicTreeMainService.updateById(dicTreeMain);

        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:treemain:delete")
    @RequiresPermissionsDesc(menu={"代码字典" , "树形代码"}, button="树形代码主表删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody BDicTreeMain dicTreeMain) {
        Integer id = dicTreeMain.getId();
        if (id == null) {
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "树形代码主表主键不能为空");
        }
        //先删除树形的子表
        dicTreeCodeService.deleteByMainId(id);
        //再删除当前树形表的Id
        dicTreeMainService.deleteById(id);
        return ResponseUtil.ok();
    }


}
