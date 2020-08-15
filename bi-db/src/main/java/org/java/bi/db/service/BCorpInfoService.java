package org.java.bi.db.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.java.bi.db.dao.BCorpInfoMapper;
import org.java.bi.db.domain.BCorpInfo;
import org.java.bi.db.domain.BCorpInfoExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;

@Service
public class BCorpInfoService {
    @Resource
    private BCorpInfoMapper bCorpInfoMapper;

    public List<BCorpInfo> list(Integer id, String name) {
        BCorpInfoExample example = new BCorpInfoExample();
        BCorpInfoExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("level,ordernumber,id");

        return bCorpInfoMapper.selectByExample(example);
    }

    public List<BCorpInfo> querySelective(String name, Integer page,Integer limit,String sort){
        BCorpInfoExample example=new BCorpInfoExample();
        BCorpInfoExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }
        if(limit<999999) {
            PageHelper.startPage(page, limit);
        }
        return bCorpInfoMapper.selectByExample(example);
    }


    public int updateById(BCorpInfo bCorpInfo){
        bCorpInfo.setUpdateTime(LocalDateTime.now());
        return bCorpInfoMapper.updateByPrimaryKeySelective(bCorpInfo);
    }

    public void deleteById(Integer id){
        bCorpInfoMapper.logicalDeleteByPrimaryKey(id);
    }
    public void deleteByIdList(List<Integer> idList) {
        BCorpInfoExample example = new BCorpInfoExample();
        example.or().andIdIn(idList);
        bCorpInfoMapper.logicalDeleteByExample(example);
    }

    public void add(BCorpInfo bCorpInfo){
        bCorpInfo.setAddTime(LocalDateTime.now());
        bCorpInfo.setUpdateTime(LocalDateTime.now());
        bCorpInfoMapper.insertSelective(bCorpInfo);
    }

    public BCorpInfo findById(Integer id){
        return bCorpInfoMapper.selectByPrimaryKey(id);
    }

    public List<BCorpInfo> findByParentId(Integer id){
        BCorpInfoExample example = new BCorpInfoExample();
        example.or().andDeletedEqualTo(false).andPidEqualTo(id);
        return bCorpInfoMapper.selectByExample(example);
    }

    public boolean checkExistByKeyAndName(Integer id, String name) {
        BCorpInfoExample example = new BCorpInfoExample();
        BCorpInfoExample.Criteria criteria=example.createCriteria();

        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }
        if(!StringUtils.isEmpty(id)){
            criteria.andIdNotEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        return bCorpInfoMapper.countByExample(example) != 0;
    }

    public List<Integer> recursiveChildDeptById(Integer id){
        BCorpInfoExample example = new BCorpInfoExample();
//        if(id==null){
            example.or().andDeletedEqualTo(false);
//        } else {
//            example.or().andDeletedEqualTo(false).andIdEqualTo(id);
//        }
        List<BCorpInfo> listAll= bCorpInfoMapper.selectByExample(example);

        List<Integer> result=new ArrayList<>();
        result.add(id);
        recursiveChildTree(id,listAll,result);
        return result;
    }

    public List<Integer> recursiveChildDeptByIds(List<Integer> ids){
        BCorpInfoExample example = new BCorpInfoExample();
        example.or().andDeletedEqualTo(false);
        List<BCorpInfo> listAll= bCorpInfoMapper.selectByExample(example);
        List<Integer> result=new ArrayList<>();
        result.addAll(ids);
        for(Integer id:ids){
            recursiveChildTree(id,listAll,result);
        }
        return result.stream().distinct().collect(Collectors.toList());
    }
    public Integer[] recursiveChildDeptByIds(Integer[] ids){
        BCorpInfoExample example = new BCorpInfoExample();
        example.or().andDeletedEqualTo(false);
        List<BCorpInfo> listAll= bCorpInfoMapper.selectByExample(example);
        List<Integer> resultList = Arrays.asList(ids);
        List<Integer> result=new ArrayList<>();
        result.addAll(resultList);
        for(Integer id:ids){
            recursiveChildTree(id,listAll,result);
        }
        return result.toArray(new Integer[0]);
    }

    private void recursiveChildTree(Integer id, List<BCorpInfo> list,List<Integer> result) {
        for (BCorpInfo bCorpInfo : list) {
            if(id.equals(bCorpInfo.getPid()) ) {
                result.add(bCorpInfo.getId());
                recursiveChildTree(bCorpInfo.getId(), list,result);
            }
        }
    }

}
