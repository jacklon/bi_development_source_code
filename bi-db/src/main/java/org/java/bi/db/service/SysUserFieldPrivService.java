package org.java.bi.db.service;


import org.java.bi.db.dao.SysUserFieldPrivMapper;
import org.java.bi.db.domain.SysUserFieldPriv;
import org.java.bi.db.domain.SysUserFieldPrivExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysUserFieldPrivService {

    @Resource
    private SysUserFieldPrivMapper privMapper;

    public List<SysUserFieldPriv> querySelective(Integer userId, Integer sysTableDespId) {
        SysUserFieldPrivExample example = new SysUserFieldPrivExample();
        SysUserFieldPrivExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(sysTableDespId)) {
            criteria.andSysTableDespIdEqualTo(sysTableDespId);
        }
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");

        return privMapper.selectByExampleSelective(example);
    }

    public int updateById(SysUserFieldPriv adminFieldPriv) {
        adminFieldPriv.setUpdateTime(LocalDateTime.now());
        return privMapper.updateByPrimaryKeySelective(adminFieldPriv);
    }

    public void deleteById(Integer id) {
        privMapper.deleteByPrimaryKey(id);
    }

    public void add(SysUserFieldPriv adminFieldPriv) {
        adminFieldPriv.setAddTime(LocalDateTime.now());
        adminFieldPriv.setUpdateTime(LocalDateTime.now());
        privMapper.insertSelective(adminFieldPriv);
    }

    public SysUserFieldPriv findById(Integer id) {
        return privMapper.selectByPrimaryKeySelective(id);
    }



    public Boolean checkIfExists(SysUserFieldPriv adminFieldPriv) {
        SysUserFieldPrivExample example = new SysUserFieldPrivExample();
        SysUserFieldPrivExample.Criteria criteria = example.createCriteria();
        Integer sysTableDespId=adminFieldPriv.getSysTableDespId();
        String fieldName=adminFieldPriv.getFieldName();
        if (!StringUtils.isEmpty(sysTableDespId)) {
            criteria.andSysTableDespIdEqualTo(sysTableDespId);
        }
        if (!StringUtils.isEmpty(fieldName)) {
            criteria.andFieldNameEqualTo(fieldName);
        }
        if(adminFieldPriv.getId()!=null){
            criteria.andIdNotEqualTo(adminFieldPriv.getId());
        }
        criteria.andDeletedEqualTo(false);
        return privMapper.countByExample(example)!=0;
    }

}
