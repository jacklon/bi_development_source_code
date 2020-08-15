package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysInterfaceMonitorMapper;
import org.java.bi.db.domain.SysInterfaceMonitor;
import org.java.bi.db.domain.SysInterfaceMonitorExample;
import org.java.bi.db.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysInterfaceMonitorService {
    @Resource
    private SysInterfaceMonitorMapper litemallInterfaceMonitorMapper;

    public List<SysInterfaceMonitor> querySelective(
            String logSourceCode,String logDirectionCode,String logTypeCode,String logContent,
            Integer errorFlag,String startDate,String endDate,
            Integer page,Integer limit,String sort){
        SysInterfaceMonitorExample example=new SysInterfaceMonitorExample();
        SysInterfaceMonitorExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(logSourceCode)){
            criteria.andLogSourceCodeEqualTo(logSourceCode);
        }
        if(!StringUtils.isEmpty(logDirectionCode)){
            criteria.andLogDirectionCodeEqualTo(logDirectionCode);
        }
        if(!StringUtils.isEmpty(logTypeCode)){
            criteria.andLogTypeCodeEqualTo(logTypeCode);
        }
        if(!StringUtils.isEmpty(logContent)){
            criteria.andLogContentLike("%"+logContent+"%");
        }
        if(!StringUtils.isEmpty(errorFlag)){
            if(errorFlag==0) {
                criteria.andErrorFlagEqualTo(false);
            } else  if(errorFlag==1) {
                criteria.andErrorFlagEqualTo(true);
            }
        }
        if(!StringUtils.isEmpty(startDate)){
            criteria.andAddTimeGreaterThanOrEqualTo(DateUtil.getLocalDateTimeFromStringHM(startDate));
        }
        if(!StringUtils.isEmpty(endDate)){
            criteria.andAddTimeLessThanOrEqualTo(DateUtil.getLocalDateTimeFromStringHM(endDate));
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return litemallInterfaceMonitorMapper.selectByExample(example);
    }

    public void add(SysInterfaceMonitor userinfoDef){
        userinfoDef.setAddTime(LocalDateTime.now());
        userinfoDef.setUpdateTime(LocalDateTime.now());
        litemallInterfaceMonitorMapper.insertSelective(userinfoDef);
    }

    public int updateById(SysInterfaceMonitor userinfoDef){
        userinfoDef.setUpdateTime(LocalDateTime.now());
        return litemallInterfaceMonitorMapper.updateByPrimaryKeySelective(userinfoDef);
    }
    public void deleteById(Integer id){
        litemallInterfaceMonitorMapper.logicalDeleteByPrimaryKey(id);
    }


    public SysInterfaceMonitor findById(Integer id){
        return litemallInterfaceMonitorMapper.selectByPrimaryKey(id);
    }

}
