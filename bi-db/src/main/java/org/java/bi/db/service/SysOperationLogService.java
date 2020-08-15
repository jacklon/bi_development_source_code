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

    /**
     * 通过参数方式增加数据操作日志
     * @param logTypeName 日志类型
     * @param tableId 系统维护表
     * @param dataId  数据主键
     * @param logRemark 操作日志
     * @param parentTableId 当前操作表的父表ID
     * @param parentDataId 父表的数据主键
     * @param flowInstanceId 流程实例id
     * @param addBy 填加人
     */
    public void add(String logTypeName,Integer tableId,Integer dataId,String logRemark,String nodeRemark,
                    Integer parentTableId,Integer parentDataId,String flowInstanceId,
                    String taskId,String addBy,Integer flowStartId) {
        List<SysTableDesp>  sysTableDespList= sysTableDespService.queryAll();

        SysOperationLog addNew=new SysOperationLog();
        addNew.setLogTypeName(logTypeName);
        addNew.setTableId(tableId);
        Optional<SysTableDesp> findTable = sysTableDespList.stream().filter(item -> item.getId().equals(tableId)).findFirst();
        if(findTable.isPresent()){
            addNew.setTableName(findTable.get().getOltDbTalbeViewName());
            addNew.setTableDesc(findTable.get().getOltTableDesp());
        }
        addNew.setDataId(dataId);
        addNew.setLogRemark(logRemark);
        addNew.setNodeRemark(nodeRemark);
        addNew.setParentTableId(parentTableId);
        if(!StringUtils.isEmpty(parentTableId)){
            Optional<SysTableDesp> findParentTable = sysTableDespList.stream().filter(item -> item.getId().equals(parentTableId)).findFirst();
            if(findTable.isPresent()){
                addNew.setParentTableName(findParentTable.get().getOltDbTalbeViewName());
                addNew.setParentTableDesc(findParentTable.get().getOltTableDesp());
            }
        }
        addNew.setParentDataId(parentDataId);
        if(!StringUtils.isEmpty(flowStartId)) {
            addNew.setFlowStartId(flowStartId);
        }
        if(!StringUtils.isEmpty(flowInstanceId)) {
            addNew.setFlowInstanceId(flowInstanceId);
        }
        if(!StringUtils.isEmpty(taskId)) {
            addNew.setTaskId(taskId);
        }
        addNew.setAddBy(addBy);
        addNew.setUpdateBy(addBy);
        addNew.setAddTime(LocalDateTime.now());
        addNew.setUpdateTime(LocalDateTime.now());
        sysOperationLogMapper.insertSelective(addNew);
    }

}
