package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.BDicTreeMainMapper;
import org.java.bi.db.domain.BDicTreeMain;
import org.java.bi.db.domain.BDicTreeMainExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BDicTreeMainService {
    @Resource
    private BDicTreeMainMapper bDicTreeMainMapper;

    public List<BDicTreeMain> queryIndex(){
        BDicTreeMainExample example=new BDicTreeMainExample();
        example.or().andDeletedEqualTo(false).andEnabledEqualTo(true);
        return bDicTreeMainMapper.selectByExample(example);
    }

    public List<BDicTreeMain> querySelective(String name, Integer page,Integer limit,String sort){
        BDicTreeMainExample example=new BDicTreeMainExample();
        BDicTreeMainExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return bDicTreeMainMapper.selectByExample(example);
    }


    public int updateById(BDicTreeMain homeIcon){
        homeIcon.setUpdateTime(LocalDateTime.now());
        return bDicTreeMainMapper.updateByPrimaryKeySelective(homeIcon);
    }

    public void deleteById(Integer id){
        bDicTreeMainMapper.logicalDeleteByPrimaryKey(id);
    }


    public void add(BDicTreeMain homeIcon){
        homeIcon.setAddTime(LocalDateTime.now());
        homeIcon.setUpdateTime(LocalDateTime.now());
        bDicTreeMainMapper.insertSelective(homeIcon);
    }


    public BDicTreeMain findById(Integer id){
        return bDicTreeMainMapper.selectByPrimaryKey(id);
    }

    public boolean checkExistByKeyAndName(Integer id, String name) {
        BDicTreeMainExample example = new BDicTreeMainExample();
        BDicTreeMainExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }
        if(!StringUtils.isEmpty(id)){
            criteria.andIdNotEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        return bDicTreeMainMapper.countByExample(example) != 0;
    }
}
