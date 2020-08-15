package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.SysOpadminPubMapper;
import org.java.bi.db.domain.SysOpadminPub;
import org.java.bi.db.domain.SysOpadminPubExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysOpadminPubService {
    @Resource
    private SysOpadminPubMapper litemallOpadminPubMapper;


    public List<SysOpadminPub> queryIndex(){
        SysOpadminPubExample example=new SysOpadminPubExample();
        example.or().andDeletedEqualTo(false);
        return litemallOpadminPubMapper.selectByExample(example);
    }

    public List<SysOpadminPub> querySelective(Integer litemallUserinfoPubId, Integer page,Integer limit,String sort){
        SysOpadminPubExample example=new SysOpadminPubExample();
        SysOpadminPubExample.Criteria criteria=example.createCriteria();
        if(litemallUserinfoPubId!=null){
            criteria.andInfoIdEqualTo(litemallUserinfoPubId);
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return litemallOpadminPubMapper.selectByExample(example);
    }


    public int updateById(SysOpadminPub litemallOpadminPub){
        litemallOpadminPub.setUpdateTime(LocalDateTime.now());
        return litemallOpadminPubMapper.updateByPrimaryKeySelective(litemallOpadminPub);
    }

    public void deleteById(Integer id){
        litemallOpadminPubMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByInfoId(Integer litemallUserinfoPubId){
        SysOpadminPubExample example = new SysOpadminPubExample();
        example.or().andInfoIdEqualTo(litemallUserinfoPubId);
        litemallOpadminPubMapper.logicalDeleteByExample(example);
    }


    public void add(SysOpadminPub litemallOpadminPub){
        litemallOpadminPub.setAddTime(LocalDateTime.now());
        litemallOpadminPub.setUpdateTime(LocalDateTime.now());
        litemallOpadminPubMapper.insertSelective(litemallOpadminPub);
    }


    public SysOpadminPub findById(Integer id){
        return litemallOpadminPubMapper.selectByPrimaryKey(id);
    }

    public List<SysOpadminPub> queryByInfoId(Integer infoId) {
        SysOpadminPubExample example = new SysOpadminPubExample();
        example.or().andInfoIdEqualTo(infoId).andDeletedEqualTo(false);
        return litemallOpadminPubMapper.selectByExample(example);
    }
    }
