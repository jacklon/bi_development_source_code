package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysOperationLogMapper;
import org.java.bi.db.domain.SysOperationLog;
import org.java.bi.db.domain.SysOperationLogExample;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SysOperationLogService {

    @Resource
    private SysOperationLogMapper sysOperationLogMapper;

    @Autowired
    private SysTableDespService sysTableDespService;


    public SysOperationLog selectByPrimaryKey(Integer id) {
        return sysOperationLogMapper.selectByPrimaryKey(id);
    }

    public List<SysOperationLog> querySelective(String flowInstanceId, String logRemark,
        String addBy,String startOpTime,String endOpTime,Integer page, Integer limit) {
        SysOperationLogExample example = new SysOperationLogExample();
        SysOperationLogExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(flowInstanceId)) {
            criteria.andFlowInstanceIdEqualTo(flowInstanceId);
        }
        if (!StringUtils.isEmpty(logRemark)) {
            criteria.andLogRemarkLike("%"+logRemark+"%");
        }
        if (!StringUtils.isEmpty(addBy)) {
            criteria.andAddByLike("%"+addBy+"%");
        }
        //将日期转为时间格式
        if (!StringUtils.isEmpty(startOpTime)) {
            LocalDateTime startDateTime= DateUtil.getLocalDateTimeFromString(startOpTime);
            criteria.andAddTimeGreaterThanOrEqualTo(startDateTime);
        }
        //将日期转为时间格式
        if (!StringUtils.isEmpty(endOpTime)) {
            LocalDateTime endDateTime= DateUtil.getLocalDateTimeFromString(endOpTime);
            criteria.andAddTimeLessThanOrEqualTo(endDateTime);
        }

        criteria.andDeletedEqualTo(false);
        //按填加时间倒序
        example.setOrderByClause(" add_time desc");

        PageHelper.startPage(page, limit);
        return sysOperationLogMapper.selectByExampleSelective(example);
    }

    public int updateById(SysOperationLog sysOperationLog) {
        sysOperationLog.setUpdateTime(LocalDateTime.now());
        return sysOperationLogMapper.updateByPrimaryKeySelective(sysOperationLog);
    }

    public void deleteById(Integer id) {
        sysOperationLogMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SysOperationLog sysOperationLog) {
        sysOperationLog.setAddTime(LocalDateTime.now());
        sysOperationLog.setUpdateTime(LocalDateTime.now());
        sysOperationLogMapper.insertSelective(sysOperationLog);
    }

    public SysOperationLog findById(Integer id) {
        return sysOperationLogMapper.selectByPrimaryKeySelective(id);
    }

    public List<SysOperationLog> findByFlowStartId(Integer flowStartId) {
        SysOperationLogExample example = new SysOperationLogExample();
        SysOperationLogExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(flowStartId)) {
            criteria.andFlowStartIdEqualTo(flowStartId);
        }
        return sysOperationLogMapper.selectByExampleSelective(example);
    }


}
