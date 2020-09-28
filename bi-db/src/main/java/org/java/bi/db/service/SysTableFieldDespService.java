package org.java.bi.db.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysTableFieldDespMapper;
import org.java.bi.db.domain.BDicCode;
import org.java.bi.db.domain.SysTableDesp;
import org.java.bi.db.domain.SysTableFieldDesp;
import org.java.bi.db.domain.SysTableFieldDespExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysTableFieldDespService {
    @Resource
    private SysTableFieldDespMapper sysTableFieldDespMapper;

    @Autowired
    private SysTableDespService sysTableDespService;

    @Autowired
    private CommonDBService commonDBService;

    @Autowired
    private BDicCodeService litemallDiccodeService;

    @Autowired
    private SysBiMenuService litemallMenuService;


    public List<SysTableFieldDesp> queryIndex(){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        example.or().andDeletedEqualTo(false);
        return sysTableFieldDespMapper.selectByExample(example);
    }

    /**
     * 模糊查找
     * @param fieldName
     * @param page
     * @param limit
     * @param sort
     * @return
     */
    public List<SysTableFieldDesp> querySelective(String fieldName, Integer page,Integer limit,String sort){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        SysTableFieldDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(fieldName)){
            criteria.andFieldNameLike("%"+fieldName+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        } else{
            example.setOrderByClause("ordernumber");
        }
        PageHelper.startPage(page,limit);
        return sysTableFieldDespMapper.selectByExample(example);
    }

    /**
     * 精确查找
     * @param stdId
     * @param stdChineseDesc
     * @param fieldChineseDesp
     * @param sort
     * @return
     */
    public List<SysTableFieldDesp> findByMainIdOrMainName(Integer stdId, String stdChineseDesc, String fieldChineseDesp, String sort){
        SysTableFieldDespExample example=new SysTableFieldDespExample();
        SysTableFieldDespExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(stdId)){
            criteria.andStdIdEqualTo(stdId);
        }
        if(!StringUtils.isEmpty(stdChineseDesc)){
            criteria.andStdChineseDescEqualTo(stdChineseDesc);
        }
        if(!StringUtils.isEmpty(fieldChineseDesp)){
            criteria.andFieldChineseDespEqualTo(fieldChineseDesp);
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else
        {
            example.setOrderByClause("ordernumber");
        }

        return sysTableFieldDespMapper.selectByExample(example);
    }

    public int updateById(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        return sysTableFieldDespMapper.updateByPrimaryKeySelective(sysTableFieldDesp);
    }

    public int updateAllFieldById(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        return sysTableFieldDespMapper.updateByPrimaryKey(sysTableFieldDesp);
    }

    public void deleteById(Integer id){
        sysTableFieldDespMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByMainId(Integer stdId){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andStdIdEqualTo(stdId);
        sysTableFieldDespMapper.deleteByExample(example);
//        sysTableFieldDespMapper.logicalDeleteByExample(example);
    }

    public void add(SysTableFieldDesp sysTableFieldDesp){
        sysTableFieldDesp.setAddTime(LocalDateTime.now());
        sysTableFieldDesp.setUpdateTime(LocalDateTime.now());
        sysTableFieldDespMapper.insertSelective(sysTableFieldDesp);
    }


    public SysTableFieldDesp findById(Integer id){
        return sysTableFieldDespMapper.selectByPrimaryKey(id);
    }

    public List<SysTableFieldDesp> queryByMainId(Integer stdId) {
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andStdIdEqualTo(stdId).andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber,add_time");
        return sysTableFieldDespMapper.selectByExample(example);
    }

    public Map<String, Object> getTableFieldDesp(Integer stdId, String fieldName) {

        Map<String, Object> result=new HashMap<>();

        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andStdIdEqualTo(stdId).andFieldNameEqualTo(fieldName).andDeletedEqualTo(false);

        SysTableFieldDesp sysTableFieldDesp= sysTableFieldDespMapper.selectOneByExample(example);

        result.put("sysTableFieldDesp",sysTableFieldDesp);


        return result;
    }

    public boolean checkExistByFieldName(Integer stdId,String fieldName){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andStdIdEqualTo(stdId).andFieldNameEqualTo(fieldName);
        return sysTableFieldDespMapper.countByExample(example) != 0;
    }

    public  SysTableFieldDesp getByFieldName(Integer stdId,String fieldName){
        SysTableFieldDespExample example = new SysTableFieldDespExample();
        example.or().andStdIdEqualTo(stdId).andFieldNameEqualTo(fieldName).andDeletedEqualTo(false);
        return sysTableFieldDespMapper.selectOneByExample(example);
    }

}
