package org.java.bi.db.service;

import org.java.bi.db.dao.SysBiMenuMapper;
import org.java.bi.db.domain.SysBiMenu;
import org.java.bi.db.domain.SysBiMenuExample;
import org.springframework.util.StringUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysBiMenuService {
    @Resource
    private SysBiMenuMapper menuMapper;


    public Set<String> queryByIds(Integer[] menuIds) {
        Set<String> menus = new HashSet<String>();
        if(menuIds.length == 0){
            return menus;
        }
        SysBiMenuExample example = new SysBiMenuExample();
        example.or().andIdIn(Arrays.asList(menuIds)).andDeletedEqualTo(false);
        List<SysBiMenu> menuList = menuMapper.selectByExample(example);
        for(SysBiMenu menu: menuList){
            menus.add(menu.getName());
        }
        return menus;

    }

    public List<SysBiMenu> querySelective(Integer id, String name) {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();

        if (!org.springframework.util.StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!org.springframework.util.StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("level,sort,id");

        return menuMapper.selectByExample(example);

    }

    public List<SysBiMenu> querySelective(List<Integer> idList) {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();

        criteria.andIdIn(idList);
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("level,sort,id");

        return menuMapper.selectByExample(example);

    }


    public List<SysBiMenu> queryFirstLevelMenu() {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();

        criteria.andParentIdEqualTo(0);
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("level,sort,id");

        return menuMapper.selectByExample(example);

    }

    public SysBiMenu findById(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public SysBiMenu getMenuByPath(String path) {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();

        criteria.andPathEqualTo(path);
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("level,sort,id");

        return menuMapper.selectOneByExample(example);

    }

    public void add(SysBiMenu role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        menuMapper.insertSelective(role);
    }

    public void deleteById(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
//        menuMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByIdList(List<Integer> idList) {
        SysBiMenuExample example = new SysBiMenuExample();
        example.or().andIdIn(idList);
        menuMapper.deleteByExample(example);
//        menuMapper.logicalDeleteByExample(example);
    }

    public void updateById(SysBiMenu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    public boolean checkExist(String code) {
        SysBiMenuExample example = new SysBiMenuExample();
        example.or().andCodeEqualTo(code).andDeletedEqualTo(false);
        return menuMapper.countByExample(example) != 0;
    }
    /**
     * 查询某菜单编码是否已经存在 相同的父菜单，并且菜单名称相同，即视为已经存在的菜单
     */
    public boolean checkExist(SysBiMenu litemallMenu) {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(litemallMenu.getName())){
                criteria.andNameEqualTo(litemallMenu.getName());
        }
        if(!StringUtils.isEmpty(litemallMenu.getCode())){
            criteria.andCodeEqualTo(litemallMenu.getCode());
        }
        if(!StringUtils.isEmpty(litemallMenu.getParentId())){
            criteria.andParentIdEqualTo(litemallMenu.getParentId());
        }
        Integer id=litemallMenu.getId();
        if(id!=null&&!StringUtils.isEmpty(id.toString())){
            criteria.andIdNotEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        return menuMapper.countByExample(example) != 0;
    }

    public SysBiMenu getHaveExistsMenu(SysBiMenu litemallMenu) {
        SysBiMenuExample example = new SysBiMenuExample();
        SysBiMenuExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(litemallMenu.getName())){
            criteria.andNameEqualTo(litemallMenu.getName());
        }
        if(!StringUtils.isEmpty(litemallMenu.getCode())){
            criteria.andCodeEqualTo(litemallMenu.getCode());
        }
        if(!StringUtils.isEmpty(litemallMenu.getParentId())){
            criteria.andParentIdEqualTo(litemallMenu.getParentId());
        }
        Integer id=litemallMenu.getId();
        if(id!=null&&!StringUtils.isEmpty(id.toString())){
            criteria.andIdNotEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        return menuMapper.selectOneByExample(example);
    }


    public List<SysBiMenu> queryAll() {
        SysBiMenuExample example = new SysBiMenuExample();
        example.or().andDeletedEqualTo(false);
        return menuMapper.selectByExample(example);
    }

}
