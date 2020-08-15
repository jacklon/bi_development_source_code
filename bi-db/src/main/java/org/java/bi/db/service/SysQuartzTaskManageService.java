package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysQuartzTaskManageMapper;
import org.java.bi.db.domain.SysQuartzTaskManage;
import org.java.bi.db.domain.SysQuartzTaskManageExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysQuartzTaskManageService {
    @Resource
    private SysQuartzTaskManageMapper sysQuartzTaskManageMapper;

    public List<SysQuartzTaskManage> querySelective(
       String jobName,String jobGroup,String jobStatus,String className,
       String parameters,String methodName,String description,
       Integer page,Integer limit,String sort){
        SysQuartzTaskManageExample example=new SysQuartzTaskManageExample();
        SysQuartzTaskManageExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(jobName)){
            criteria.andJobNameLike("%"+jobName+"%");
        }
        if(!StringUtils.isEmpty(jobGroup)){
            criteria.andJobGroupLike("%"+jobGroup+"%");
        }
        if(!StringUtils.isEmpty(jobStatus)){
            criteria.andJobStatusEqualTo(jobStatus);
        }
        if(!StringUtils.isEmpty(className)){
            criteria.andClassNameLike("%"+className+"%");
        }
        if(!StringUtils.isEmpty(parameters)){
            criteria.andParametersLike("%"+parameters+"%");
        }
        if(!StringUtils.isEmpty(methodName)){
            criteria.andMethodNameLike("%"+methodName+"%");
        }
        if(!StringUtils.isEmpty(description)){
            criteria.andDescriptionLike("%"+description+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        } else
        {
            example.setOrderByClause("add_time desc");
        }
        PageHelper.startPage(page,limit);
        return sysQuartzTaskManageMapper.selectByExample(example);
    }


    public int updateById(SysQuartzTaskManage sysQuartzTaskManage){
        sysQuartzTaskManage.setUpdateTime(LocalDateTime.now());
        return sysQuartzTaskManageMapper.updateByPrimaryKeySelective(sysQuartzTaskManage);
    }

    public void deleteById(Integer id){
        sysQuartzTaskManageMapper.deleteByPrimaryKey(id);
    }



    public void add(SysQuartzTaskManage sysQuartzTaskManage){
        sysQuartzTaskManage.setAddTime(LocalDateTime.now());
        sysQuartzTaskManage.setUpdateTime(LocalDateTime.now());
        sysQuartzTaskManageMapper.insertSelective(sysQuartzTaskManage);
    }

    public SysQuartzTaskManage findById(Integer id){
        return sysQuartzTaskManageMapper.selectByPrimaryKey(id);
    }

    public boolean checkExist(SysQuartzTaskManage sysQuartzTaskManage) {
        SysQuartzTaskManageExample example = new SysQuartzTaskManageExample();
        SysQuartzTaskManageExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(sysQuartzTaskManage.getJobName())){
            criteria.andJobNameEqualTo(sysQuartzTaskManage.getJobName());
        }
        if(!StringUtils.isEmpty(sysQuartzTaskManage.getId())){
            criteria.andIdNotEqualTo(sysQuartzTaskManage.getId());
        }

        criteria.andDeletedEqualTo(false);
        return sysQuartzTaskManageMapper.countByExample(example) != 0;
    }



}
