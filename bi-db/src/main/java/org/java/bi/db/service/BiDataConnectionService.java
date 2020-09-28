package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.BiDataConnectionMapper;
import org.java.bi.db.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BiDataConnectionService {
    @Resource
    private BiDataConnectionMapper dataConnectionMapper;


    @Autowired
    private CommonDBService commonDBService;


    public List<BiDataConnection> querySelective(String datasourceName, Integer page,Integer limit,String sort){
        BiDataConnectionExample example=new BiDataConnectionExample();
        BiDataConnectionExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(datasourceName)){
            criteria.andDatasourceNameLike("%"+datasourceName+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return dataConnectionMapper.selectByExample(example);
    }

    public List<BiDataConnection> queryAll(){
        BiDataConnectionExample example=new BiDataConnectionExample();
        BiDataConnectionExample.Criteria criteria=example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return dataConnectionMapper.selectByExample(example);
    }
    public BiDataConnection findById(Integer id) {
        return dataConnectionMapper.selectByPrimaryKey(id);
    }

    public void add(BiDataConnection biDataConnection) {
        biDataConnection.setAddTime(LocalDateTime.now());
        biDataConnection.setUpdateTime(LocalDateTime.now());
        dataConnectionMapper.insertSelective(biDataConnection);
    }

    public void deleteById(Integer id) {
        dataConnectionMapper.logicalDeleteByPrimaryKey(id);
    }




    public void updateById(BiDataConnection biDataConnection) {
        biDataConnection.setUpdateTime(LocalDateTime.now());
        dataConnectionMapper.updateByPrimaryKeySelective(biDataConnection);
    }



}
