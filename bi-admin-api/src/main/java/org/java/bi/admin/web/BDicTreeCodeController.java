package org.java.bi.admin.web;

import io.swagger.annotations.Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.bi.admin.util.AdminResponseCode;
import org.java.bi.admin.util.GetCurrentUser;
import org.java.bi.admin.vo.MenuClassVo;
import org.java.bi.core.util.ResponseUtil;

import org.java.bi.db.domain.BDicTreeCode;
import org.java.bi.db.service.BDicTreeCodeService;
import org.java.bi.db.service.CommonDBService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/treecode")
@Validated
@Api(description = "基础数据:/admin/treecode")
public class BDicTreeCodeController {
    private final Log logger = LogFactory.getLog(BDicTreeCodeController.class);

    @Autowired
    private BDicTreeCodeService bDicTreeCodeService;

    @Autowired
    private CommonDBService commonDBService;

//    @RequiresPermissions("admin:treecode:list")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码查询")
    @GetMapping("/list")
    public Object list(Integer mainId,String mainName,Integer parentId, Integer id, String name,Boolean ifAnalyze) {
        return ResponseUtil.okList(getChildrenList(mainId,mainName,parentId,id,name,ifAnalyze));
    }

    private MenuClassVo recursiveTree(MenuClassVo parent, List<MenuClassVo> list) {
        for (MenuClassVo menuClassVo : list) {
            if(parent.getId().equals(menuClassVo.getParentId()) ) {
                menuClassVo = recursiveTree(menuClassVo, list);
                if(parent.getChildren()==null){
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(menuClassVo);
            }
        }
        return parent;
    }


//    @RequiresPermissions("admin:treecode:read")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        BDicTreeCode treecode = bDicTreeCodeService.findById(id);
        return ResponseUtil.ok(treecode);
    }

    @GetMapping("/readByMenuPath")
    public Object read(@NotNull String menuPath) {
        BDicTreeCode treecode = bDicTreeCodeService.findByMenuPath(menuPath);
        return ResponseUtil.ok(treecode);
    }




    private Object validate(BDicTreeCode dicTreeCode) {
        String code = dicTreeCode.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.fail(502,"树形代码编码不能为空");
        }
        String name = dicTreeCode.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.fail(502,"树形代码名称不能为空");
        }

        Boolean ifExists= bDicTreeCodeService.checkExistByKeyAndName(dicTreeCode.getMainId(),dicTreeCode.getId(),dicTreeCode.getName());
        if(ifExists){
            return ResponseUtil.fail(502,"树形代码编码已经存在");
        }

        return null;
    }

//    @RequiresPermissions("admin:treecode:create")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码添加")
    @PostMapping("/create")
    public Object create(@RequestBody BDicTreeCode dicTreeCode) {
        Object error = validate(dicTreeCode);
        if (error != null) {
            return error;
        }

        if (bDicTreeCodeService.checkExistByKeyAndName(dicTreeCode.getMainId(),dicTreeCode.getId(),dicTreeCode.getName())){
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "树形代码编码已经存在");
        }
        if(dicTreeCode.getParentId()==null||dicTreeCode.getParentId()==0){
            dicTreeCode.setLevel(1);
        } else
        {
            //父级层级加1
            BDicTreeCode parentCode=bDicTreeCodeService.findById(dicTreeCode.getParentId());
            if(parentCode!=null){
                dicTreeCode.setLevel(parentCode.getLevel()+1);
            }

        }
        dicTreeCode.setAddBy(GetCurrentUser.getCurrentUserName());
        dicTreeCode.setUpdateBy(GetCurrentUser.getCurrentUserName());
        bDicTreeCodeService.add(dicTreeCode);

        return ResponseUtil.ok(dicTreeCode);
    }

//    @RequiresPermissions("admin:treecode:update")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码编辑")
    @PostMapping("/update")
    public Object update(@RequestBody BDicTreeCode dicTreeCode) {
        Object error = validate(dicTreeCode);
        if (error != null) {
            return error;
        }
        //查找数据库中的typeName，如果发生了变化，则需要更新相关的表中的数据
        BDicTreeCode sourceDicTree=bDicTreeCodeService.findById(dicTreeCode.getId());


        if(dicTreeCode.getParentId()==null||dicTreeCode.getParentId()==0){
            dicTreeCode.setLevel(1);
        } else
        {
            //父级层级加1
            BDicTreeCode parentCode=bDicTreeCodeService.findById(dicTreeCode.getParentId());
            if(parentCode!=null){
                dicTreeCode.setLevel(parentCode.getLevel()+1);
            }

        }
        dicTreeCode.setUpdateBy(GetCurrentUser.getCurrentUserName());
        bDicTreeCodeService.updateById(dicTreeCode);


        return ResponseUtil.ok();
    }

//    @RequiresPermissions("admin:treecode:delete")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody BDicTreeCode dicTreeCode) {
        Integer id = dicTreeCode.getId();
        if (id == null) {
            return ResponseUtil.fail(502,"是删除的树形代码项不能为空");
        }

        bDicTreeCodeService.deleteById(id);
        return ResponseUtil.ok();
    }
//    @RequiresPermissions("admin:treecode:deletelist")
//    @RequiresPermissionsDesc(menu={"基础数据" , "树形代码详情"}, button="树形代码删除列表")
    @PostMapping("/deletelist")
    public Object delete(@RequestBody List<Integer> menuIds) {

        if (menuIds== null||menuIds.size()==0) {
            return ResponseUtil.fail(502,"是删除的树形代码项不能为空");
        }

        bDicTreeCodeService.deleteByIdList(menuIds);
        return ResponseUtil.ok();
    }


    public  List<MenuClassVo> getChildrenList(Integer mainId,String mainName,Integer parentId,Integer id, String name,Boolean ifAnalyze)
    {
        List<MenuClassVo> menuClassVoList = new ArrayList<>();
        List<BDicTreeCode> articleClassList=bDicTreeCodeService.querySelective(mainId,mainName,parentId,id,name,ifAnalyze);
        List<MenuClassVo> result = new ArrayList<>();
        for(BDicTreeCode litemallMenu:articleClassList){
            MenuClassVo temp=new MenuClassVo();
            BeanUtils.copyProperties(litemallMenu,temp);
            menuClassVoList.add(temp);
        }
        // 1、获取第一级节点
        for (MenuClassVo menuClass : menuClassVoList) {
            if(0 == menuClass.getParentId()) {
                result.add(menuClass);
            }
        }
        // 2、递归获取子节点
        for (MenuClassVo parent : result) {
            parent = recursiveTree(parent, menuClassVoList);
        }
        return result;
    }


}
