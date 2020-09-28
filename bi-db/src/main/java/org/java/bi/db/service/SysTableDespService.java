package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysTableDespMapper;
import org.java.bi.db.dao.SysTableFieldDespMapper;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.domain.SysTableDespExample;
import org.java.bi.db.domain.SysTableFieldDesp;
import org.java.bi.db.domain.SysTableFieldDespExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysTableDespService {
    @Resource
    private SysTableDespMapper sysTableDespMapper;

    @Resource
    private SysTableFieldDespMapper sysTableFieldDespMapper;

    @Autowired
    private CommonDBService commonDBService;

    public List<SysTableDesp> queryAll(){
        SysTableDespExample example=new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();

        criteria.andDeletedEqualTo(false);
        return sysTableDespMapper.selectByExample(example);
    }

    public List<SysTableDesp> querySelective(String dbTableOrViewName,String chineseDesc,
        String fieldName,String fieldChineseDesp,Integer page,Integer limit,String sort){
        SysTableDespExample example=new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(dbTableOrViewName)){
            criteria.andDbTableOrViewNameLike("%"+dbTableOrViewName+"%");
        }
        if(!StringUtils.isEmpty(chineseDesc)){
            criteria.andChineseDescLike("%"+chineseDesc+"%");
        }
        if(!StringUtils.isEmpty(fieldName)||!StringUtils.isEmpty(fieldChineseDesp)){
            SysTableFieldDespExample fieldExample=new SysTableFieldDespExample();
            SysTableFieldDespExample.Criteria fieldCriteria=fieldExample.createCriteria();
            if(!StringUtils.isEmpty(fieldName)){
                fieldCriteria.andFieldNameEqualTo(fieldName);
            }
            if(!StringUtils.isEmpty(fieldChineseDesp)){
                fieldCriteria.andFieldChineseDespLike("%"+fieldChineseDesp+"%");
            }
            criteria.andDeletedEqualTo(false);
            List<SysTableFieldDesp> sysTableFieldDespList= sysTableFieldDespMapper.selectByExample(fieldExample);
            List<Integer> mainIdList=sysTableFieldDespList.stream().map(SysTableFieldDesp::getStdId).distinct().collect(Collectors.toList());
            if(mainIdList!=null&&mainIdList.size()>0){
                criteria.andIdIn(mainIdList);
            }else
            {
                return null;
            }
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return sysTableDespMapper.selectByExample(example);
    }


    public int updateById(SysTableDesp homeIcon){
        homeIcon.setUpdateTime(LocalDateTime.now());
        return sysTableDespMapper.updateByPrimaryKeySelective(homeIcon);
    }

    public void deleteById(Integer id){
        sysTableDespMapper.deleteByPrimaryKey(id);
//        sysTableDespMapper.logicalDeleteByPrimaryKey(id);
    }


    public void add(SysTableDesp homeIcon){
        homeIcon.setAddTime(LocalDateTime.now());
        homeIcon.setUpdateTime(LocalDateTime.now());
        sysTableDespMapper.insertSelective(homeIcon);
    }


    public SysTableDesp findById(Integer id){
        return sysTableDespMapper.selectByPrimaryKey(id);
    }

    public SysTableDesp findByIdOrDesp(Integer id,String desp) {
        SysTableDespExample example = new SysTableDespExample();
        SysTableDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andIdEqualTo(id);
        } else
        {
            if(!StringUtils.isEmpty(desp)){
                criteria.andChineseDescEqualTo(desp);
            }
        }
        criteria.andDeletedEqualTo(false);

        return sysTableDespMapper.selectOneByExample(example);
    }


    public boolean checkExistByName(String name) {
        SysTableDespExample example = new SysTableDespExample();
        example.or().andChineseDescEqualTo(name).andDeletedEqualTo(false);
        return sysTableDespMapper.countByExample(example) != 0;
    }
}
