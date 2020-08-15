package org.java.bi.db.service;

import com.github.pagehelper.PageHelper;
import org.java.bi.db.dao.BDicMainMapper;
import org.java.bi.db.domain.BDicMain;
import org.java.bi.db.domain.BDicMainExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BDicMainService {
    @Resource
    private BDicMainMapper litemallDicMainMapper;

    public List<BDicMain> queryIndex(){
        BDicMainExample example=new BDicMainExample();
        example.or().andDeletedEqualTo(false).andEnabledEqualTo(true);
        return litemallDicMainMapper.selectByExample(example);
    }

    public List<BDicMain> querySelective(String name, Integer page,Integer limit,String sort){
        BDicMainExample example=new BDicMainExample();
        BDicMainExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }

        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        PageHelper.startPage(page,limit);
        return litemallDicMainMapper.selectByExample(example);
    }

    public List<BDicMain> queryAll(){
        BDicMainExample example=new BDicMainExample();
        BDicMainExample.Criteria criteria=example.createCriteria();

        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("ordernumber");

        return litemallDicMainMapper.selectByExample(example);
    }


    public int updateById(BDicMain homeIcon){
        homeIcon.setUpdateTime(LocalDateTime.now());
        return litemallDicMainMapper.updateByPrimaryKeySelective(homeIcon);
    }

    public void deleteById(Integer id){
        litemallDicMainMapper.logicalDeleteByPrimaryKey(id);
    }


    public void add(BDicMain homeIcon){
        homeIcon.setAddTime(LocalDateTime.now());
        homeIcon.setUpdateTime(LocalDateTime.now());
        litemallDicMainMapper.insertSelective(homeIcon);
    }


    public BDicMain findById(Integer id){
        return litemallDicMainMapper.selectByPrimaryKey(id);
    }

    public boolean checkExistByName(String name) {
        BDicMainExample example = new BDicMainExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return litemallDicMainMapper.countByExample(example) != 0;
    }
}
