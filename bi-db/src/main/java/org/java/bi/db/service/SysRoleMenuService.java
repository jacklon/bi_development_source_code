package org.java.bi.db.service;

import com.github.pagehelper.util.StringUtil;

import org.java.bi.db.dao.SysBiMenuMapper;
import org.java.bi.db.dao.SysRoleMenuMapper;
import org.java.bi.db.dao.ViewRoleMenuMapper;
import org.java.bi.db.domain.*;
import org.java.bi.db.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper rolemenuMapper;

    @Resource
    private SysBiMenuMapper menuMapper;

    @Resource
    private ViewRoleMenuMapper viewRoleMenuMapper;

    @Autowired
    private CommonDBService commonDBService;


    public Set<Integer> queryByRoleId(Integer roleId) {
        Set<Integer> menus = new HashSet<Integer>();
        if(roleId == null){
            return menus;
        }
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<SysRoleMenu> menuList = rolemenuMapper.selectByExample(example);
        menus=menuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toSet());

//        for(SysRoleMenu roleMenu : menuList){
//            menus.add(roleMenu.getMenuId());
//        }
        return menus;
    }

    public Set<Integer> queryByRoleIdView(Integer roleId) {
        Set<Integer> menus = new HashSet<Integer>();
        if(roleId == null){
            return menus;
        }
        ViewRoleMenuExample example = new ViewRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false).andLevelEqualTo(3);

        List<ViewRoleMenu> menuList = viewRoleMenuMapper.selectByExample(example);
        menus=menuList.stream().map(ViewRoleMenu::getMenuId).distinct().collect(Collectors.toSet());

//        for(SysRoleMenu roleMenu : menuList){
//            menus.add(roleMenu.getMenuId());
//        }
        return menus;
    }


    public Set<String> queryCodeByRoleId(Integer roleId) {
        Set<String> menus = new HashSet<String>();
        if(roleId == null){
            return menus;
        }
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<SysRoleMenu> menuList = rolemenuMapper.selectByExample(example);
        menus=menuList.stream().map(SysRoleMenu::getMenuCode).distinct().collect(Collectors.toSet());

//        for(SysRoleMenu roleMenu : menuList){
//            menus.add(roleMenu.getMenuCode());
//        }
        return menus;
    }

    public Set<String> queryCodeByRoleId(List<Integer> roleIds) {
        Set<String> menus = new HashSet<String>();
        if(roleIds == null){
            return menus;
        }
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdIn(roleIds).andDeletedEqualTo(false);
        List<SysRoleMenu> menuList = rolemenuMapper.selectByExample(example);
        menus=menuList.stream().map(SysRoleMenu::getMenuCode).distinct().collect(Collectors.toSet());


        return menus;
    }


    public List<SysBiMenu> getLiemallMenuListByRoleId(Integer roleId) {
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<SysRoleMenu> roleMenus = rolemenuMapper.selectByExample(example);

        List<Integer> menuIds=roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        if(menuIds==null||menuIds.size()==0){
            return null;
        } else
        {
            SysBiMenuExample menuExample = new SysBiMenuExample();
            example.or().andIdIn(menuIds).andDeletedEqualTo(false);
            List<SysBiMenu> menuList = menuMapper.selectByExample(menuExample);
            return  menuList;
        }
    }

    public SysRoleMenu findById(Integer id) {
        return rolemenuMapper.selectByPrimaryKey(id);
    }

    public void add(SysRoleMenu roleMenu) {
        roleMenu.setAddTime(LocalDateTime.now());
        roleMenu.setUpdateTime(LocalDateTime.now());
        rolemenuMapper.insertSelective(roleMenu);
    }

    public void deleteById(Integer id) {
        rolemenuMapper.logicalDeleteByPrimaryKey(id);
    }


    public void deleteByRoleId(Integer roleId) {
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        rolemenuMapper.deleteByExample(example);
        //rolemenuMapper.logicalDeleteByExample(example);
    }

    public void updateById(SysRoleMenu roleMenu) {
        roleMenu.setUpdateTime(LocalDateTime.now());
        rolemenuMapper.updateByPrimaryKeySelective(roleMenu);
    }

    public boolean checkRoleMenus(Integer roleId) {
        if(roleId == null){
            return false;
        }
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        return rolemenuMapper.countByExample(example) != 0;
    }


    /**
     * 获取用户一级菜单权限
     *
     */
    public Set<String> getMenuRoleCollectionFirstLevel(List<Integer> roleIds) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        if(roleIds!=null&&roleIds.size()>0){
            criteria.andRoleIdIn(roleIds);
        }
        criteria.andDeletedEqualTo(false);
        criteria.andLevelEqualTo(1);
        List<ViewRoleMenu> result=viewRoleMenuMapper.selectByExample(example);
        Set<String> setResult=result.stream().map(ViewRoleMenu::getMenuCode).collect(Collectors.toSet());

        return setResult;
    }

    /**
     * 获取用户一级菜单权限
     *
     */
    public List<ViewRoleMenu> getMenuRoleFirstLevel(List<Integer> roleIds) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        if(roleIds!=null&&roleIds.size()>0){
            criteria.andRoleIdIn(roleIds);
        }
        criteria.andDeletedEqualTo(false);
        criteria.andLevelEqualTo(1);
        List<ViewRoleMenu> result=viewRoleMenuMapper.selectByExample(example);


        return result;
    }

    /**
     * 获取用户一级菜单权限
     *
     */

    public  Set<String> getMenuRoleSecondLevel(List<Integer> roleIds,List<String> roleNames,String parentCode) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        if(roleIds!=null&&roleIds.size()>0){
            criteria.andRoleIdIn(roleIds);
        }
        if(roleNames!=null&&roleNames.size()>0){
            criteria.andRoleNameIn(roleNames);
        }
        if(!StringUtil.isEmpty(parentCode)){
            criteria.andParentCodeEqualTo(parentCode);
        }
        criteria.andDeletedEqualTo(false);
        criteria.andLevelEqualTo(2);

        List<ViewRoleMenu> result=viewRoleMenuMapper.selectByExample(example);

        Set<String> setResult=result.stream().map(ViewRoleMenu::getMenuCode).collect(Collectors.toSet());

        return setResult;
    }

    public  List<ViewRoleMenu> getMenuRoleViewSecondLevel(List<Integer> roleIds,List<String> roleNames,String parentCode) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        if(roleIds!=null&&roleIds.size()>0){
            criteria.andRoleIdIn(roleIds);
        }
        if(roleNames!=null&&roleNames.size()>0){
            criteria.andRoleNameIn(roleNames);
        }
        if(!StringUtil.isEmpty(parentCode)){
            criteria.andParentCodeEqualTo(parentCode);
        }
        criteria.andDeletedEqualTo(false);
        criteria.andLevelEqualTo(2);

        List<ViewRoleMenu> result=viewRoleMenuMapper.selectByExample(example);

        return result;
    }

    public  List getMenuRoleViewBySql(List<Integer> roleIds,List<String> roleNames,String parentCode,Integer level,List<String> parentCodeList) {
        String condition="";
        if(roleIds!=null&&roleIds.size()>0){
            condition+=" and b.role_id in ("+ CommonUtil.listToInteger(roleIds)+")";
        }
        if(roleNames!=null&&roleNames.size()>0){
            condition+=" and a.name in ("+ CommonUtil.listStringToString(roleNames)+")";
        }
        if(!StringUtils.isEmpty(parentCode)){
            condition+=" and d.code='"+ parentCode+"'";
        }
        if(!StringUtils.isEmpty(level)){
            condition+=" and  c.level="+ level;
        }
        if(parentCodeList!=null&&parentCodeList.size()>0){
            condition+=" and b.menu_code in ("+ CommonUtil.listStringToString(parentCodeList)+")";
        }


        String strSql="SELECT distinct `b`.`menu_id` AS `menuId`,`b`.`menu_code` AS `menuCode`,"+
            "`c`.`level` AS `level`,`c`.`path` AS `path`,`c`.`component` AS `component`,`c`.`name` AS `name`,"+
	        "`c`.`icon` AS `icon`,`d`.`code` AS `parentCode`  FROM "+
            "`litemall_role` `a` JOIN `litemall_role_menu` `b` ON `a`.`id` = `b`.`role_id` "+
            "JOIN `litemall_menu` `c` ON  `b`.`menu_id` = `c`.`id` LEFT JOIN `litemall_menu` `d` ON  `c`.`parent_id` = `d`.`id` "+
            "where 1=1 "+condition+
            " ORDER BY `c`.`level`,`c`.`parent_id`,`c`.`sort` ";

        List list= commonDBService.procedureDaoList(strSql);

        return list;
    }

    public List<ViewRoleMenu> getMenuRoleViewThirdLevel(List<Integer> roleIds,List<String> roleNames,String parentCode) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        if(roleIds!=null&&roleIds.size()>0){
            criteria.andRoleIdIn(roleIds);
        }
        if(roleNames!=null&&roleNames.size()>0){
            criteria.andRoleNameIn(roleNames);
        }
        if(!StringUtil.isEmpty(parentCode)){
            criteria.andParentCodeEqualTo(parentCode);
        }
        criteria.andDeletedEqualTo(false);
        criteria.andLevelEqualTo(3);

        List<ViewRoleMenu> result=viewRoleMenuMapper.selectByExample(example);

        return result;
    }

    public Boolean checkMenuByRoleIdAndMenuId(Integer roleId,Integer menuId) {
        ViewRoleMenuExample example=new ViewRoleMenuExample();
        ViewRoleMenuExample.Criteria criteria=example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andMenuIdEqualTo(menuId);
        criteria.andDeletedEqualTo(false);
        return viewRoleMenuMapper.countByExample(example) != 0;
    }

}
