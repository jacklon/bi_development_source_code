package org.java.bi.db.service;


import org.java.bi.db.dao.BDicTreeCodeMapper;
import org.java.bi.db.domain.BDicTreeCode;
import org.java.bi.db.domain.BDicTreeCodeExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BDicTreeCodeService {
    @Resource
    private BDicTreeCodeMapper bDicTreeCodeMapper;

    public List<BDicTreeCode> queryIndex(){
        BDicTreeCodeExample example=new BDicTreeCodeExample();
        example.or().andDeletedEqualTo(false).andEnabledEqualTo(true);
        return bDicTreeCodeMapper.selectByExample(example);
    }


    /**
     * 模糊查找
     * @param mainId
     * @param parentId
     * @param id
     * @param name
     * @return
     */
    public List<BDicTreeCode> querySelective(Integer mainId,String mainName,Integer parentId,Integer id, String name,Boolean ifAnalyze){
        BDicTreeCodeExample example=new BDicTreeCodeExample();
        BDicTreeCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(mainId)){
            criteria.andMainIdEqualTo(mainId);
        }
        if(!StringUtils.isEmpty(mainName)){
            criteria.andMainNameEqualTo(mainName);
        }
        if(!StringUtils.isEmpty(parentId)){
            criteria.andParentIdEqualTo(parentId);
        }
        if(!StringUtils.isEmpty(id)){
            criteria.andMainIdEqualTo(id);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(ifAnalyze!=null){
            criteria.andIfAnalyzeEqualTo(ifAnalyze);
        }
        criteria.andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber");

        return bDicTreeCodeMapper.selectByExample(example);
    }

    /**
     * 精确查找
     * @param name
     * @param sort
     * @return
     */
    public List<BDicTreeCode> findByDicmainIdOrDicmainName(Integer dicmainId, String name,String sort){
        BDicTreeCodeExample example=new BDicTreeCodeExample();
        BDicTreeCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(dicmainId)){
            criteria.andMainIdEqualTo(dicmainId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andMainNameEqualTo(name);
        }
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause(sort);
        }else
        {
            example.setOrderByClause("ordernumber");
        }

        return bDicTreeCodeMapper.selectByExample(example);
    }

    public int updateById(BDicTreeCode litemallDicCode){
        litemallDicCode.setUpdateTime(LocalDateTime.now());
        return bDicTreeCodeMapper.updateByPrimaryKeySelective(litemallDicCode);
    }

    public void deleteById(Integer id){
        bDicTreeCodeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByMainId(Integer mainId){
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        example.or().andMainIdEqualTo(mainId);
        bDicTreeCodeMapper.logicalDeleteByExample(example);
    }


    public void add(BDicTreeCode litemallDicCode){
        litemallDicCode.setAddTime(LocalDateTime.now());
        litemallDicCode.setUpdateTime(LocalDateTime.now());
        bDicTreeCodeMapper.insertSelective(litemallDicCode);
    }


    public BDicTreeCode findById(Integer id){
        return bDicTreeCodeMapper.selectByPrimaryKey(id);
    }

    public BDicTreeCode findByMenuPath(String menuPath){
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        example.or().andAttr1EqualTo(menuPath);
        return bDicTreeCodeMapper.selectOneByExample(example);
    }

    public List<BDicTreeCode> queryByMainIdAndParentId(Integer mainId,Integer parentId) {
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        BDicTreeCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(mainId)){
            criteria.andMainIdEqualTo(mainId);
        }
        if(!StringUtils.isEmpty(parentId)){
            criteria.andParentIdEqualTo(parentId);
        }
        example.or().andMainIdEqualTo(mainId).andDeletedEqualTo(false);
        example.setOrderByClause("ordernumber");
        return bDicTreeCodeMapper.selectByExample(example);
    }

    public boolean checkExistByKeyAndName(Integer mainId, Integer id, String name) {
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        BDicTreeCodeExample.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(mainId)){
            criteria.andMainIdEqualTo(mainId);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }
        if(!StringUtils.isEmpty(id)){
            criteria.andIdNotEqualTo(id);
        }
        criteria.andDeletedEqualTo(false);
        return bDicTreeCodeMapper.countByExample(example) != 0;
    }
    public void deleteByIdList(List<Integer> idList) {
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        example.or().andIdIn(idList);
        bDicTreeCodeMapper.logicalDeleteByExample(example);
    }

    public List<Integer> recursiveChildNodesByIds(String mainName, List<Integer> ids,Boolean ifAnalyze){
        BDicTreeCodeExample example = new BDicTreeCodeExample();
        BDicTreeCodeExample.Criteria criteria=example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andMainNameEqualTo(mainName);
        if(ifAnalyze!=null) {
            criteria.andIfAnalyzeEqualTo(ifAnalyze);
        }
        List<BDicTreeCode> listAll= bDicTreeCodeMapper.selectByExample(example);
        List<Integer> result=new ArrayList<>();
        result.addAll(ids);
        for(Integer id:ids){
            recursiveChildTree(id,listAll,result);
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    private void recursiveChildTree(Integer id, List<BDicTreeCode> list,List<Integer> result) {
        for (BDicTreeCode bDicTreeCode : list) {
            if(id.equals(bDicTreeCode.getParentId()) ) {
                result.add(bDicTreeCode.getId());
                recursiveChildTree(bDicTreeCode.getId(), list,result);
            }
        }
    }



}
