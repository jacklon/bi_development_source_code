package org.java.bi.db.service;

import org.java.bi.db.dao.SysBiRoleMapper;
import org.java.bi.db.domain.SysBiRole;
import org.java.bi.db.domain.SysBiRoleExample;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysBiRoleService {
    @Resource
    private SysBiRoleMapper roleMapper;


    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        SysBiRoleExample example = new SysBiRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<SysBiRole> roleList = roleMapper.selectByExample(example);

        for(SysBiRole role : roleList){
            roles.add(role.getName());
        }

        return roles;

    }

    public List<SysBiRole> querySelective(String name, Integer page, Integer limit, String sort) {
        SysBiRoleExample example = new SysBiRoleExample();
        SysBiRoleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause(sort);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(example);
    }

    public SysBiRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void add(SysBiRole role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insertSelective(role);
    }

    public void deleteById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    public void updateById(SysBiRole role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public boolean checkExist(String name) {
        SysBiRoleExample example = new SysBiRoleExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return roleMapper.countByExample(example) != 0;
    }

    public List<SysBiRole> queryAll() {
        SysBiRoleExample example = new SysBiRoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }

    public Boolean judgeIfInclueAdminRole(List<Integer> listRoleIds){

        SysBiRoleExample example = new SysBiRoleExample();
        example.or().andIdIn(listRoleIds).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<SysBiRole> roleList = roleMapper.selectByExample(example);

        for(SysBiRole role : roleList){
            if(role.getName().equals("超级管理员")){
                return true;
            }
        }
        return false;
    }

}
